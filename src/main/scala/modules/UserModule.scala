package modules

import com.softwaremill.macwire._
import models.services.UserServiceImpl
import models.daos.UserDAO

trait UserModule {
  def userDAO: UserDAO

  lazy val userService = wire[UserServiceImpl]
}
