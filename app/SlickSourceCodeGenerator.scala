

/**
 * Created by dev on 10/01/16.
 */

object SlickSourceCodeGenerator {
  def main(args: Array[String]) : Unit = {
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val jdbcDriver = "org.postgresql.Driver"
    val slickDriver = "slick.driver.PostgresDriver"
    val pkg = "models"
    val user = "sa"
    val password = "pass"

    slick.codegen.SourceCodeGenerator.main(
      Array(slickDriver, jdbcDriver, url, "app", pkg, user, password)
    )

  }
}
