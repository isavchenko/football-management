import models.Tables._
import models.UsersRow
import play.api._
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc._

import models.{UsersRow, Tables}
import models.Tables._
import play.api.Logger
import slick.driver.JdbcProfile
import slick.jdbc.meta.MTable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
import slick.jdbc.meta.MTable
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.PostgresDriver.api._


object Global extends WithFilters() {
  override def onStart(app: Application): Unit = {
    super.onStart(app)
    val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
    val tables = Await.result(dbConfig.db.run(MTable.getTables("users")), 1.seconds).toList
    if (tables.isEmpty) {
      Await.result(dbConfig.db.run(DBIO.seq(
        Users.schema.create,
        Users += UsersRow("Test", "notifications.scheduler@gmail.com"))), Duration.Inf)
    }
  }
}
