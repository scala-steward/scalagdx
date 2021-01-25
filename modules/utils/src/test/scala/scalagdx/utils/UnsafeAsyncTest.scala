package scalagdx.utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import cats.effect.IO

class UnsafeAsyncTest extends AnyFlatSpec with Matchers with CatsUnsafeAsync {

  private val output = new StringBuilder()

  private def putStrLn(message: => String): IO[Unit] =
    IO {
      output ++= message
      ()
    }

  private val RunIO: IO[Unit] => Unit = UnsafeAsync[IO].unsafeRunAsync(_)

  it should "run cats io" in {
    RunIO(putStrLn("hi"))
    output.toString() shouldBe "hi"
    output.clear()
    RunIO(putStrLn("i love scala"))
    output.toString() shouldBe "i love scala"
  }
}
