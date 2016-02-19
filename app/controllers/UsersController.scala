
package controllers

import models.{UsersRow, Tables}
import models.Tables._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import slick.jdbc.meta.MTable
import play.api.libs.json.util._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * Created by dev on 9/01/16.
 */
object UsersController extends Controller {
  // todo: find a better place for writes
  implicit val userWrites = Json.writes[UsersRow]
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  def list = Action.async {
    val usersFuture: Future[Seq[UsersRow]] = dbConfig.db.run(Users.to[Seq].result)
    usersFuture.map {users =>
      Ok(Json.toJson(users))
    }
  }

  // for testing purposes
  def add = Action {
    val tables = Await.result(dbConfig.db.run(MTable.getTables("users")), 1.seconds).toList
    if (tables.isEmpty) {
      Await.result(dbConfig.db.run(DBIO.seq(
        Users.schema.create,
        Users += UsersRow("Test", "notifications.scheduler@gmail.com"))), Duration.Inf)
    }
    Ok("All good!")
  }

  def update = ???

  def delete = ???
}
