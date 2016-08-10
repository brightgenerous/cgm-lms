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
