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

import scala.annotation.tailrec

import com.badlogic.gdx.utils.NumberUtils
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.GreaterEqual
import eu.timepit.refined.numeric.LessEqual
import eu.timepit.refined.string.MatchesRegex

/**
 * Representation of a mutable or immutable color.
 */
trait Color[T] extends Ordered[Color[_]] {

  import scalagdx.graphics.Color._

  /**
   * The red value of the color, has a range of [0, 1]
   */
  def r: Float Refined RGBA

  /**
   * The green value of the color, has a range of [0, 1]
   */
  def g: Float Refined RGBA

  /**
   * The blue blue of the color, has a range of [0, 1]
   */
  def b: Float Refined RGBA

  /**
   * The alpha alpha of the color, has a range of [0, 1]
   */
  def a: Float Refined RGBA

  /**
   * Converts this color to the mutable equivalent.<br>
   * If this color is already mutable, returns the same instance.
   */
  def toMutable: MutableColor

  /**
   * Converts this color to the immutable equivalent.<br>
   * If this color is already immutable, returns the same instance.
   */
  def toImmutable: ImmutableColor

  override def equals(obj: Any): Boolean = obj match {
    case that: Color[_] => toIntBits == that.toIntBits
    case _ => false
  }

  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  override def hashCode: Int = {
    def convert(value: Float) = if (value != 0f) NumberUtils.floatToIntBits(value) else 0
    var result = convert(r)
    result = 31 * result + convert(g)
    result = 31 * result + convert(b)
    result = 31 * result + convert(a)
    result
  }

  /**
   * Creates a copy of this color, using the given values.
   */
  def copy(
      r: Float Refined RGBA = this.r,
      g: Float Refined RGBA = this.g,
      b: Float Refined RGBA = this.b,
      a: Float Refined RGBA = this.a
  ): T

  /**
   * Multiplies this color with the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(r: Float, g: Float, b: Float, a: Float): T

  /**
   * Multiplies this color with the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(value: Float): T = mul(value, value, value, value)

  /**
   * Multiplies this color with the given color.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(color: Color[_]): T = mul(color.r, color.g, color.b, color.a)

  /**
   * Multiplies this color by the given color, returning a new instance containing the result.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def *(color: Color[_]): T

  /**
   * Adds this color with the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def add(r: Float, g: Float, b: Float, a: Float): T

  /**
   * Adds this color with the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def add(value: Float): T = add(value, value, value, value)

  /**
   * Adds this color with the given color.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def add(color: Color[_]): T = add(color.r, color.g, color.b, color.a)

  /**
   * Adds this color with the given RGBA values, returning a new instance containing the result.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def +(color: Color[_]): T

  /**
   * Subtracts this color by the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(r: Float, g: Float, b: Float, a: Float): T

  /**
   * Subtracts this color by the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(value: Float): T = sub(value, value, value, value)

  /**
   * Subtracts this color by the given color.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(color: Color[_]): T = sub(color.r, color.g, color.b, color.a)

  /**
   * Subtracts this color by the given RGBA values, returning a new instance containing the result.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def -(color: Color[_]): T

  /**
   * Divides this color by the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def div(r: Float, g: Float, b: Float, a: Float): T

  /**
   * Divides this color by the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def div(value: Float): T = div(value, value, value, value)

  /**
   * Divides this color by the given color.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def div(color: Color[_]): T = div(color.r, color.g, color.b, color.a)

  /**
   * Divides this color by the given RGBA values, returning a new instance containing the result.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def /(color: Color[_]): T

  /**
   * Linearly interpolates between this color and the given RGBA values, using the provided alpha value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @param alpha Interpolation coefficient, in the range of [0, 1]
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def lerp(r: Float, g: Float, b: Float, a: Float, alpha: Float Refined Alpha): T = add(
    r = (r - this.r) * alpha,
    g = (g - this.g) * alpha,
    b = (b - this.b) * alpha,
    a = (a - this.a) * alpha
  )

  /**
   * Linearly interpolates between this color and the given color, using the provided alpha value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @param alpha Interpolation coefficient, in the range of [0, 1]
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods.
   */
  def lerp(color: Color[_], alpha: Float Refined Alpha): T = lerp(color.r, color.g, color.b, color.a, alpha)

  /**
   * Multiplies this color's RGB values by its alpha value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @return New instance if using an immutable color. Otherwise returns the same instance to allow chaining methods
   */
  def premultiplyAlpha: T = mul(a, a, a, 1)

  /**
   * Packs this color's values into a 32-bit integer with the format ABGR.
   */
  def toIntBits: Int = Color.toIntBits(
    Refined.unsafeApply((r * 255f).toInt),
    Refined.unsafeApply((g * 255f).toInt),
    Refined.unsafeApply((b * 255f).toInt),
    Refined.unsafeApply((a * 255f).toInt)
  )

  /**
   * Packs this color's values into a 32-bit integer with the format ABGR, then coverts it back to a float.
   * Note: Converting to a float and back can be lossy for its alpha value.
   */
  def toFloatBits: Float = Color.toFloatBits(r, g, b, a)

  /**
   * Packs this color's values into a 32-bit integer with the format RGB565.
   */
  def toRgb565: Int = Color.toRgb565(r, g, b)

  /**
   * Packs this color's values into a 32-bit integer with the format RGB4444.
   */
  def toRgba4444: Int = Color.toRgba4444(r, g, b, a)

  /**
   * Packs this color's values into a 32-bit integer with the format RGB888.
   */
  def toRgb888: Int = Color.toRgb888(r, g, b)

  /**
   * Packs this color's values into a 32-bit integer with the format RGBA8888.
   */
  def toRgba8888: Int = Color.toRgba8888(r, g, b, a)

  /**
   * Packs the ABGR values into a 32-bit integer with the format ARGB8888.
   */
  def toArgb8888: Int = Color.toArgb8888(a, r, g, b)

  /**
   * Converts the color to an encoded hex string with the format RRGGBBAA.
   */
  def toHex: String = {
    @tailrec def hex(value: String): String = {
      if (value.length < 8) hex("0" + value)
      else value
    }
    "#" +
      hex((((255 * r).toInt << 24) | ((255 * g).toInt << 16) | ((255 * b).toInt << 8) | ((255 * a).toInt)).toHexString)
  }

  /**
   * Extracts the Hue-Saturation-Value of the color.
   */
  def toHsv: Hsv = {
    val max = math.max(math.max(r, g), b)
    val min = math.min(math.min(r, g), b)
    val range = max - min
    def hue(v1: Float, v2: Float, a: Float) = 60f * (v1 - v2) / range + a
    Hsv(
      h =
        if (range == 0f) 0f
        else if (max == r.value) hue(g, b, 360f) % 360f
        else if (max == g.value) hue(b, r, 120f)
        else hue(r, g, 240f),
      s = if (max > 0f) 1f - min / max else 0f,
      v = max
    )
  }

  override def compare(that: Color[_]): Int = toIntBits.compare(that.toIntBits)

  /**
   * Type safe equality comparison.
   */
  def ===(color: Color[_]): Boolean = equals(color)

  /**
   * Type safe inequality comparison.
   */
  def =!=(color: Color[_]): Boolean = !equals(color)
}

object Color {

  /**
   * Refined predicate which clamps a number to the range [0, 1]
   */
  type RGBA = GreaterEqual[W.`0f`.T] And LessEqual[W.`1f`.T]

  /**
   * Refined predicate which clamps a number to the range [0, 1]
   */
  type Alpha = GreaterEqual[W.`0f`.T] And LessEqual[W.`1f`.T]

  /**
   * Refined predicate which clamps a number to the range [0, 255]
   */
  type ABGR = GreaterEqual[W.`0`.T] And LessEqual[W.`255`.T]

  /**
   * Predicate for #RRGGBBAA hex codes
   */
  type RRGGBBAA = MatchesRegex[W.`"^#([a-fA-F0-9]{6}|[a-fA-F0-9]{8})$"`.T]

  /**
   * Creates an immutable [[Color]] using the given RGBA values.
   */
  def apply(
      r: Float Refined RGBA = 0f,
      g: Float Refined RGBA = 0f,
      b: Float Refined RGBA = 0f,
      a: Float Refined RGBA = 0f
  ): Color[ImmutableColor] = ImmutableColor(r, g, b, a)

  /**
   * Destructure the [[Color]] for pattern-matching.
   */
  def unapply(color: Color[_]): Option[(Float, Float, Float, Float)] = Some((color.r, color.g, color.b, color.a))

  /**
   * Creates an immutable [[Color]] using the given hex string with the RRGGBBAA format.
   */
  def fromHex(value: String Refined RRGGBBAA): Color[ImmutableColor] = ImmutableColor.fromHex(value)

  /**
   * Packs the RGBA values into a 32-bit integer with the format ABGR.<br>
   * Values must be in the range of [0, 255].
   */
  def toIntBits(r: Int Refined ABGR, g: Int Refined ABGR, b: Int Refined ABGR, a: Int Refined ABGR): Int =
    (a << 24) | (b << 16) | (g << 8) | r

  /**
   * Packs the RGBA values into a 32-bit integer with the format ABGR, then coverts it back to a float.<br>
   * Values must be in the range of [0, 255].<br>
   * Note: Converting to a float and back can be lossy for its alpha value.
   */
  def toFloatBits(r: Int Refined ABGR, g: Int Refined ABGR, b: Int Refined ABGR, a: Int Refined ABGR): Float =
    NumberUtils.intToFloatColor(
      toIntBits(
        Refined.unsafeApply(r.toInt),
        Refined.unsafeApply(g.toInt),
        Refined.unsafeApply(b.toInt),
        Refined.unsafeApply(a.toInt)
      )
    )

  /**
   * Packs the RGBA values into a 32-bit integer with the format ABGR, then coverts it back to a float.<br>
   * Values must be in the range of [0, 1].<br>
   * Note: Converting to a float and back can be lossy for its alpha value.
   */
  def toFloatBits(r: Float Refined RGBA, g: Float Refined RGBA, b: Float Refined RGBA, a: Float Refined RGBA): Float =
    NumberUtils.intToFloatColor(
      toIntBits(
        Refined.unsafeApply((r * 255f).toInt),
        Refined.unsafeApply((g * 255f).toInt),
        Refined.unsafeApply((b * 255f).toInt),
        Refined.unsafeApply((a * 255f).toInt)
      )
    )

  /**
   * Packs the alpha value into a 32-bit integer. The alpha value must be in the range of [0, 1].
   */
  def alpha(value: Float Refined Alpha): Int = (value * 255f).toInt

  private def pack(value: Float, multiplier: Float, shiftBits: Int): Int = (value * multiplier).toInt << shiftBits

  /**
   * Packs a luminance and alpha value into a 32-bit. The values must be in the range of [0, 1].
   */
  def luminanceAlpha(luminance: Float Refined Alpha, alpha: Float Refined Alpha): Int =
    pack(luminance, 255f, 8) | (alpha * 255f).toInt

  /**
   * Packs the RGB values into a 32-bit integer with the format RGB565.
   */
  def toRgb565(r: Float, g: Float, b: Float): Int = pack(r, 31f, 11) | pack(g, 63f, 5) | (b * 31f).toInt

  /**
   * Packs the RGBA values into a 32-bit integer with the format RGB4444.
   */
  def toRgba4444(r: Float, g: Float, b: Float, a: Float): Int =
    pack(r, 15f, 12) | pack(g, 15f, 8) | pack(b, 15f, 4) | (a * 15f).toInt

  /**
   * Packs the RGB values into a 32-bit integer with the format RGB888.<br>
   * Values must be in the range of [0, 1].
   */
  def toRgb888(r: Float Refined RGBA, g: Float Refined RGBA, b: Float Refined RGBA): Int =
    pack(r, 255f, 16) | pack(g, 255f, 8) | (b * 255f).toInt

  /**
   * Packs the RGBA values into a 32-bit integer with the format RGBA8888.<br>
   * Values must be in the range of [0, 1].
   */
  def toRgba8888(r: Float Refined RGBA, g: Float Refined RGBA, b: Float Refined RGBA, a: Float Refined RGBA): Int =
    pack(r, 255f, 24) | pack(g, 255f, 16) | pack(b, 255f, 8) | (a * 255f).toInt

  /**
   * Packs the ARGB values into a 32-bit integer with the format ARGB8888.<br>
   * Values must be in the range of [0, 1].
   */
  def toArgb8888(a: Float Refined RGBA, r: Float Refined RGBA, g: Float Refined RGBA, b: Float Refined RGBA): Int =
    pack(a, 255f, 24) | pack(r, 255f, 16) | pack(g, 255f, 8) | (b * 255f).toInt

  /**
   * Creates an immutable [[Color]] using the given integer with the format RGB565.
   */
  def fromRgb565(value: Int): Color[ImmutableColor] = ImmutableColor.fromRgb565(value)

  /**
   * Creates an immutable [[Color]] using the given integer with the format RGBA4444.
   */
  def fromRgba4444(value: Int): Color[ImmutableColor] = ImmutableColor.fromRgba4444(value)

  /**
   * Creates an immutable [[Color]] using the given integer with the format RGB888.
   */
  def fromRgb888(value: Int): Color[ImmutableColor] = ImmutableColor.fromRgb888(value)

  /**
   * Creates an immutable [[Color]] using the given integer with the format RGBA8888.
   */
  def fromRgba8888(value: Int): Color[ImmutableColor] = ImmutableColor.fromRgba8888(value)

  /**
   * Creates an immutable [[Color]] using the given integer with the format ARGB8888.
   */
  def fromArgb8888(value: Int): Color[ImmutableColor] = ImmutableColor.fromArgb8888(value)

  /**
   * Creates an immutable [[Color]] using the given value with the format ABGR8888.
   */
  def fromAbgr8888(value: Float): Color[ImmutableColor] = ImmutableColor.fromAbgr8888(value)

  /**
   * Creates an immutable [[Color]] using the given Hue-Saturation-Value.
   */
  def fromHsv(h: Float, s: Float, v: Float): Color[ImmutableColor] = ImmutableColor.fromHsv(h, s, v)

  /**
   * Creates an immutable [[Color]] using the given Hue-Saturation-Value.
   */
  def fromHsv(value: Hsv): Color[ImmutableColor] = ImmutableColor.fromHsv(value)
}
