package modules

import play.api.routing.Router
import play.api.routing.sird._
import org.webjars.play.WebJarAssets
import scala.concurrent.Future
import scala.language.dynamics

class Routes(
    application: controllers.Application,
    socialAuthController: controllers.SocialAuthController,
    credentialsAuthController: controllers.CredentialsAuthController,
    signUpController: controllers.SignUpController,
    webJarAssets: WebJarAssets
) {

  // def baseMap = Map[(RequestMethodExtractor, String), Function0[Future[Result]]](
  //   (GET -> "") -> application.index,
  //   (GET -> "/signIn") -> application.signIn
  // )

  //def router = Router.from {
  //  baseMap.map {
  //    case ((method, path), fun) =>
  //      ???
  //  }
  //}

  def apply() = Router.from {
    // Home page
    case GET(p"/") => application.index
    case GET(p"/signIn") => application.signIn
    case GET(p"/signUp") => application.signUp
    case GET(p"/signOut") => application.signOut
    case GET(p"/authenticate/$provider") => socialAuthController.authenticate(provider)
    case POST(p"/authenticate/credentials") => credentialsAuthController.authenticate
    case POST(p"/signUp") => signUpController.signUp

    // Map static resources from the /public folder to the /assets URL path
    case GET(p"/assets/$file") => controllers.Assets.at(path = "/public", file)
    case GET(p"/webjars/$file") => webJarAssets.at(file)
  }

  def reverse = new Dynamic {
    override def applyDynamic(method: String) = method match {
      case "/" =>
    }
  }
}

object Routes {
}
