package tables

import models.User
import slick.driver.PostgresDriver.api._

/**
 * Created by dev on 9/01/16.
 */
class Users(tag: Tag) extends Table[User](tag, "users") {
  def name = column[String]("name")
  def email = column[String]("email")
  def * = (name, email) <> (User.tupled, User.unapply)
}


