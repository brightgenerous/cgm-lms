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
////////////////////////
lazy val appConfig = ConfigFactory.parseFile(new File("./conf/application.conf")).resolve()

flywayLocations := Seq("filesystem:conf/db/migration/default/common", "filesystem:conf/db/migration/default/h2")

flywayUrl := appConfig.getString("slick.dbs.default.db.url")

flywayUser := appConfig.getString("slick.dbs.default.db.user")

flywayPassword := appConfig.getString("slick.dbs.default.db.password")

////////////////////////
// Slick code generator
////////////////////////
slickCodeGen <<= slickCodeGenTask // register sbt command
//(compile in Compile) <<= (compile in Compile) dependsOn (slickCodeGenTask) // register automatic code generation on compile
lazy val slickCodeGen = taskKey[Seq[File]]("slick-codegen")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val slickDriver = appConfig.getString("slick.dbs.default.driver").init
  val jdbcDriver = appConfig.getString("slick.dbs.default.db.driver")
  val url = appConfig.getString("slick.dbs.default.db.url")
  val outputDir = "modules/cgmlms-infrastructure/app"
  val pkg = "sc.ript.cgmlms.infra.database"
  val username = appConfig.getString("slick.dbs.default.db.user")
  val password = appConfig.getString("slick.dbs.default.db.password")
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
  .dependsOn(cgmlmsCommon)
  .enablePlugins(PlayScala)
lazy val cgmlmsDomain = (project in file("modules/cgmlms-domain"))
  .settings(commonSettings: _*)
  .settings(cgmlmsDomainSettings: _*)
  .dependsOn(cgmlmsCommon, cgmlmsInfrastructure)
  .enablePlugins(PlayScala)
lazy val cgmlmsWeb = (project in file("modules/cgmlms-web"))
  .settings(commonSettings: _*)
  .settings(cgmlmsWebSettings: _*)
  .dependsOn(cgmlmsCommon, cgmlmsInfrastructure, cgmlmsDomain)
  .enablePlugins(PlayScala)
