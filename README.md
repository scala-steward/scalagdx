[![Travis](https://travis-ci.org/scalagdx/scalagdx.svg?branch=main)](https://travis-ci.org/github/scalagdx/scalagdx)
[![codecov](https://codecov.io/gh/scalagdx/scalagdx/branch/main/graph/badge.svg?token=XX5LL6MDQT)](https://codecov.io/gh/scalagdx/scalagdx)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.scalagdx/scalagdx-app.svg)](https://oss.sonatype.org/#nexus-search;quick~scalagdx)
[![LibGDX](https://img.shields.io/badge/libgdx-1.9.12-red.svg)](https://libgdx.badlogicgames.com/)
[![license](https://img.shields.io/badge/license-GPL%20v3-green)](https://www.gnu.org/licenses/gpl-3.0.en.html)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

Scala functional wrapper for LibGDX. (Work in Progress)

## Planned Features
ScalaGDX aims to be a modular framework, inspired by [LibKTX](https://github.com/libktx/ktx).
| Module   | Planned Features                                           |
|----------|------------------------------------------------------------|
| app      | ApplicationListener which returns an F[_] rather than Unit |
| assets   | Referentially transparent asset management                 |
| graphics | Referentially transparent rendering methods                |
| i18n     | Simple localization api                                    |
| log      | Referentially transparent logging                          |
