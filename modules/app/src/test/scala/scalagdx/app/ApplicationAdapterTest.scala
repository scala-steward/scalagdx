package scalagdx.app

import cats.effect.IO
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalagdx.app.utils.CatsUnsafeAsync

class ApplicationAdapterTest extends AnyFlatSpec with Matchers with CatsUnsafeAsync {

  it should "return unit on all methods" in {
    val listener = new ApplicationAdapter[IO].asJava
    listener.create() shouldBe (())
    listener.resize(0, 0) shouldBe (())
    listener.resume() shouldBe (())
    listener.pause() shouldBe (())
    listener.dispose() shouldBe (())
  }
}
