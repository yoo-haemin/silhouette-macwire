package controllers

import play.api._
import play.api.mvc.{ ControllerComponents, AbstractController }
import play.api.i18n.MessagesApi

import com.mohiva.play.silhouette.api.{ Environment, LogoutEvent, Silhouette }
import com.mohiva.play.silhouette.api.actions.{ SecuredAction, UserAwareAction }
import com.mohiva.play.silhouette.impl.providers.SocialProviderRegistry

import forms._
import models.User
import modules.SilhouetteModule.DefaultEnv
import utils.CSRFHelper

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param env The Silhouette environment.
 * @param socialProviderRegistry The social provider registry.
 */
class Application(cc: ControllerComponents)(
    val messagesApi: MessagesApi,
    silhouette: Silhouette[DefaultEnv],
    socialProviderRegistry: SocialProviderRegistry,
    csrfHelper: CSRFHelper
) extends AbstractController(cc) with CookieAuthentication {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = Action.async { implicit request =>
    silhouette.SecuredRequestHandler { securedRequest =>
      Future.successful(Ok(securedRequest.identity.toString))
    }
  }

  /**
   * Handles the Sign In action.
   *
   * @return The result to display.
   */
  def signIn = UserAwareAction.async { implicit request =>
    request.identity match {
      case Some(user) => Future.successful(Redirect(routes.Application.index()))
      case None => Future.successful(Ok(views.html.signIn(SignInForm.form, socialProviderRegistry, csrfHelper)))
    }
  }

  /**
   * Handles the Sign Up action.
   *
   * @return The result to display.
   */
  def signUp = UserAwareAction.async { implicit request =>
    request.identity match {
      case Some(user) => Future.successful(Redirect(routes.Application.index()))
      case None => Future.successful(Ok(views.html.signUp(SignUpForm.form, csrfHelper)))
    }
  }

  /**
   * Handles the Sign Out action.
   *
   * @return The result to display.
   */
  def signOut = SecuredAction.async { implicit request =>
    val result = Redirect(routes.Application.index())
    silhouette.env.eventBus.publish(LogoutEvent(request.identity, request, request2Messages))

    silhouette.env.authenticatorService.discard(request.authenticator, result)
  }
}
