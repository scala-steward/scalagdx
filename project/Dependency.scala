import sbt._

object Dependency {

  private def dependency(group: String)(version: String)(useScalaVersion: Boolean)(artifact: String) =
    (if (useScalaVersion) group %% _ else group % _)(artifact) % version

  private val typeLevel = dependency("org.typelevel") _
  val catsCore = typeLevel(Version.catsCore)(true)("cats-core")
  val catsEffect = typeLevel(Version.catsEffect)(true)("cats-effect")

  // Compiler plugins
  val kindProjector = typeLevel(Version.kindProjector)(false)("kind-projector")

  // Testing frameworks
  private val weaver = dependency("com.disneystreaming")(Version.weaver)(true) _
  val weaverCats = weaver("weaver-cats")
  val weaverCatsCheck = weaver("weaver-scalacheck")
}
