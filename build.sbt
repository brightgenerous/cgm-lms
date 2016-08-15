import com.typesafe.config.ConfigFactory

name := """cgm-lms"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .dependsOn(cgmlmsCommon, cgmlmsInfrastructure, cgmlmsDomain)
  .enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"
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
  val outputDir = "modules/cgmlms-infrastructure/app"
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

////////////////////////
// Library
///////////////////////
lazy val cgmlmsCommon = (project in file("modules/cgmlms-common"))
  .settings(commonSettings: _*)
  .settings(cgmlmsCommonSettings: _*)
  .enablePlugins(PlayScala)
lazy val cgmlmsInfrastructure = (project in file("modules/cgmlms-infrastructure"))
  .settings(commonSettings: _*)
  .settings(cgmlmsInfrastructureSettings: _*)
  .aggregate(cgmlmsCommon)
  .dependsOn(cgmlmsCommon)
  .enablePlugins(PlayScala)
lazy val cgmlmsDomain = (project in file("modules/cgmlms-domain"))
  .settings(commonSettings: _*)
  .settings(cgmlmsDomainSettings: _*)
  .aggregate(cgmlmsCommon, cgmlmsInfrastructure)
  .dependsOn(cgmlmsCommon, cgmlmsInfrastructure)
  .enablePlugins(PlayScala)
