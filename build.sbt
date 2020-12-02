import Dependencies.Libraries._
import ReleaseTransformations._

lazy val root = project
  .in(file("."))
  .aggregate(app)
  .settings(noPublishSettings)
  .settings(commonSettings, releaseSettings)

lazy val app = project
  .in(file("app"))
  .settings(commonSettings, publishSettings)
  .settings(
    name := "scalagdx-app",
    libraryDependencies ++= Seq(
      gdxCore,
      gdxNativesDesktopTest,
    )
  )

lazy val commonSettings = Seq(
  organization := "com.github.scalagdx",
  scalaVersion := "2.13.4",
  crossScalaVersions := Seq(scalaVersion.value),
  //add compiler plugins here
  //add common dependencies here
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
    )
  ),
  homepage := Some(url("https://github.com/scalagdx/scalagdx")),
  licenses := Seq(
    "GPLv3" -> url("https://opensource.org/licenses/GPL-3.0")
  ),
  pomIncludeRepository := { _ =>
    false
  },
  developers := List(
    Developer(
      id = "qwbarch",
      name = "Edward Yang",
      email = "edwardyang0410@gmail.com",
      url = url("https://github.com/qwbarch")
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
    else Opts.resolver.sonatypeStaging
  ),
)

lazy val credentialSettings = Seq(
  credentials ++= (for {
    username <- Option(
      "qwbarch"
    ) //Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(
      "%8ueJmLvV$GCbG4^qLJ#FiA%S#SskJmA9NmkWTX4"
    ) //Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password
  )).toSeq
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
  )
)
