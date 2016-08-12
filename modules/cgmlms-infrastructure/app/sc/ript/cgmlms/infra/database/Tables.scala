package sc.ript.cgmlms.infra.database
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Person.schema ++ SchemaVersion.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Person
   *  @param id Database column ID SqlType(INTEGER)
   *  @param name Database column NAME SqlType(VARCHAR), Length(100,true) */
  case class PersonRow(id: Int, name: String)
  /** GetResult implicit for fetching PersonRow objects using plain SQL queries */
  implicit def GetResultPersonRow(implicit e0: GR[Int], e1: GR[String]): GR[PersonRow] = GR{
    prs => import prs._
    PersonRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table PERSON. Objects of this class serve as prototypes for rows in queries. */
  class Person(_tableTag: Tag) extends Table[PersonRow](_tableTag, "PERSON") {
    def * = (id, name) <> (PersonRow.tupled, PersonRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> PersonRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER) */
    val id: Rep[Int] = column[Int]("ID")
    /** Database column NAME SqlType(VARCHAR), Length(100,true) */
    val name: Rep[String] = column[String]("NAME", O.Length(100,varying=true))
  }
  /** Collection-like TableQuery object for table Person */
  lazy val Person = new TableQuery(tag => new Person(tag))

  /** Entity class storing rows of table SchemaVersion
   *  @param installedRank Database column installed_rank SqlType(INTEGER), PrimaryKey
   *  @param version Database column version SqlType(VARCHAR), Length(50,true)
   *  @param description Database column description SqlType(VARCHAR), Length(200,true)
   *  @param `type` Database column type SqlType(VARCHAR), Length(20,true)
   *  @param script Database column script SqlType(VARCHAR), Length(1000,true)
   *  @param checksum Database column checksum SqlType(INTEGER)
   *  @param installedBy Database column installed_by SqlType(VARCHAR), Length(100,true)
   *  @param installedOn Database column installed_on SqlType(TIMESTAMP)
   *  @param executionTime Database column execution_time SqlType(INTEGER)
   *  @param success Database column success SqlType(BOOLEAN) */
  case class SchemaVersionRow(installedRank: Int, version: Option[String], description: String, `type`: String, script: String, checksum: Option[Int], installedBy: String, installedOn: java.sql.Timestamp, executionTime: Int, success: Boolean)
  /** GetResult implicit for fetching SchemaVersionRow objects using plain SQL queries */
  implicit def GetResultSchemaVersionRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[String], e3: GR[Option[Int]], e4: GR[java.sql.Timestamp], e5: GR[Boolean]): GR[SchemaVersionRow] = GR{
    prs => import prs._
    SchemaVersionRow.tupled((<<[Int], <<?[String], <<[String], <<[String], <<[String], <<?[Int], <<[String], <<[java.sql.Timestamp], <<[Int], <<[Boolean]))
  }
  /** Table description of table schema_version. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class SchemaVersion(_tableTag: Tag) extends Table[SchemaVersionRow](_tableTag, "schema_version") {
    def * = (installedRank, version, description, `type`, script, checksum, installedBy, installedOn, executionTime, success) <> (SchemaVersionRow.tupled, SchemaVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(installedRank), version, Rep.Some(description), Rep.Some(`type`), Rep.Some(script), checksum, Rep.Some(installedBy), Rep.Some(installedOn), Rep.Some(executionTime), Rep.Some(success)).shaped.<>({r=>import r._; _1.map(_=> SchemaVersionRow.tupled((_1.get, _2, _3.get, _4.get, _5.get, _6, _7.get, _8.get, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column installed_rank SqlType(INTEGER), PrimaryKey */
    val installedRank: Rep[Int] = column[Int]("installed_rank", O.PrimaryKey)
    /** Database column version SqlType(VARCHAR), Length(50,true) */
    val version: Rep[Option[String]] = column[Option[String]]("version", O.Length(50,varying=true))
    /** Database column description SqlType(VARCHAR), Length(200,true) */
    val description: Rep[String] = column[String]("description", O.Length(200,varying=true))
    /** Database column type SqlType(VARCHAR), Length(20,true)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(20,varying=true))
    /** Database column script SqlType(VARCHAR), Length(1000,true) */
    val script: Rep[String] = column[String]("script", O.Length(1000,varying=true))
    /** Database column checksum SqlType(INTEGER) */
    val checksum: Rep[Option[Int]] = column[Option[Int]]("checksum")
    /** Database column installed_by SqlType(VARCHAR), Length(100,true) */
    val installedBy: Rep[String] = column[String]("installed_by", O.Length(100,varying=true))
    /** Database column installed_on SqlType(TIMESTAMP) */
    val installedOn: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("installed_on")
    /** Database column execution_time SqlType(INTEGER) */
    val executionTime: Rep[Int] = column[Int]("execution_time")
    /** Database column success SqlType(BOOLEAN) */
    val success: Rep[Boolean] = column[Boolean]("success")

    /** Index over (success) (database name schema_version_s_idx) */
    val index1 = index("schema_version_s_idx", success)
  }
  /** Collection-like TableQuery object for table SchemaVersion */
  lazy val SchemaVersion = new TableQuery(tag => new SchemaVersion(tag))
}
