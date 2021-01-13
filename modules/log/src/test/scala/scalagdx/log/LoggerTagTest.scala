package scalagdx.log

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LoggerTagTest extends AnyFlatSpec with Matchers {

  it should "have a LoggerTag value of \"scalagdx.log.LoggerTest\"" in {
    LoggerTag.from(classOf[LoggerTagTest]) shouldBe LoggerTag("scalagdx.log.LoggerTagTest")
  }
}
