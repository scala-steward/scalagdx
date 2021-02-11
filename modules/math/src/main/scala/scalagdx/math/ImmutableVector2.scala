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
package scalagdx.math

import com.badlogic.gdx.math.Matrix3
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._

import math._

/**
 * Representation of an immutable 2D vector.
 */
class ImmutableVector2(val x: Float, val y: Float) extends Vector2[ImmutableVector2] {

  override def toString(): String = s"ImmutableVector2($x, $y)"

  /**
   * @inheritdoc
   */
  override lazy val len: Float = super.len

  /**
   * @inheritdoc
   */
  override lazy val len2: Float = super.len2

  /**
   * @inheritdoc
   */
  override def limit2(value: Float): ImmutableVector2 =
    if (len2 > value) scl(sqrt((value / len2).toDouble).toFloat)
    else this

  /**
   * @inheritdoc
   */
  override def setLength2(value: Float): ImmutableVector2 =
    if (len2 == 0 || len2 == value) this
    else scl(sqrt((value / len2).toDouble).toFloat)

  /**
   * @inheritdoc
   */
  override def clamp(min: Float, max: Float): ImmutableVector2 =
    if (len2 == 0f) this
    else {
      val max2 = max * max
      if (len2 > max2)
        scl(sqrt((max2 / len2).toDouble).toFloat)
      else {
        val min2 = min * min
        if (len2 < min2)
          scl(sqrt((min2 / len2).toDouble).toFloat)
        else this
      }
    }

  /**
   * @inheritdoc
   */
  override def mulAdd(vector: Vector2[_], scalar: Float): ImmutableVector2 = ImmutableVector2(
    x = this.x + (vector.x * scalar),
    y = this.y + (vector.y * scalar)
  )

  /**
   * @inheritdoc
   */
  override def mulAdd(vector: Vector2[_], scalar: Vector2[_]): ImmutableVector2 = ImmutableVector2(
    x = this.x + (vector.x * scalar.x),
    y = this.y + (vector.y * scalar.y)
  )

  /**
   * @inheritdoc
   */
  override def nor: ImmutableVector2 =
    if (len != 0) ImmutableVector2(x / len, y / len)
    else this

  /**
   * @inheritdoc
   */
  override def lerp(vector: Vector2[_], alpha: Refined[Float, Vector.Alpha]): ImmutableVector2 = {
    val invAlpha = 1f - alpha
    ImmutableVector2(
      x = (x * invAlpha) + (vector.x * alpha),
      y = (y * invAlpha) + (vector.y * alpha)
    )
  }

  /**
   * @inheritdoc
   */
  override def toMutable: MutableVector2 = MutableVector2(x, y)

  /**
   * @inheritdoc
   */
  override def toImmutable: ImmutableVector2 = this

  /**
   * @inheritdoc
   */
  override def copy(x: Float = this.x, y: Float = this.y): ImmutableVector2 = ImmutableVector2(x, y)

  /**
   * @inheritdoc
   */
  override def add(x: Float, y: Float): ImmutableVector2 = ImmutableVector2(
    x = this.x + x,
    y = this.y + y
  )

  /**
   * @inheritdoc
   */
  override def sub(x: Float, y: Float): ImmutableVector2 = ImmutableVector2(
    x = this.x - x,
    y = this.y - y
  )

  /**
   * @inheritdoc
   */
  override def scl(x: Float, y: Float): ImmutableVector2 = ImmutableVector2(
    x = this.x * x,
    y = this.y * y
  )

  /**
   * @inheritdoc
   */
  override def div(x: Float, y: Float): ImmutableVector2 = ImmutableVector2(
    x = this.x / x,
    y = this.y / y
  )

  /**
   * @inheritdoc
   */
  override def mul(matrix: Matrix3): ImmutableVector2 = ImmutableVector2(
    x = this.x * matrix.`val`(0) + this.y * matrix.`val`(3) + matrix.`val`(6),
    y = this.x * matrix.`val`(1) + this.y * matrix.`val`(4) + matrix.`val`(7)
  )

  /**
   * @inheritdoc
   */
  override def setAngleRad(value: Float): ImmutableVector2 = ImmutableVector2(len, 0f).rotateRad(value)

  /**
   * @inheritdoc
   */
  override def rotateRad(value: Float): ImmutableVector2 = {
    val cos = math.cos(value.toDouble).toFloat
    val sin = math.sin(value.toDouble).toFloat
    ImmutableVector2(
      x = x * cos - y * sin,
      y = x * sin + y * cos
    )
  }

  /**
   * @inheritdoc
   */
  override def rotateAroundDeg(vector: Vector2[_], value: Float): ImmutableVector2 =
    sub(vector).rotateDeg(value).add(vector)

  /**
   * @inheritdoc
   */
  override def rotateAroundRad(vector: Vector2[_], value: Float): ImmutableVector2 =
    sub(vector).rotateRad(value).add(vector)

  /**
   * @inheritdoc
   */
  override def rotate90(direction: Int): ImmutableVector2 =
    if (direction >= 0) ImmutableVector2(x = -y, y = x)
    else ImmutableVector2(x = y, y = -x)

  /**
   * @inheritdoc
   */
  override def +(vector: Vector2[_]): ImmutableVector2 = add(vector)

  /**
   * @inheritdoc
   */
  override def -(vector: Vector2[_]): ImmutableVector2 = sub(vector)

  /**
   * @inheritdoc
   */
  override def *(vector: Vector2[_]): ImmutableVector2 = scl(vector)

  /**
   * @inheritdoc
   */
  override def /(vector: Vector2[_]): ImmutableVector2 = div(vector)
}

object ImmutableVector2 {

  import scalagdx.math.Vector2.XY

  /**
   * Constructs a new [[ImmutableVector2]] using the given (x, y) values.
   */
  def apply(x: Float = 0f, y: Float = 0f): ImmutableVector2 = new ImmutableVector2(x, y)

  /**
   * Destructure the [[ImmutableVector2]] for pattern-matching.
   */
  def unapply(vector: ImmutableVector2): Option[(Float, Float)] = Some(vector.x -> vector.y)

  /**
   * Creates an [[ImmutableVector2]] from a string with the format (x,y).
   */
  def fromString(value: String Refined XY): ImmutableVector2 = {
    val values = value.substring(1, value.length - 1).split(",")
    ImmutableVector2(
      x = values(0).toFloat,
      y = values(1).toFloat
    )
  }
}
