import sbt._

object Dependencies {

  object Versions {
    lazy val gdx = "1.9.12"
  }

  object Libraries {
    lazy val gdxCore = "com.badlogicgames.gdx" % "gdx" % Versions.gdx
    lazy val gdxBackendHeadless =
      "com.badlogicgames.gdx" % "gdx-backend-headless" % Versions.gdx
    lazy val gdxNativesDesktopTest =
      "com.badlogicgames.gdx" % "gdx-platform" % Versions.gdx % Test classifier "natives-desktop"
  }
}
