import Dependency._

val TestScope = "it,test"

ThisBuild / scalacOptions ++= Seq(
  "-Ymacro-annotations",
  "-Ywarn-macros:after",
  "-P:semanticdb:synthetics:on",
  "-target:jvm-1.7",
)

lazy val commonSettings: Seq[SettingsDefinition] = Seq(
  organization := "com.github.scalagdx",
  scalaVersion := "2.13.6",
  crossScalaVersions := Seq("2.13.6", "2.12.13"),
  libraryDependencies ++= Seq(
    compilerPlugin(kindProjector cross CrossVersion.full),
    catsCore,
    catsEffect,
    weaverCats % TestScope,
    weaverCatsCheck % TestScope,
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
  )
