package scalagdx

import io.estatico.newtype.macros.newtype

package object log {
  
  @newtype case class InfoPrefix(value: String)
  @newtype case class DebugPrefix(value: String)
  @newtype case class ErrorPrefix(value: String)

  @newtype case class LoggerTag(value: String)

  object LoggerTag {

    def from(clazz: Class[_]): LoggerTag = LoggerTag(clazz.getName)
  }
}
