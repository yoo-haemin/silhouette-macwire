import _root_.controllers.Assets

import com.softwaremill.macwire._

//import org.flywaydb.play.FlywayPlayComponents
import org.webjars.play.WebJarAssets
import play.api._
import play.api.ApplicationLoader.Context
import play.api.cache.SyncCacheApi
//import play.api.db.slick.SlickComponents
import play.api.i18n.I18nComponents
import play.api.libs.openid.OpenIDComponents
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import play.filters.csrf.CSRFComponents
import play.filters.headers.SecurityHeadersComponents

import modules._

class AppLoader extends ApplicationLoader {
  def load(context: Context): Application = {
    LoggerConfigurator(context.environment.classLoader).foreach { _.configure(context.environment) }
    new AppComponents(context).application
  }
}

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context)
    with ControllerModule with UtilModule with UserModule with SilhouetteModule
    with DAOModule with DatabaseModule
    with I18nComponents with AhcWSComponents with CSRFComponents with SecurityHeadersComponents
    /* with SlickComponents */ with SyncCacheApi with OpenIDComponents {

  // for the optional Router param in error handler
  // if this is trying to use the Router value from BuiltInComponentsFromContext
  // it results in a circular dependency between the Router and the HttpErrorHandler
  // the application obviously won't start if this happens
  lazy val routerOption = None
  override lazy val httpErrorHandler = errorHandler
  override lazy val httpFilters: Seq[EssentialFilter] = filters.filters

  import _root_.controllers._
  import _root_.modules.Routes

  lazy val assetsConfiguration: AssetsConfiguration = wire[AssetsConfigurationProvider].get()
  //lazy val assetsConfiguration = assetsConfigurationProvider.get()
  lazy val assetsMetadata = wire[DefaultAssetsMetadata]
  lazy val webJarAssets = wire[WebJarAssets]
  lazy val assets: Assets = wire[Assets]
  lazy val router: Router = wire[Routes]()
}
