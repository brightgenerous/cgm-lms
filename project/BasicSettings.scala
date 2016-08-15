import play.sbt.PlayImport.PlayKeys
import sbt.Keys._
import sbt.{Attributed, Build, Test, _}
import sbtassembly.AssemblyKeys.{assembly, assemblyMergeStrategy}
import sbtassembly.{MergeStrategy, PathList}

object BasicSettings extends Build {

  //  scalaVersion : = defaultScalaVersion
  val defaultScalaVersion = "2.11.8"

  lazy val commonSettings = Seq(
    organization := "cgmlms",
    scalaVersion := defaultScalaVersion,
    retrieveManaged := true,
    fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value),
    assemblyMergeStrategy in assembly := {
      case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith ".properties" => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith ".xml" => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith ".types" => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith ".class" => MergeStrategy.first
      case "application.conf" => MergeStrategy.concat
      case "unwanted.txt" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    }
  )

  lazy val cgmlmsCommonSettings = Seq(
    name := "cgmlms-common",
    version := "1.0-SNAPSHOT"
  )

  lazy val cgmlmsInfrastructureSettings = Seq(
    name := "cgmlms-infrastructure",
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
      "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
      "org.scalatest" % "scalatest_2.11" % "3.0.0" % Test,
      "com.h2database" % "h2" % "1.4.192" % Test
    ),
    javaOptions in Test ++= Seq("-Dconfig.file=../../conf/application_infrastructure_test.conf")
  )

  lazy val cgmlmsDomainSettings = Seq(
    name := "cgmlms-domain",
    version := "1.0-SNAPSHOT"
  )

  lazy val cgmlmsWebSettings = Seq(
    name := "cgmlms-web",
    version := "1.0-SNAPSHOT"
  )
}
