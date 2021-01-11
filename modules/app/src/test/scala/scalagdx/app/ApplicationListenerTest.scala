package scalagdx.app

import cats.ApplicativeError
import cats.effect.IO
import cats.effect.Sync
import com.badlogic.gdx.{ApplicationListener => JApplicationListener}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scalagdx.app.utils.CatsUnsafeAsync

final class ExampleApplicationListener[F[_]: Sync: ApplicativeError[*[_], Throwable]] extends ApplicationListener[F] {

  override def create: F[Unit] = Sync[F].delay()

  override def render: F[Unit] = Sync[F].delay()

  override def resize(width: Int, height: Int): F[Unit] = Sync[F].delay()

  override def resume: F[Unit] = Sync[F].delay()

  override def pause: F[Unit] =
    ApplicativeError[F, Throwable].handleError(
      ApplicativeError[F, Throwable].raiseError(new Exception("Pause")),
    ) { case exception: Exception => () }

  override def dispose: F[Unit] = ApplicativeError[F, Throwable].raiseError(new Exception("Dispose"))
}

class ApplicationListenerTest extends AnyFlatSpec with Matchers with CatsUnsafeAsync {

  val listener: JApplicationListener = new ExampleApplicationListener[IO].asJava

  "example application listener" should "return unit on methods except dispose" in {
    listener.create() shouldBe ()
    listener.render() shouldBe ()
    listener.resize(0, 0) shouldBe ()
    listener.resume() shouldBe ()
    listener.pause() shouldBe ()
  }

  it should "throw exception on dispose" in {
    the[Exception] thrownBy listener.dispose() should have message "Dispose"
  }
}
