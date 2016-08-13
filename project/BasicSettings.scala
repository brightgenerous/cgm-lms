import play.sbt.PlayImport.PlayKeys
import sbt.Test
import sbt.Keys._
import sbt.{Attributed, Build}
import sbtassembly.AssemblyKeys.assembly

object BasicSettings extends Build {

  //  scalaVersion : = defaultScalaVersion
  val defaultScalaVersion = "2.11.8"

  lazy val commonSettings = Seq(
    organization := "cgmlms",
    scalaVersion := defaultScalaVersion,
    retrieveManaged := true
  )

  lazy val cgmlmsCommonSettings = Seq(
    name := "cgmlms-common",
    fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value),
    version := "1.0-SNAPSHOT",
    scalaVersion := defaultScalaVersion
  )

  lazy val cgmlmsInfrastructureSettings = Seq(
    name := "cgmlms-infrastructure",
    fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value),
    version := "1.0-SNAPSHOT",
    scalaVersion := defaultScalaVersion,
    javaOptions in Test ++= Seq("-Dconfig.file=../../conf/application.conf")
  )

  lazy val cgmlmsDomainSettings = Seq(
    name := "cgmlms-domain",
    fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value),
    version := "1.0-SNAPSHOT",
    scalaVersion := defaultScalaVersion
  )

  lazy val cgmlmsWebSettings = Seq(
    name := "cgmlms-web",
    version := "1.0-SNAPSHOT"
  )
}
