//package controllers

//import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
//import com.mohiva.play.silhouette.api.Silhouette
//import play.api.mvc.{ RequestHeader, Result }
//import scala.concurrent.Future
//import modules.SilhouetteModule.DefaultEnv
//import models.User

//trait CookieAuthentication extends Silhouette[DefaultEnv] {
//  override def onNotAuthenticated(request: RequestHeader): Option[Future[Result]] =
//    Some(Future.successful(Redirect(routes.Application.signIn())))
//}
