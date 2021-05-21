import sbt._

object Dependency {

  private def dependency(group: String)(version: String)(useScalaVersion: Boolean)(artifact: String) =
    (if (useScalaVersion) group %% _ else group % _)(artifact) % version

  private val typeLevel = dependency("org.typelevel") _

  private val cats = typeLevel(Version.catsCore)(true)
  val catsCore = cats("cats-core")
  val catsKernel = cats("cats-kernel")

  private val catsEffect = typeLevel(Version.catsEffect)(true)
  val catsEffectCore = catsEffect("cats-effect")
  val catsEffectKernel = catsEffect("cats-effect-kernel")
  val catsEffectStd = catsEffect("cats-effect-std")

  private val gdx = dependency("com.badlogicgames.gdx")(Version.libGDX)(false) _
  val gdxCore = gdx("gdx")
  val gdxBackendLwjgl = gdx("gdx-backend-lwjgl")

  val lwjgl2 = "org.lwjgl.lwjgl" % "lwjgl" % Version.lwjgl2
  val fs2 = "co.fs2" %% "fs2-core" % Version.fs2
  val organizeImports = "com.github.liancheng" %% "organize-imports" % Version.organizeImports

  // Compiler plugins
  val kindProjector = typeLevel(Version.kindProjector)(false)("kind-projector")
  val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Version.betterMonadicFor

  // Testing frameworks
  private val weaver = dependency("com.disneystreaming")(Version.weaver)(true) _
  val weaverCats = weaver("weaver-cats")
  val weaverCatsCheck = weaver("weaver-scalacheck")
}
