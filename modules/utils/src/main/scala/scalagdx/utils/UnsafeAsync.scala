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
package scalagdx.utils

import cats.effect.IO

/**
 * Type class for running side effects on an IO monad.
 */
trait UnsafeAsync[F[_]] {

  /**
   * Triggers the evaluation of the source and any suspended
   * side effects, re-throwing the error if unhandled.
   */
  def unsafeRunAsync(ev: F[Unit]): Unit

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  protected final def handleError(cb: Either[Throwable, Unit]): Unit = cb match {
    case Left(exception) => throw exception
    case _ => ()
  }
}

object UnsafeAsync {

  def apply[F[_]](implicit ev: UnsafeAsync[F]): UnsafeAsync[F] = ev
}

/**
 * Allows [[IO]] to run from Libgdx's [[com.badlogic.gdx.ApplicationListener]].
 * Should only be provided once at the top-level.
 */
trait CatsUnsafeAsync {

  /**
   * Provides a [[UnsafeAsync]] instance for cat's [[IO]] monad.
   */
  implicit val catsUnsafeAsync: UnsafeAsync[IO] = new UnsafeAsync[IO] {

    override def unsafeRunAsync(ev: IO[Unit]): Unit = ev.unsafeRunAsync(handleError)
  }
}
