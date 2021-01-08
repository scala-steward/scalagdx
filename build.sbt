import Dependency._
import ReleaseTransformations._

val TestScope = "it,test"

lazy val commonSettings = Seq(
  organization := "com.github.scalagdx",
  scalaVersion := "2.13.4",
  crossScalaVersions := Seq("2.11.12", "2.12.12", "2.13.4"),
  libraryDependencies ++= Seq(
    compilerPlugin(kindProjector cross CrossVersion.full),
    gdxCore,
    scalaCheck % TestScope,
    scalaTest % TestScope,
    scalaTestPlus % TestScope,
  ),
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
    username <- Option(
      "qwbarch",
    ) //Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(
      "%8ueJmLvV$GCbG4^qLJ#FiA%S#SskJmA9NmkWTX4",
    ) //Option(System.getenv().get("SONATYPE_PASSWORD"))
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
  .configs(IntegrationTest)
  .settings(noPublishSettings, commonSettings, releaseSettings)
  .settings(
    unmanagedSourceDirectories in Compile := Nil,
    unmanagedSourceDirectories in Test := Nil,
    unmanagedSourceDirectories in IntegrationTest := Nil,
  )
  .aggregate(app)

lazy val app = project
  .in(file("modules/app"))
  .settings(commonSettings, publishSettings)
  .settings(
    name := "gdx-app",
  )
