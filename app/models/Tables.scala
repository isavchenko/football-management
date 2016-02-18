package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Team.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema


  /** GetResult implicit for fetching TeamRow objects using plain SQL queries */
  implicit def GetResultTeamRow(implicit e0: GR[Option[String]]): GR[TeamRow] = GR{
    prs => import prs._
    TeamRow(<<?[String])
  }
  /** Table description of table team. Objects of this class serve as prototypes for rows in queries. */
  class Team(_tableTag: Tag) extends Table[TeamRow](_tableTag, "team") {
    def * = name <> (TeamRow, TeamRow.unapply)

    /** Database column name SqlType(text), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Default(None))
  }
  /** Collection-like TableQuery object for table Team */
  lazy val Team = new TableQuery(tag => new Team(tag))


  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (name, email) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(name), Rep.Some(email)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column name SqlType(varchar) */
    val name: Rep[String] = column[String]("name")
    /** Database column email SqlType(varchar) */
    val email: Rep[String] = column[String]("email")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
/** Entity class storing rows of table Team
   *  @param name Database column name SqlType(text), Default(None) */
  case class TeamRow(name: Option[String] = None)

  /** Entity class storing rows of table Users
   *  @param name Database column name SqlType(varchar)
   *  @param email Database column email SqlType(varchar) */
  case class UsersRow(name: String, email: String)
