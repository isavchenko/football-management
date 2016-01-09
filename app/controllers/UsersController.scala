package controllers

import models.User
import play.api.libs.json.{Writes, Json}
import play.api.{db, Play}
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, Controller}
import slick.driver.JdbcProfile
import slick.jdbc.meta.MTable
import slick.lifted.TableQuery
import tables.Users
import slick.driver.PostgresDriver.api._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by dev on 9/01/16.
 */
object UsersController extends Controller {
  def list = Action {
    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
    val users = TableQuery[Users]
    val result: Future[Seq[String]] = dbConfig.db.run(users.map(_.email).result)
    Ok(Json.toJson(Await.result(result, Duration.Inf)))
  }

  // for testing purposes
  def add = Action {
    val users = TableQuery[Users]
    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
    val tables = Await.result(dbConfig.db.run(MTable.getTables("users")), 1.seconds).toList
    if (tables.isEmpty) {
      Await.result(dbConfig.db.run(DBIO.seq(
        users.schema.create,
        users += User("Test", "notifications.scheduler@gmail.com"))), Duration.Inf)
    }
    Ok("All good!")
  }

}
