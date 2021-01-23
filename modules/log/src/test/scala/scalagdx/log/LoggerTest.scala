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

class LoggerTest
    extends AnyFlatSpec
    with Matchers
    with MockFactory
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with OneInstancePerTest {

  import LoggerTest.{output, stdout, exception}

  private val className = classOf[LoggerTest].getName

  System.setOut(new PrintStream(output))

  private val application = mock[Application]
  private val applicationLogger = new HeadlessApplicationLogger()

  // Create mock for Gdx.app.getLogLevel
  (application.getLogLevel _).expects().returning(Application.LOG_INFO).anyNumberOfTimes()

  // Create mock for Gdx.app.log(tag, message)
  (application.log: (String, String) => Unit)
    .expects(*, *)
    .onCall(applicationLogger.log(_, _))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.log(tag, message, throwable)
  (application.log: (String, String, Throwable) => Unit)
    .expects(*, *, exception)
    .onCall((tag, message, _) => application.log(tag, message))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.error(tag, message))
  (application.error: (String, String) => Unit)
    .expects(*, *)
    .onCall(applicationLogger.log(_, _))
    .anyNumberOfTimes()

  // Create mock for Gdx.app.error(tag, message, throwable)
  (application.error: (String, String, Throwable) => Unit)
    .expects(*, *, exception)
    .onCall((tag, message, _) => applicationLogger.log(tag, message))
    .anyNumberOfTimes()

  /*
  Since the Application log level is set to LOG_INFO during this test,
  any mocks required for debugging messages are omitted.
   */

  Gdx.app = application

  private implicit val sdxLogger = Logger.getLogger[IO]()
  private implicit val tag = LoggerTag.from(classOf[LoggerTest])

  override protected def beforeAll(): Unit = System.setOut(new PrintStream(output))

  override protected def afterAll(): Unit = System.setOut(stdout)

  override protected def beforeEach(): Unit = output.reset()

  it should "be info log level" in {
    Gdx.app.getLogLevel shouldBe Application.LOG_INFO
  }

  it should "not log debug messages" in {
    Logger[IO].debug("debug1").unsafeRunSync()
    output.toString shouldBe ""
    Logger[IO].debug(new Exception("debug2"))("debug2").unsafeRunSync()
    output.toString shouldBe ""
  }

  it should "log info messages" in {
    Logger[IO].info("info1").unsafeRunSync()
    output.toString shouldBe s"[[INFO] $className] info1\n"
    output.reset()
    Logger[IO].info(exception)("info2").unsafeRunSync()
    output.toString() shouldBe s"[[INFO] $className] info2\n"
  }

  it should "log error messages" in {
    Logger[IO].error("error1").unsafeRunSync()
    output.toString shouldBe s"[[ERROR] $className] error1\n"
    output.reset()
    Logger[IO].error("error2").unsafeRunSync()
    output.toString shouldBe s"[[ERROR] $className] error2\n"
  }
}

object LoggerTest {

  private val stdout = System.out
  private val output = new ByteArrayOutputStream
  private val exception = new Exception("logger test")
}
