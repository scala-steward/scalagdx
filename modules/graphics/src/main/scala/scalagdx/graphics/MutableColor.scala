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
package scalagdx.graphics

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import com.badlogic.gdx.graphics.{Color => JColor}

object MutableColor {

  import scalagdx.graphics.Color._

  /**
   * Creates a [[MutableColor]] using the given RGBA values.
   */
  def apply(r: Float = 0f, g: Float = 0f, b: Float = 0f, a: Float = 0f): MutableColor = new MutableColor(r, g, b, a)

  /**
   * Destructure the [[MumtableColor]] for pattern-matching.
   */
  def unapply(color: MutableColor): Option[(Float, Float, Float, Float)] = Some((color.r, color.g, color.b, color.a))

  private def from(f: MutableColor => Unit) = {
    val color = MutableColor()
    f(color)
    color
  }

  /**
   * Creates a [[MutableColor]] using the given hex string with the RRGGBBAA format.
   */
  def fromHex(value: String Refined RRGGBBAA): MutableColor = JColor.valueOf(value)

  /**
   * Creates a [[MutableColor]] using the given integer with the format RGB565.
   */
  def fromRgb565(value: Int): MutableColor = from(JColor.rgb565ToColor(_, value))

  /**
   * Creates a [[MutableColor]] using the given integer with the format RGBA4444.
   */
  def fromRgba4444(value: Int): MutableColor = from(JColor.rgba4444ToColor(_, value))

  /**
   * Creates a [[MutableColor]] using the given integer with the format RGB888.
   */
  def fromRgb888(value: Int): MutableColor = from(JColor.rgb888ToColor(_, value))

  /**
   * Creates a [[MutableColor]] using the given integer with the format RGBA8888.
   */
  def fromRgba8888(value: Int): MutableColor = from(JColor.rgba8888ToColor(_, value))

  /**
   * Creates a [[MutableColor]] using the given integer with the format ARGB8888.
   */
  def fromArgb8888(value: Int): MutableColor = from(JColor.argb8888ToColor(_, value))

  /**
   * Creates a [[MutableColor]] using the given value with the format ABGR8888.
   */
  def fromAbgr8888(value: Float): MutableColor = from(JColor.abgr8888ToColor(_, value))

  /**
   * Creates a [[MutableColor]] using the given Hue-Saturation-Value.
   */
  def fromHsv(h: Float, s: Float, v: Float): MutableColor = MutableColor().fromHsv(h, s, v)

  /**
   * Creates a [[MutableColor]] using the given Hue-Saturation-Value.
   */
  def fromHsv(value: Hsv): MutableColor = MutableColor().fromHsv(value.h, value.s, value.v)
}
