import Dependency._
import ReleaseTransformations._

val TestScope = "it,test"

// Requires type signature, otherwise it becomes Seq[Object]
lazy val commonSettings: Seq[SettingsDefinition] = Seq(
  Defaults.itSettings,
  organization := "com.github.scalagdx",
  scalaVersion := "2.13.4",
  crossScalaVersions := Seq("2.11.12", "2.12.12", "2.13.4"),
  libraryDependencies ++= Seq(
    compilerPlugin(kindProjector cross CrossVersion.full),
    gdxCore,
    catsCore,
    catsEffect,
    newType,
    scalaCheck % TestScope,
    scalaTest % TestScope,
    scalaTestPlus % TestScope,
    gdxBackendHeadless % TestScope,
  ),
  scalacOptions += "-Ymacro-annotations",
  scalacOptions --= Seq("-Wunused:imports", "Xlint:adapted-args"),
)

lazy val noPublishSettings = {
  Seq(
    publish := { () },
    publishLocal := { () },
    publishArtifact := false,
    skip in publish := true,
  )
}

lazy val releaseSettings = Seq(
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/scalagdx/scalagdx"),
      "scm:git:git@github.com:scalagdx/scalagdx.git",
    ),
  ),
  homepage := Some(url("https://github.com/scalagdx/scalagdx")),
  licenses := Seq(
    "GPLv3" -> url("https://opensource.org/licenses/GPL-3.0"),
  ),
  pomIncludeRepository := { _ =>
    false
  },
  developers := List(
    Developer(
      id = "qwbarch",
      name = "Edward Yang",
      email = "edwardyang0410@gmail.com",
      url = url("https://github.com/qwbarch"),
    ),
  ),
  publishMavenStyle := true,
  publishArtifact in Test := false,
)

lazy val publishSettings =
  releaseSettings ++ sharedPublishSettings ++ credentialSettings ++ sharedReleaseProcess

lazy val sharedPublishSettings = Seq(
  publishTo := Some(
    if (isSnapshot.value) Opts.resolver.sonatypeSnapshots
    else Opts.resolver.sonatypeStaging,
  ),
)

lazy val credentialSettings = Seq(
  credentials ++= (for {
    username <- Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password,
  )).toSeq,
)

lazy val sharedReleaseProcess = Seq(
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("+publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeRelease"),
    pushChanges,
  ),
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings ++ noPublishSettings ++ releaseSettings: _*)
  .settings(
    unmanagedSourceDirectories in Compile := Nil,
    unmanagedSourceDirectories in Test := Nil,
  )
  .aggregate(app, log)

lazy val app = project
  .in(file("modules/app"))
  .configs(IntegrationTest)
  .settings(commonSettings ++ publishSettings: _*)
  .settings(
    name := "gdx-app",
  )

lazy val log = project
  .in(file("modules/log"))
  .configs(IntegrationTest)
  .settings(commonSettings ++ publishSettings: _*)
  .settings(
    name := "gdx-log",
  )
