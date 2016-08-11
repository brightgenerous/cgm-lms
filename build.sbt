import com.typesafe.config.ConfigFactory

name := """cgm-lms"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

////////////////////////
// flyway
///////////////////////
flywayLocations := Seq("filesystem:conf/db/migration/default/common", "filesystem:conf/db/migration/default/h2")

flywayUrl := "jdbc:h2:file:./target/db/example"

flywayUser := "sa"

flywayPassword := ""

////////////////////////
// Slick code generator
////////////////////////
slickCodeGen <<= slickCodeGenTask // register sbt command
//(compile in Compile) <<= (compile in Compile) dependsOn (slickCodeGenTask) // register automatic code generation on compile
lazy val config = ConfigFactory.parseFile(new File("./conf/application.conf"))
lazy val slickCodeGen = taskKey[Seq[File]]("slick-codegen")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val slickDriver = config.getString("slick.dbs.default.driver").init
  val jdbcDriver = config.getString("slick.dbs.default.db.driver")
  val url = config.getString("slick.dbs.default.db.url")
  val outputDir = "app/"
  val pkg = "sc.ript.cgmlms.infra.database"
  val username = config.getString("slick.dbs.default.db.user")
  val password = config.getString("slick.dbs.default.db.password")
  toError(
    r.run(
      "slick.codegen.SourceCodeGenerator",
      cp.files,
      Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password),
      s.log
    )
  )
  val fname = outputDir + "/Tables.scala"
  Seq(file(fname))
}
