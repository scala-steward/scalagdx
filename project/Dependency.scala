import sbt._

object Dependency {

  private def dependency(group: String)(version: String)(useScalaVersion: Boolean)(artifact: String): ModuleID =
    (if (useScalaVersion) group %% _ else group % _)(artifact) % version

  private val typeLevel = dependency("org.typelevel") _
  val catsCore = typeLevel(Version.cats)(true)("cats-core")
  val catsEffect = typeLevel(Version.cats)(true)("cats-effect")
  val kindProjector = typeLevel(Version.kindProjector)(false)("kind-projector")

  private val gdx = dependency("com.badlogicgames.gdx")(Version.gdx)(false) _
  val gdxCore = gdx("gdx")
  val gdxBackendHeadless = gdx("gdx-backend-headless")
  val gdxPlatform = gdx("gdx-platform")

  val newType = "io.estatico" %% "newtype" % Version.newType
  val refined = "eu.timepit" %% "refined" % Version.refined

  val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest
  val scalaTestPlus = "org.scalatestplus" %% "scalacheck-1-14" % Version.scalaTestPlus
  val scalaMock = "org.scalamock" %% "scalamock" % Version.scalaMock
}
