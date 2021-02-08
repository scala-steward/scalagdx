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

import com.badlogic.gdx.{ApplicationListener => JApplicationListener}
import scalagdx.utils.Disposable
import scalagdx.utils.UnsafeAsync

/**
 * Used to handle the [[com.badlogic.gdx.Application]] lifecycle
 */
trait ApplicationListener[F[_]] extends Disposable[F] { self =>

  /**
   * Called when the [[com.badlogic.gdx.Application]] is created.
   * All methods in the OpenGL context is available when this is called.
   */
  def create: F[Unit]

  /**
   * Called as often as possible. This is equivelent to a game-loop.
   */
  def render: F[Unit]

  /**
   * Called when the [[com.badlogic.gdx.Application]] is resized
   * @param width The new width of the application
   * @param height The new height of the application
   * @return
   */
  def resize(width: Int, height: Int): F[Unit]

  /**
   * Called when the [[com.badlogic.gdx.Application]] is resumed
   */
  def resume: F[Unit]

  /**
   * Called when the [[com.badlogic.gdx.Application]] is paused
   */
  def pause: F[Unit]

  /**
   * Automatically called when the [[com.badlogic.gdx.Application]] stops
   */
  def dispose: F[Unit]

  /**
   * Converts this into the LibGDX equivelent to use with the target platform's [[Application]]
   */
  final def asJava(implicit F: UnsafeAsync[F]): JApplicationListener = new JApplicationListener {

    override def create(): Unit = F.unsafeRunAsync(self.create)

    override def resize(width: Int, height: Int): Unit = F.unsafeRunAsync(self.resize(width, height))

    override def render(): Unit = F.unsafeRunAsync(self.render)

    override def pause(): Unit = F.unsafeRunAsync(self.pause)

    override def resume(): Unit = F.unsafeRunAsync(self.resume)

    override def dispose(): Unit = F.unsafeRunAsync(self.dispose)

  }
}
