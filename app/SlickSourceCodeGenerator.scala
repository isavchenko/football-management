import slick.codegen.SourceCodeGenerator
import slick.driver.PostgresDriver
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object SlickSourceCodeGenerator {
  def main(args: Array[String]): Unit = {
    Await.ready(
      codegenFuture.map(_.writeToFile(
        "slick.driver.PostgresDriver", "app", "models", "Tables", "Tables.scala"
      )),
      20 seconds
    )
  }
  // fetch data model
  val modelAction = PostgresDriver.createModel(Some(PostgresDriver.defaultTables)) // you can filter specific tables here
  val db = PostgresDriver.api.Database.forURL(url="jdbc:postgresql://localhost:5432/postgres", user = "sa", password = "pass",
                  driver = "org.postgresql.Driver")
  val modelFuture = db.run(modelAction)
  // customize code generator
  val codegenFuture = modelFuture.map(model => new SourceCodeGenerator(model) {
    val models = new scala.collection.mutable.MutableList[String]

    override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]): String = {
      super.packageCode(profile, pkg, container, parentType) + "\n" + outsideCode
    }

    def outsideCode = s"${indent(models.mkString("\n"))}"

    override def Table = new Table(_) {
      override def EntityType = new EntityTypeDef {
        override def docWithCode: String = {
          models += super.docWithCode.toString + "\n"
          ""
        }
      }
    }
  })
}