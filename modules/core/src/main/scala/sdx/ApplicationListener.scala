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
 *
 * This product includes software developed at libGDX (https://libgdx.com/).
 */
package sdx

/**
 * The ApplicationListener follows the standard Android activity life-cycle and is emulated on the desktop accordingly.
 */
trait ApplicationListener[F[_]] extends Disposable[F] {
  /**
   * Called when the [[Application]] is created.
   */
  def create: F[Unit]

  /**
   * Called when the [[Application]] should render itself.
   */
  def render: F[Unit]

  /**
   * Called when the [[Application]] is resized. This will only be called during a non-paused state.
   *
   * First argument: The window's width in pixels.
   * Second argument: The window's height in pixels.
   */
  def resize: Int => Int => F[Unit]

  /**
   * Called when the [[Application]] is paused, usually when it's not active or visible on-screen.<br>
   * An [[Application]] is also paused before it is disposed.
   */
  def pause: F[Unit]

  /**
   * Called when the [[Application]] is resumed from a paused state, usually when it regains focus.
   */
  def resume: F[Unit]

  /**
   * Called when the [[Application]] is destroyed.
   */
  override def dispose: F[Unit]
}
