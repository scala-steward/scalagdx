package scalagdx.graphics

import com.badlogic.gdx.graphics.{Color => JColor}
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.GreaterEqual
import eu.timepit.refined.numeric.LessEqual
import scalagdx.graphics.Color.RGBA
import scalagdx.graphics.ext.ColorOps
import eu.timepit.refined.refineV
import eu.timepit.refined.string.MatchesRegex
import scala.annotation.tailrec

/**
 * Color represented by its RGBA values in the range [0, 1]
 */
final case class Color(
    r: Float Refined RGBA,
    g: Float Refined RGBA,
    b: Float Refined RGBA,
    a: Float Refined RGBA,
) {

  /**
   * Converts this into the java equivalent.
   */
  def asJava: JColor = new JColor(r, g, b, a)

  /**
   * Applies the function f to this color's RGBA values and the given RGBA values.
   *
   * @param color The color to use with the function f
   * @param f The first float represents this color's RGBA values.
   *              The second float represents the parameter color's RGBA values.
   * @return [[Left]] if any RGBA value is out of the range of [0,1], otherwise returns a [[Right]] with the new color
   */
  private def operator(r: Float, g: Float, b: Float, a: Float)(f: (Float, Float) => Float): Either[String, Color] =
    for {
      r <- refineV[RGBA](f(this.r, r))
      g <- refineV[RGBA](f(this.g, g))
      b <- refineV[RGBA](f(this.b, b))
      a <- refineV[RGBA](f(this.a, a))
    } yield Color(r, g, b, a)

  /**
   * Applies the function f to this color's RGBA values and the given value.
   *
   * @param color The color to use with the function f
   * @param f The first float represents this color's RGBA values.
   *              The second float represents the parameter color's RGBA values.
   * @return [[Left]] if any RGBA value is out of the range of [0,1], otherwise returns a [[Right]] with the new color
   */
  private def operator(value: Float)(f: (Float, Float) => Float): Either[String, Color] =
    operator(value, value, value, value)(f)

  /**
   * Applies the function f to this color's RGBA values and the given color's values.
   *
   * @param color The color to use with the function f
   * @param f The first float represents this color's RGBA values.
   *              The second float represents the parameter color's RGBA values.
   * @return [[Left]] if any RGBA value is out of the range of [0,1], otherwise returns a [[Right]] with the new color
   */
  private def operator(color: Color)(f: (Float, Float) => Float): Either[String, Color] =
    operator(color.r, color.g, color.b, color.a)(f)

  /**
   * Clamps a float value to a range of [0, 1]
   */
  private def clamp(value: Float): Float Refined RGBA =
    if (value > 1f) 1f
    else if (value < 0f) 0f
    else Refined.unsafeApply(value)

  /**
   * Applies the function f to this color's RGBA values and the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @param color The color to use with the function f
   * @param f The first float represents this color's RGBA values. The second float represents the parameter color's RGBA values.
   */
  private def operatorClamped(r: Float, g: Float, b: Float, a: Float)(f: (Float, Float) => Float): Color = Color(
    r = clamp(f(this.r, r)),
    g = clamp(f(this.g, g)),
    b = clamp(f(this.b, b)),
    a = clamp(f(this.a, a)),
  )

  /**
   * Applies the function f to this color's RGBA values and the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @param color The color to use with the function f
   * @param f The first float represents this color's RGBA values. The second float represents the parameter color's RGBA values.
   */
  private def operatorClamped(value: Float)(f: (Float, Float) => Float): Color =
    operatorClamped(value, value, value, value)(f)

  /**
   * Applies the function f to this color's RGBA values and the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   *
   * @param color The color to use with the function f
   * @param f The first float represents this color's RGBA values. The second float represents the parameter color's RGBA values.
   */
  private def operatorClamped(color: Color)(f: (Float, Float) => Float): Color =
    operatorClamped(color.r, color.g, color.b, color.a)(f)

  private def addition: (Float, Float) => Float = _ + _

  /**
   * Adds this color's RGBA values with the given RGBA values.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def add(r: Float, g: Float, b: Float, a: Float): Either[String, Color] = operator(r, g, b, a)(addition)

  /**
   * Adds this color's RGBA values with the given value.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def add(value: Float): Either[String, Color] = operator(value)(addition)

  /**
   * Adds this color's RGBA values with the given color's values.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def add(color: Color): Either[String, Color] = operator(color)(addition)

  /**
   * Adds this color's RGBA values with the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def addClamped(r: Float, g: Float, b: Float, a: Float): Color = operatorClamped(r, g, b, a)(addition)

  /**
   * Adds this color's RGBA values with the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def addClamped(value: Float): Color = operatorClamped(value)(addition)

  /**
   * Adds this color's RGBA values with the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def addClamped(color: Color): Color = operatorClamped(color)(addition)

  /**
   * Adds this color's RGBA values with the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def +(color: Color): Color = addClamped(color)

  private def subtraction: (Float, Float) => Float = _ - _

  /**
   * Subtracts this color's RGBA values by the given RGBA values.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def subtract(r: Float, g: Float, b: Float, a: Float): Either[String, Color] = operator(r, g, b, a)(subtraction)

  /**
   * Subtracts this color's RGBA values by the given value.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def subtract(value: Float): Either[String, Color] = operator(value)(subtraction)

  /**
   * Subtracts this color's RGBA values by the given color's values.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def subtract(color: Color): Either[String, Color] = operator(color)(subtraction)

  /**
   * Subtracts this color's RGBA values by the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def subtractClamped(r: Float, g: Float, b: Float, a: Float): Color = operatorClamped(r, g, b, a)(subtraction)

  /**
   * Subtracts this color's RGBA values by the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def subtractClamped(value: Float): Color = operatorClamped(value)(subtraction)

  /**
   * Subtracts this color's RGBA values with the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def subtractClamped(color: Color): Color = operatorClamped(color)(subtraction)

  /**
   * Subtracts this color's RGBA values with the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def -(color: Color): Color = subtractClamped(color)

  private def multiplication: (Float, Float) => Float = _ * _

  /**
   * Multiplies this color's RGBA values with the given RGBA values.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def multiply(r: Float, g: Float, b: Float, a: Float): Either[String, Color] = operator(r, g, b, a)(multiplication)

  /**
   * Multiplies this color's RGBA values with the given value.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def multiply(value: Float): Either[String, Color] = operator(value)(multiplication)

  /**
   * Multiplies this color's RGBA values with the given color's values.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def multiply(color: Color): Either[String, Color] = operator(color)(multiplication)

  /**
   * Multiplies this color's RGBA values with the given RGBA values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def multiplyClamped(r: Float, g: Float, b: Float, a: Float): Color = operatorClamped(r, g, b, a)(multiplication)

  /**
   * Multiplies this color's RGBA values with the given value.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def multiplyClamped(value: Float): Color = operatorClamped(value)(multiplication)

  /**
   * Multiplies this color's RGBA values with the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def multiplyClamped(color: Color): Color = operatorClamped(color)(multiplication)

  /**
   * Multiplies this color's RGBA values with the given color's values.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def *(color: Color): Color = multiplyClamped(color)

  private def linearInterpolation(coefficient: Float)(current: Float, target: Float): Float =
    current + (coefficient * (target - current))

  /**
   * Linearly interpolates between this color and the target RGBA values by the given coefficient.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def lerp(r: Float, g: Float, b: Float, a: Float, coefficient: Float): Either[String, Color] =
    operator(r, g, b, a)(linearInterpolation(coefficient))

  /**
   * Linearly interpolates between this color and the target color's values by the given coefficient.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def lerp(color: Color, coefficient: Float): Either[String, Color] =
    operator(color)(linearInterpolation(coefficient))

  /**
   * Linearly interpolates between this color and the target value by the given coefficient.
   *
   * @return [[Left]] if any RGBA value is out of the range of [0, 1], otherwise returns a [[Right]] with the new color
   */
  def lerp(value: Float, coefficient: Float): Either[String, Color] =
    operator(value)(linearInterpolation(coefficient))

  /**
   * Linearly interpolates between this color and the target RGBA values by the given coefficient.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def lerpClamped(r: Float, g: Float, b: Float, a: Float, coefficient: Float): Color =
    operatorClamped(r, g, b, a)(linearInterpolation(coefficient))

  /**
   * Linearly interpolates between this color and the target color's values by the given coefficient.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def lerpClamped(color: Color, coefficient: Float): Color =
    operatorClamped(color)(linearInterpolation(coefficient))

  /**
   * Linearly interpolates between this color and the target value by the given coefficient.
   * Clamps the returned color's RGBA values to the range of [0, 1].
   */
  def lerpClamped(value: Float, coefficient: Float): Color =
    operatorClamped(value)(linearInterpolation(coefficient))

  /**
   * Multiplies the RGB values by the alpha value.
   */
  def premultiplyAlpha: Color = (for {
    r <- refineV[RGBA](r * a)
    g <- refineV[RGBA](g * a)
    b <- refineV[RGBA](b * a)
  } yield Color(r, g, b, a)) match {
    case Right(value) => value
    case Left(_) => throw new UnknownError("This should never be thrown. Please contact the library authors to fix.")
  }

  /**
   * Packs the color components into a 32-bit integer with the format ABGR and then converts it to a float.
   */
  def toFloatBits: Float = Color.toFloatBits(r, g, b, a)

  /**
   * Packs the color components into a 32-bit integer with the format ABGR.
   */
  def toIntBits: Int = Color.toIntBits(
    r = Refined.unsafeApply((r.value * 255f).toInt),
    g = Refined.unsafeApply((g.value * 255f).toInt),
    b = Refined.unsafeApply((b.value * 255f).toInt),
    a = Refined.unsafeApply((a.value * 255f).toInt),
  )

  def rgb565: Int = JColor.rgb565(asJava)

  def rgba4444: Int = JColor.rgba4444(asJava)

  def rgb888: Int = JColor.rgb888(asJava)

  def rgba8888: Int = JColor.rgba8888(asJava)

  def argb8888: Int = JColor.argb8888(asJava)

  /**
   * Converts this color to a hex string with the RRGGBBAA format.
   */
  def toHex: String = {
    @tailrec def hex(value: String): String = {
      if (value.length < 8) hex("0" + value)
      else value
    }
    hex((((255 * r).toInt << 24) | ((255 * g).toInt << 16) | ((255 * b).toInt << 8) | ((255 * a).toInt)).toHexString)
  }
}

object Color {

  /**
   * Refined predicate which clamps a numbenr to the range [0f, 1f]
   */
  type RGBA = GreaterEqual[W.`0f`.T] And LessEqual[W.`1f`.T]

  /**
   * Refined predicate which clamps a number to the range [0, 255]
   */
  type ABGR = GreaterEqual[W.`0`.T] And LessEqual[W.`255`.T]

  /**
   * Predicate for #RRGGBBAA hex codes
   */
  type RRGGBBAA = MatchesRegex["^#([a-fA-F0-9]{8}|[a-fA-F0-9]{6})$"]

  /**
   * Constructs a new [[Color]] with 0 as its default RGBA values.
   */
  def apply(): Color = Color(0f, 0f, 0f, 0f)

  def apply(rgba8888: Int): Color = new JColor(rgba8888).asScala

  def apply(hex: String Refined RRGGBBAA): Color = JColor.valueOf(hex.value).asScala

  /**
   * Packs the color components into a 32-bit integer with the format ABGR and then converts it to a float.
   */
  def toFloatBits(
      r: Float Refined RGBA,
      g: Float Refined RGBA,
      b: Float Refined RGBA,
      a: Float Refined RGBA,
  ): Float = JColor.toFloatBits(r, g, b, a)

  /**
   * Packs the color components into a 32-bit integer with the format ABGR and then converts it to a float.
   * The required parameters must be in the range of [0, 255].
   */
  def toFloatBits(
      r: Int Refined ABGR,
      g: Int Refined ABGR,
      b: Int Refined ABGR,
      a: Int Refined ABGR,
  ): Float = JColor.toFloatBits(r, g, b, a)

  /**
   * Packs the color components into a 32-bit integer with the format ABGR.
   */
  def toIntBits(
      r: Int Refined ABGR,
      g: Int Refined ABGR,
      b: Int Refined ABGR,
      a: Int Refined ABGR,
  ): Int = JColor.toIntBits(r, g, b, a)

  def alpha(value: Float): Int = JColor.alpha(value)

  def luminanceAlpha(luminance: Float, alpha: Float): Int = JColor.luminanceAlpha(luminance, alpha)

  def rgb565(r: Float, g: Float, b: Float): Int = JColor.rgb565(r, g, b)

  def rgba4444(r: Float, g: Float, b: Float, a: Float): Int = JColor.rgba4444(r, g, b, a)

  def rgb888(r: Float, g: Float, b: Float): Int = JColor.rgb888(r, g, b)

  def rgba8888(r: Float, g: Float, b: Float, a: Float): Int = JColor.rgba8888(r, g, b, a)

  def argb8888(a: Float, r: Float, g: Float, b: Float): Int = JColor.argb8888(a, r, g, b)

  /**
   * Creates a new color using the given rgb565 value.
   */
  def fromRgb565(value: Int): Color = {
    val color = new JColor()
    JColor.rgb565ToColor(color, value)
    color.asScala
  }

  /**
   * Creates a new color using the given rgba4444 value.
   */
  def fromRgba4444(value: Int): Color = {
    val color = new JColor()
    JColor.rgba4444ToColor(color, value)
    color.asScala
  }

  /**
   * Creates a new color using the given rgb888 value.
   */
  def fromRgb888(value: Int): Color = {
    val color = new JColor()
    JColor.rgb888ToColor(color, value)
    color.asScala
  }

  /**
   * Creates a new color using the given rgba8888value.
   */
  def fromRgba8888(value: Int): Color = {
    val color = new JColor()
    JColor.rgba8888ToColor(color, value)
    color.asScala
  }

  /**
   * Creates a new color using the given argb8888 value.
   */
  def fromArgb8888(value: Int): Color = {
    val color = new JColor()
    JColor.argb8888ToColor(color, value)
    color.asScala
  }

  /**
   * Creates a new color using the given abgr8888 value.
   */
  def fromAbgr8888(value: Float): Color = {
    val color = new JColor()
    JColor.abgr8888ToColor(color, value)
    color.asScala
  }

  /**
   * Creates a new color using the given Hue-Saturation-Value.
   * The hsv values are not refined to preserve high range color.
   *
   * @param h Hue in range [0, 360]
   * @param s Saturation in range [0, 1]
   * @param v Brightness in range [0, 1]
   */
  def fromHsv(h: Float, s: Float, v: Float): Color = new JColor().fromHsv(h, s, v).asScala

  /**
   * Creates a new color using the given hex string with the RRGGBBAA format.
   */
  def fromHex(value: String Refined RRGGBBAA): Color = JColor.valueOf(value).asScala

  lazy val White: Color = Color(1f, 1f, 1f, 1f)
  lazy val LightGray: Color = Color(0xbfbfbfff)
  lazy val Gray: Color = Color(0x7f7f7fff)
  lazy val DarkGray: Color = Color(0x3f3f3fff)
  lazy val Black: Color = Color(0f, 0f, 0f, 1f)
  lazy val Clear: Color = Color(0f, 0f, 0f, 0f)
  lazy val Blue: Color = Color(0f, 0f, 1f, 1f)
  lazy val Navy: Color = Color(0f, 0f, 0.5f, 1f)
  lazy val Royal: Color = Color(0x4169e1ff)
  lazy val Slate: Color = Color(0x708090ff)
  lazy val Sky: Color = Color(0x87ceebff)
  lazy val Cyan: Color = Color(0f, 1f, 1f, 1f)
  lazy val Teal: Color = Color(0f, 0.5f, 0.5f, 1f)
  lazy val Green: Color = Color(0x00ff00ff)
  lazy val Chartreuse: Color = Color(0x7fff00ff)
  lazy val Lime: Color = Color(0x32cd32ff)
  lazy val Forest: Color = Color(0x228b22ff)
  lazy val Olive: Color = Color(0x6b8e23ff)
  lazy val Yellow: Color = Color(0xffff00ff)
  lazy val Gold: Color = Color(0xffd700ff)
  lazy val Goldenrod: Color = Color(0xdaa520ff)
  lazy val Orange: Color = Color(0xffa500ff)
  lazy val Brown: Color = Color(0x8b4513ff)
  lazy val Tan: Color = Color(0xd2b48cff)
  lazy val Firebrick: Color = Color(0xb22222ff)
  lazy val Red: Color = Color(0xff0000ff)
  lazy val Scarlet: Color = Color(0xff341cff)
  lazy val Coral: Color = Color(0xff7f50ff)
  lazy val Salmon: Color = Color(0xfa8072ff)
  lazy val Pink: Color = Color(0xff69b4ff)
  lazy val Magenta: Color = Color(1f, 0f, 1f, 1f)
  lazy val Purple: Color = Color(0xa020f0ff)
  lazy val Violet: Color = Color(0xee82eeff)
  lazy val Maroon: Color = Color(0xb03060ff)
}
