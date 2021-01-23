package scalagdx.log

import java.io.ByteArrayOutputStream
import java.io.PrintStream

import cats.effect.IO
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.HeadlessApplicationLogger
import org.scalamock.scalatest.MockFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.BeforeAndAfterEach
import org.scalatest.OneInstancePerTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

@SuppressWarnings(Array("org.wartremover.warts.Var"))
class LoggerTest
    extends AnyFlatSpec
    with Matchers
    with MockFactory
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with OneInstancePerTest {

  import LoggerTest._

  private val className = classOf[LoggerTest].getName

  System.setOut(new PrintStream(output))

  private val application = mock[Application]
  private val applicationLogger = new HeadlessApplicationLogger()

  private var logLevel: Int = -1

  // Create mock for Gdx.app.setLogLevel
  (application.setLogLevel _)
    .expects(*)
    .onCall { a: Int => logLevel = a }
    .anyNumberOfTimes()

  // Create mock for Gdx.app.getLogLevel
  (application.getLogLevel _)
    .expects()
    .onCall(() => logLevel)
    .anyNumberOfTimes()

  // Create mock for Gdx.app.log(tag, message)
  (application.log: (String, String) => Unit)
    .expects(*, *)
    .onCall(applicationLogger.log(_, _))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.log(tag, message, throwable), this mock ignores the exception
  (application.log: (String, String, Throwable) => Unit)
    .expects(*, *, exception)
    .onCall((tag, message, _) => applicationLogger.log(tag, message))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.error(tag, message))
  (application.error: (String, String) => Unit)
    .expects(*, *)
    .onCall(applicationLogger.log(_, _))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.error(tag, message, throwable), this mock ignores the exception
  (application.error: (String, String, Throwable) => Unit)
    .expects(*, *, exception)
    .onCall((tag, message, _) => applicationLogger.log(tag, message))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.debug(tag, message)
  (application.debug: (String, String) => Unit)
    .expects(*, *)
    .onCall(applicationLogger.debug(_, _))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.debug(tag, message, throwable), this mock ignores the exception
  (application.debug: (String, String, Throwable) => Unit)
    .expects(*, *, exception)
    .onCall((tag, message, _) => applicationLogger.debug(tag, message))
    .anyNumberOfTimes()

  Gdx.app = application

  private implicit val sdxLogger =
    Logger.getLogger[IO](InfoPrefix(infoPrefix), DebugPrefix(debugPrefix), ErrorPrefix(errorPrefix))
  private implicit val tag: LoggerTag = LoggerTag.from(classOf[LoggerTest])

  override protected def beforeAll(): Unit = System.setOut(new PrintStream(output))

  override protected def afterAll(): Unit = System.setOut(stdout)

  override protected def beforeEach(): Unit = output.reset()

  private def expectedOutput(prefix: String)(input: String) = s"[$prefix $className] $input\n"
  private def expectedInfo = expectedOutput(infoPrefix) _
  private def expectedDebug = expectedOutput(debugPrefix) _
  private def expectedError = expectedOutput(errorPrefix) _
  private def expectedEmpty = (_: String) => ""

  private def assertLog(input: => String)(f: String => IO[Unit])(expectedOutput: String => String) = {
    f(input).unsafeRunSync()
    output.toString shouldBe expectedOutput(input)
    output.reset()
  }

  it should "log at info level" in {
    Gdx.app.setLogLevel(Application.LOG_INFO)

    assertLog("info1")(Logger[IO].info(_))(expectedInfo)
    assertLog("info2")(Logger[IO].info(exception)(_))(expectedInfo)

    assertLog("debug1")(Logger[IO].debug(_))(expectedEmpty)
    assertLog("debug2")(Logger[IO].debug(exception)(_))(expectedEmpty)

    assertLog("error1")(Logger[IO].error(_))(expectedError)
    assertLog("error2")(Logger[IO].error(exception)(_))(expectedError)
  }

  it should "log at debug level" in {
    Gdx.app.setLogLevel(Application.LOG_DEBUG)

    assertLog("info1")(Logger[IO].info(_))(expectedInfo)
    assertLog("info2")(Logger[IO].info(exception)(_))(expectedInfo)

    assertLog("debug1")(Logger[IO].debug(_))(expectedDebug)
    assertLog("debug2")(Logger[IO].debug(exception)(_))(expectedDebug)

    assertLog("error1")(Logger[IO].error(_))(expectedError)
    assertLog("error2")(Logger[IO].error(exception)(_))(expectedError)
  }

  it should "log at error level" in {
    Gdx.app.setLogLevel(Application.LOG_ERROR)

    assertLog("info1")(Logger[IO].info(_))(expectedEmpty)
    assertLog("info2")(Logger[IO].info(exception)(_))(expectedEmpty)

    assertLog("debug1")(Logger[IO].debug(_))(expectedEmpty)
    assertLog("debug2")(Logger[IO].debug(exception)(_))(expectedEmpty)

    assertLog("error1")(Logger[IO].error(_))(expectedError)
    assertLog("error2")(Logger[IO].error(exception)(_))(expectedError)
  }

  it should "not log" in {
    Gdx.app.setLogLevel(Application.LOG_NONE)

    assertLog("info1")(Logger[IO].info(_))(expectedEmpty)
    assertLog("info2")(Logger[IO].info(exception)(_))(expectedEmpty)

    assertLog("debug1")(Logger[IO].debug(_))(expectedEmpty)
    assertLog("debug2")(Logger[IO].debug(exception)(_))(expectedEmpty)

    assertLog("error1")(Logger[IO].error(_))(expectedEmpty)
    assertLog("error2")(Logger[IO].error(exception)(_))(expectedEmpty)
  }
}

object LoggerTest {

  private val stdout = System.out
  private val output = new ByteArrayOutputStream
  private val exception = new Exception("logger test")

  private val infoPrefix = "[INFO]"
  private val debugPrefix = "[DEBUG]"
  private val errorPrefix = "[ERROR]"
}
