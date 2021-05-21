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
package sdx.lwjgl

import cats.syntax.all._
import com.badlogic.gdx.Graphics.DisplayMode

import scala.util.hashing.MurmurHash3

final class LwjglDisplayMode(
    width: Int,
    height: Int,
    refreshRate: Int,
    bitsPerPixel: Int,
    val mode: org.lwjgl.opengl.DisplayMode,
) extends DisplayMode(width, height, refreshRate, bitsPerPixel) {

  override def equals(x: Any): Boolean = x match {
    case other: LwjglDisplayMode =>
      width === other.width &&
        height === other.height &&
        refreshRate === other.refreshRate &&
        bitsPerPixel === other.bitsPerPixel &&
        mode == mode
    case _ => false
  }

  override def hashCode(): Int = {
    var hash = LwjglDisplayMode.hashSeed
    hash = MurmurHash3.mix(hash, width.##)
    hash = MurmurHash3.mix(hash, height.##)
    hash = MurmurHash3.mix(hash, refreshRate.##)
    hash = MurmurHash3.mix(hash, bitsPerPixel.##)
    hash = MurmurHash3.mixLast(hash, mode.##)
    hash
  }

  override def toString(): String = s"LwjglDisplayMode($width,$height,$refreshRate,$bitsPerPixel,$mode)"
}

object LwjglDisplayMode {

  private val hashSeed = MurmurHash3.stringHash("LwjglDisplayMode")

  def unapply(displayMode: LwjglDisplayMode): Option[(Int, Int, Int, Int, org.lwjgl.opengl.DisplayMode)] =
    Some(
      (
        displayMode.width,
        displayMode.height,
        displayMode.refreshRate,
        displayMode.bitsPerPixel,
        displayMode.mode,
      ),
    )
}
