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
package scalagdx.graphics

import scala.annotation.switch

import com.badlogic.gdx.utils.NumberUtils
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import scalagdx.graphics.Color.RGBA

/**
 * Representation of an immutable color.
 */
class ImmutableColor(
    val r: Float Refined RGBA,
    val g: Float Refined RGBA,
    val b: Float Refined RGBA,
    val a: Float Refined RGBA
) extends Color[ImmutableColor] {

  import scalagdx.graphics.ImmutableColor.refine

  override def toMutable: MutableColor = MutableColor(r, g, b, a)

  override def toImmutable: ImmutableColor = this

  override def toString(): String = s"ImmutableColor($r, $g, $b, $a)"

  /**
   * @inheritdoc
   */
  override def copy(
      r: Float Refined RGBA = this.r,
      g: Float Refined RGBA = this.g,
      b: Float Refined RGBA = this.b,
      a: Float Refined RGBA = this.a
  ): ImmutableColor = ImmutableColor(r, g, b, a)

  private def operator(r: Float, g: Float, b: Float, a: Float)(f: (Float, Float) => Float) = ImmutableColor(
    r = refine(f(this.r, r)),
    g = refine(f(this.g, g)),
    b = refine(f(this.b, b)),
    a = refine(f(this.a, a))
  )

  /**
   * @inheritdoc
   */
  override def mul(r: Float, g: Float, b: Float, a: Float): ImmutableColor = operator(r, g, b, a)(_ * _)

  /**
   * @inheritdoc
   */
  override def *(color: Color[_]): ImmutableColor = mul(color.r, color.g, color.b, color.a)

  /**
   * @inheritdoc
   */
  override def add(r: Float, g: Float, b: Float, a: Float): ImmutableColor = operator(r, g, b, a)(_ + _)

  /**
   * @inheritdoc
   */
  override def +(color: Color[_]): ImmutableColor = add(color.r, color.g, color.b, color.a)

  /**
   * @inheritdoc
   */
  override def sub(r: Float, g: Float, b: Float, a: Float): ImmutableColor = operator(r, g, b, a)(_ - _)

  /**
   * @inheritdoc
   */
  override def -(color: Color[_]): ImmutableColor = sub(color.r, color.g, color.b, color.a)

  /**
   * @inheritdoc
   */
  override def div(r: Float, g: Float, b: Float, a: Float): ImmutableColor = operator(r, g, b, a)(_ / _)

  /**
   * @inheritdoc
   */
  override def /(color: Color[_]): ImmutableColor = div(color.r, color.g, color.b, color.a)
}

object ImmutableColor {

  import scalagdx.graphics.Color.RRGGBBAA

  /**
   * Creates an [[ImmutableColor]] using the given RGBA values.
   */
  def apply(
      r: Float Refined RGBA = 0f,
      g: Float Refined RGBA = 0f,
      b: Float Refined RGBA = 0f,
      a: Float Refined RGBA = 0f
  ): ImmutableColor = new ImmutableColor(r, g, b, a)

  /**
   * Destructure the [[ImmutableColor]] for pattern-matching.
   */
  def unapply(color: ImmutableColor): Option[(Float, Float, Float, Float)] = Some((color.r, color.g, color.b, color.a))

  /**
   * Creates an [[ImmutableColor]] using the given hex string with the format RRGGBBAA.
   */
  def fromHex(value: String Refined RRGGBBAA): ImmutableColor = {
    def hex(start: Int, end: Int): Float Refined RGBA =
      Refined.unsafeApply(Integer.parseInt(value.substring(start, end), 16) / 255f)
    ImmutableColor(
      r = hex(1, 3),
      g = hex(3, 5),
      b = hex(5, 7),
      a = if (value.length == 9) hex(7, 9) else 1f
    )
  }

  private def unpackValue(value: Int)(divisor: Float)(a: Int, b: Int): Float Refined RGBA =
    Refined.unsafeApply(((value & a) >>> b) / divisor)

  private def unpackAlpha(value: Int)(divisor: Float)(a: Int): Float Refined RGBA =
    Refined.unsafeApply((value & a) / divisor)

  /**
   * Creates an [[ImmutableColor]] using the given integer with the format RGB565.
   */
  def fromRgb565(value: Int): ImmutableColor = {
    def f = unpackValue(value) _
    ImmutableColor(
      r = f(31f)(0x0000F800, 11),
      g = f(63f)(0x000007E0, 5),
      b = f(31f)(0x0000001F, 0)
    )
  }

  /**
   * Creates an [[ImmutableColor]] using the given integer with the format RGBA4444.
   */
  def fromRgba4444(value: Int): ImmutableColor = {
    def f = unpackValue(value)(15f) _
    def g = unpackAlpha(value)(15f) _
    ImmutableColor(
      r = f(0x0000f000, 12),
      g = f(0x00000f00, 8),
      b = f(0x000000f0, 4),
      a = g(0x0000000f)
    )
  }

  /**
   * Creates an [[ImmutableColor]] using the given integer with the format RGB888.
   */
  def fromRgb888(value: Int): ImmutableColor = {
    def f = unpackValue(value)(255f) _
    def g = unpackAlpha(value)(255f) _
    ImmutableColor(
      r = f(0x00ff0000, 16),
      g = f(0x0000ff00, 8),
      b = g(0x000000ff)
    )
  }

  /**
   * Creates an [[ImmutableColor]] using the given integer with the format RGBA8888.
   */
  def fromRgba8888(value: Int): ImmutableColor = {
    def f = unpackValue(value)(255f) _
    def g = unpackAlpha(value)(255f) _
    ImmutableColor(
      r = f(0xff000000, 24),
      g = f(0x00ff0000, 16),
      b = f(0x0000ff00, 8),
      a = g(0x000000ff)
    )
  }

  /**
   * Creates an [[ImmutableColor]] using the given integer with the format ARGB8888.
   */
  def fromArgb8888(value: Int): ImmutableColor = {
    def f = unpackValue(value)(255f) _
    def g = unpackAlpha(value)(255f) _
    ImmutableColor(
      a = f(0xff000000, 24),
      r = f(0x00ff0000, 16),
      g = f(0x0000ff00, 8),
      b = g(0x000000ff)
    )
  }

  /**
   * Creates an [[ImmutableColor]] using the given value with the format ABGR8888.
   */
  def fromAbgr8888(value: Float): ImmutableColor = {
    val intColor = NumberUtils.floatToIntColor(value)
    def f = unpackValue(intColor)(255f) _
    def g = unpackAlpha(intColor)(255f) _
    ImmutableColor(
      a = f(0xff000000, 24),
      b = f(0x00ff0000, 16),
      g = f(0x0000ff00, 8),
      r = g(0x000000ff)
    )
  }

  /**
   * Creates an [[ImmutableColor]] using the given Hue-Saturation-Value.
   */
  def fromHsv(h: Float, s: Float, v: Float): ImmutableColor = {
    val x = (h / 60f + 6) % 6
    val i = x.toInt
    val f = x - i
    val p = v * (1 - s)
    val q = v * (1 - s * f)
    val t = v * (1 - s * (1 - f))
    def convert(r: Float, g: Float, b: Float) =
      ImmutableColor(
        r = refine(r),
        g = refine(g),
        b = refine(b)
      )
    (i: @switch) match {
      case 0 => convert(v, t, p)
      case 1 => convert(q, v, p)
      case 2 => convert(p, v, t)
      case 3 => convert(p, q, v)
      case 4 => convert(t, p, v)
      case _ => convert(v, p, q)
    }
  }

  /**
   * Creates an [[ImmutableColor]] using the given Hue-Saturation-Value.
   */
  def fromHsv(value: Hsv): ImmutableColor = fromHsv(value.h, value.s, value.v)

  private def refine(value: Float): Float Refined RGBA =
    if (value > 1f) 1f
    else if (value < 0f) 0f
    else Refined.unsafeApply(value)
}
