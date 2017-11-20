package controllers

import SilhouetteMacwireComponents
import modules.ControllerModule
import controllers.Assets
import controllers.WebJarAssets
import java.util.UUID

import com.google.inject.AbstractModule
import com.mohiva.play.silhouette.api.{ Environment, LoginInfo }
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.test._
import models.User
import org.specs2.mock.Mockito
import org.specs2.specification.Scope
import play.api.Configuration
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws.WSClient
import play.api.routing.Router
import play.api.test.{ FakeRequest, PlaySpecification, WithApplication }

import play.api.{ ApplicationLoader, BuiltInComponentsFromContext, Configuration }
import router.Routes

import testkit.Silhouette
import testkit.SilhouetteMacwire
import testkit.FakeSilhouetteMacwireComponents

/**
 * Test case for the [[controllers.Application]] class.
 */
class ApplicationSpec extends PlaySpecification
    with Mockito with Silhouette with SilhouetteMacwire {
  sequential

  val application = new FakeSilhouetteMacwireComponents(context) {
    override lazy val silhouetteEnvironment = silhouetteEnv
  }.application

  "The `index` action" should {
    "redirect to login page if user is unauthorized" in new WithApplication(application) {
      val Some(redirectResult) = route(FakeRequest(controllers.routes.Application.index())
        .withAuthenticator[CookieAuthenticator](LoginInfo("invalid", "invalid")))

      status(redirectResult) must be equalTo SEE_OTHER

      val redirectURL = redirectLocation(redirectResult).getOrElse("")
      redirectURL must contain(controllers.routes.Application.signIn().toString())

      val Some(unauthorizedResult) = route(FakeRequest(GET, redirectURL))

      status(unauthorizedResult) must be equalTo OK
      contentType(unauthorizedResult) must beSome("text/html")
      contentAsString(unauthorizedResult) must contain("Silhouette - Sign In")
    }

    "return 200 if user is authorized" in new WithApplication(application) {
      val Some(result) = route(FakeRequest(controllers.routes.Application.index())
        .withAuthenticator[CookieAuthenticator](identity.loginInfo))

      status(result) must beEqualTo(OK)
    }
  }
}
