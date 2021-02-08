/**
 * Copyright (c) 2021 scalagdx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package scalagdx.app

import cats.ApplicativeError
import cats.effect.IO
import cats.effect.Sync
import com.badlogic.gdx.{ApplicationListener => JApplicationListener}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalagdx.utils.CatsUnsafeAsync

final class ExampleApplicationListener[F[_]: Sync: ApplicativeError[*[_], Throwable]] extends ApplicationListener[F] {

  override def create: F[Unit] = Sync[F].unit

  override def render: F[Unit] = Sync[F].unit

  override def resize(width: Int, height: Int): F[Unit] = Sync[F].delay(())

  override def resume: F[Unit] = Sync[F].unit

  override def pause: F[Unit] =
    ApplicativeError[F, Throwable].handleError(
      ApplicativeError[F, Throwable].raiseError(new Exception("Pause"))
    ) { case _: Exception => () }

  override def dispose: F[Unit] = ApplicativeError[F, Throwable].raiseError(new Exception("Dispose"))
}

class ApplicationListenerTest extends AnyFlatSpec with Matchers with CatsUnsafeAsync {

  val listener: JApplicationListener = new ExampleApplicationListener[IO].asJava

  "example application listener" should "return unit on methods except dispose" in {
    listener.create() shouldBe (())
    listener.render() shouldBe (())
    listener.resize(0, 0) shouldBe (())
    listener.resume() shouldBe (())
    listener.pause() shouldBe (())
  }

  it should "throw exception on dispose" in {
    the[Exception] thrownBy listener.dispose() should have message "Dispose"
  }
}
