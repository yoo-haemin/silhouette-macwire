package modules

import com.mohiva.play.silhouette.api.Environment
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.AvatarService
import com.mohiva.play.silhouette.api.util.Clock
import com.mohiva.play.silhouette.api.util.PasswordHasher
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.impl.providers.{ CredentialsProvider, SocialProviderRegistry }

import com.softwaremill.macwire._

import play.api.Configuration
import play.api.i18n.MessagesApi

import controllers._
import models.User
import models.services.UserService
import modules.SilhouetteModule.DefaultEnv
import utils.CSRFHelper

trait ControllerModule {
  def messagesApi: MessagesApi
  def silhouetteEnvironment: Environment[DefaultEnv]
  def socialProviderRegistry: SocialProviderRegistry
  def csrfHelper: CSRFHelper
  def userService: UserService
  def authInfoRepository: AuthInfoRepository
  def credentialsProvider: CredentialsProvider
  def configuration: Configuration
  def clock: Clock
  def avatarService: AvatarService
  def passwordHasher: PasswordHasher

  lazy val applicationController: Application = wire[Application]
  lazy val credentialsAuthController: CredentialsAuthController = wire[CredentialsAuthController]
  lazy val signUpController: SignUpController = wire[SignUpController]
  lazy val socialAuthController: SocialAuthController = wire[SocialAuthController]
}
