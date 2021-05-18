import Dependency._

ThisBuild / scalacOptions ++= Seq(
  "-Ymacro-annotations",
  "-Ywarn-macros:after",
  "-P:semanticdb:synthetics:on",
  "-target:jvm-1.8",
)

lazy val commonSettings: Seq[SettingsDefinition] = Seq(
  organization := "com.github.scalagdx",
  scalaVersion := "2.13.5",
  crossScalaVersions := Seq("2.13.5", "2.12.13"),
  libraryDependencies ++= Seq(
    compilerPlugin(kindProjector cross CrossVersion.full),
    compilerPlugin(betterMonadicFor),
    catsCore,
    catsEffectCore,
    catsEffectKernel,
    weaverCats % Test,
    weaverCatsCheck % Test,
  ),
  testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
  wartremoverErrors ++= Warts.allBut(
    Wart.ImplicitParameter,
    Wart.ImplicitConversion,
    Wart.Any,
    Wart.Nothing,
    Wart.Serializable,
    Wart.JavaSerializable,
    Wart.Product,
    Wart.PlatformDefault,
  ),
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    Compile / unmanagedSourceDirectories := Nil,
    Test / unmanagedSourceDirectories := Nil,
  ).aggregate(core)

lazy val core = (project in file("modules/core"))
  .settings(commonSettings: _*)
  .settings(
    name := "sdx-core",
    libraryDependencies += gdxCore,
  )
