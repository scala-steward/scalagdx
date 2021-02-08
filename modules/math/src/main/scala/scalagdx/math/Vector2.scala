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

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.{Vector2 => JVector2}
import com.badlogic.gdx.utils.NumberUtils
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.string.MatchesRegex
import scalagdx.math.Vector.Alpha

import math._

/**
 * Representation of a mutable or immutable 2D vector.
 */
trait Vector2[T] extends Vector[Vector2, T] {

  /**
   * The x value of this vector.
   */
  def x: Float

  /**
   * The y value of this vector.
   */
  def y: Float

  /**
   * Converts this vector to the mutable equivalent.<br>
   * If this vector is already mutable, returns the same instance.
   */
  def toMutable: MutableVector2

  /**
   * Converts this vector to the immutable equivalent.<br>
   * If this vector is already immutable, returns the same instance.
   */
  def toImmutable: ImmutableVector2

  /**
   * Converts this vector to a string in the format (x,y).
   */
  def asString: String = s"($x,$y)"

  override def equals(obj: Any): Boolean = {
    def check(a: Float, b: Float) = NumberUtils.floatToIntBits(a) == NumberUtils.floatToIntBits(b)
    obj match {
      case Vector2(x, y) => check(this.x, x) && check(this.y, y)
      case _ => false
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  override def hashCode(): Int = {
    var hash = 1
    hash = 31 * hash + NumberUtils.floatToIntBits(x)
    hash = 31 * hash + NumberUtils.floatToIntBits(y)
    hash
  }

  /**
   * Creates a copy of this vector, using the given values.
   */
  def copy(x: Float = this.x, y: Float = this.y): T

  /**
   * Adds this vector with the given (x, y) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def add(x: Float, y: Float): T

  /**
   * Subtracts this vector by the given (x, y) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(x: Float, y: Float): T

  /**
   * Multiplies this vector with the given (x, y) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def scl(x: Float, y: Float): T

  /**
   * Divides this vector by the given (x, y) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def div(x: Float, y: Float): T

  /**
   * Left-multiplies this vector by the given matrix.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(matrix: Matrix3): T

  /**
   * The cross product between this and the given (x, y) values.
   */
  def crs(x: Float, y: Float): Float = this.x * y - this.y * x

  /**
   * The cross product between this and the given vector.
   */
  def crs(vector: Vector2[_]): Float = crs(vector.x, vector.y)

  /**
   * The angle in degrees of this vector relative to the given vector.<br>
   * Angles are towards the positive y-axis (typically counter-clockwise) in the [0, 360] range.
   */
  def angleDeg(vector: Vector2[_]): Float = {
    val angle = atan2(vector.crs(this).toDouble, vector.dot(this).toDouble).toFloat * MathUtils.radiansToDegrees
    if (angle < 0f) angle + 360f
    else angle
  }

  /**
   * The angle in degrees of this vector relative to the x-axis.<br>
   * Angles are towards the positive y-axis (typically counter-clockwise) in the [0, 360] range.
   */
  def angleDeg: Float = {
    val angle = atan2(y.toDouble, x.toDouble).toFloat * MathUtils.radiansToDegrees
    if (angle < 0f) angle + 360f
    else angle
  }

  /**
   * The angle in radians of this vector relative to the given vector.<br>
   * Angles are towards the positive y-axis (typically counter-clockwise).
   */
  def angleRad(vector: Vector2[_]): Float = atan2(vector.crs(this).toDouble, vector.dot(this).toDouble).toFloat

  /**
   * The angle in radians of this vector relative to the x-axis.<br>
   * Angles are towards the positive y-axis (typically counter-clockwise).
   */
  def angleRad: Float = atan2(y.toDouble, x.toDouble).toFloat

  /**
   * Sets the angle in degrees relative to the x-axis, towards the positive y-axis (typically counter-clockwise).
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def setAngleDeg(value: Float): T = setAngleRad(value * MathUtils.degreesToRadians)

  /**
   * Sets the angle in radians relative to the x-axis, towards the positive y-axis (typically counter-clockwise).
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def setAngleRad(value: Float): T

  /**
   * Rotates the vector by the given angle in degrees, counter-clockwise assuming the y-axis points up.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotateDeg(value: Float): T = rotateRad(value * MathUtils.degreesToRadians)

  /**
   * Rotates the vector by the given angle in radians, counter-clockwise assuming the y-axis points up.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotateRad(value: Float): T

  /**
   * Rotates the vector by the given angle in degrees around the given vector,
   * counter-clockwise assuming the y-axis points up.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotateAroundDeg(vector: Vector2[_], value: Float): T

  /**
   * Rotates the vector by the given angle in radians around the given vector,
   * counter-clockwise assuming the y-axis points up.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotateAroundRad(vector: Vector2[_], value: Float): T

  /**
   * Rotates the vector by 90 degrees in the specified direction,
   * where >= 0 is counter-clockwise and <0 is clockwise.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotate90(direction: Int): T

  /**
   * Compares this vector with the given vector, using the supplied epsilon for fuzzy equality testing.
   */
  def epsilonEquals(x: Float, y: Float, epsilon: Float): Boolean =
    !(abs((x - this.x).toDouble).toFloat > epsilon) && !(abs((y - this.y).toDouble).toFloat > epsilon)

  /**
   * Compares this vector with the given vector, using MathUtils for fuzzy equality testing.
   */
  def epsilonEquals(x: Float, y: Float): Boolean = epsilonEquals(x, y, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * @inheritdoc
   */
  def epsilonEquals(vector: Vector2[_], epsilon: Float): Boolean = epsilonEquals(vector.x, vector.y, epsilon)

  /**
   * Compares this vector with the given vector, using MathUtils for fuzzy equality testing.
   */
  def epsilonEquals(vector: Vector2[_]): Boolean = epsilonEquals(vector, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * @inheritdoc
   */
  def interpolate(vector: Vector2[_], alpha: Float Refined Alpha, interpolation: Interpolation): T =
    lerp(vector, Refined.unsafeApply(interpolation(alpha)))

  /**
   * @inheritdoc
   */
  def isUnit: Boolean = isUnit(0.000000001f)

  /**
   * @inheritdoc
   */
  def isUnit(margin: Float): Boolean = abs(len2 - 1f) < margin

  /**
   * @inheritdoc
   */
  def isZero: Boolean = x == 0 && y == 0

  /**
   * @inheritdoc
   */
  def isZero(margin: Float): Boolean = len2 < margin

  /**
   * @inheritdoc
   */
  def isOnLine(vector: Vector2[_]): Boolean = MathUtils.isZero(x * vector.y - y * vector.x)

  /**
   * @inheritdoc
   */
  def isOnLine(vector: Vector2[_], epsilon: Float): Boolean = MathUtils.isZero(x * vector.y - y * vector.x, epsilon)

  /**
   * @inheritdoc
   */
  def isCollinear(vector: Vector2[_]): Boolean = isOnLine(vector) && dot(vector) > 0f

  /**
   * @inheritdoc
   */
  def isCollinear(vector: Vector2[_], epsilon: Float): Boolean = isOnLine(vector, epsilon) && dot(vector) > 0f

  /**
   * @inheritdoc
   */
  def isCollinearOpposite(vector: Vector2[_]): Boolean = isOnLine(vector) && dot(vector) < 0f

  /**
   * @inheritdoc
   */
  def isCollinearOpposite(vector: Vector2[_], epsilon: Float): Boolean = isOnLine(vector, epsilon) && dot(vector) < 0f

  /**
   * @inheritdoc
   */
  def isPerpendicular(vector: Vector2[_]): Boolean = MathUtils.isZero(dot(vector))

  /**
   * @inheritdoc
   */
  def isPerpendicular(vector: Vector2[_], epsilon: Float): Boolean = MathUtils.isZero(dot(vector), epsilon)

  /**
   * @inheritdoc
   */
  def hasSameDirection(vector: Vector2[_]): Boolean = dot(vector) > 0f

  /**
   * @inheritdoc
   */
  def hasOppositeDirection(vector: Vector2[_]): Boolean = dot(vector) < 0f

  /**
   * @inheritdoc
   */
  def len: Float = Vector2.len(x, y)

  /**
   * @inheritdoc
   */
  def len2: Float = Vector2.len2(x, y)

  /**
   * @inheritdoc
   */
  def limit(value: Float): T = limit2(value * value)

  /**
   * @inheritdoc
   */
  def setLength(value: Float): T = setLength2(value * value)

  /**
   * @inheritdoc
   */
  def sub(value: Float): T = sub(value, value)

  /**
   * @inheritdoc
   */
  def sub(vector: Vector2[_]): T = sub(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  def add(value: Float): T = add(value, value)

  /**
   * @inheritdoc
   */
  def add(vector: Vector2[_]): T = add(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  def scl(value: Float): T = scl(value, value)

  /**
   * @inheritdoc
   */
  def scl(vector: Vector2[_]): T = scl(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  def div(value: Float): T = div(value, value)

  /**
   * @inheritdoc
   */
  def div(vector: Vector2[_]): T = div(vector.x, vector.y)

  /**
   * Distance between this vector and the given (x, y) values.
   */
  def dst(x: Float, y: Float): Float = Vector2.dst(this.x, this.y, x, y)

  /**
   * Squared distance between this vector and the given (x, y) values.
   */
  def dst2(x: Float, y: Float): Float = Vector2.dst2(this.x, this.y, x, y)

  /**
   * @inheritdoc
   */
  def dst(vector: Vector2[_]): Float = Vector2.dst(x, y, vector.x, vector.y)

  /**
   * @inheritdoc
   */
  def dst2(vector: Vector2[_]): Float = Vector2.dst2(x, y, vector.x, vector.y)

  /**
   * The dot product between this and the given (x, y) values.
   */
  def dot(x: Float, y: Float): Float = Vector2.dot(this.x, this.y, x, y)

  /**
   * @inheritdoc
   */
  def dot(vector: Vector2[_]): Float = Vector2.dot(x, y, vector.x, vector.y)

  /**
   * @inheritdoc
   */
  def compare(that: Vector2[_]): Int = len2.compare(that.len2)
}

object Vector2 {

  /**
   * Refined predicate for strings in the format (x,y).
   */
  type XY = MatchesRegex[W.`"""\\(\\s*[+-]?(\\d*\\.)?\\d+\\s*,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\)"""`.T]

  /**
   * Creates an immutable [[Vector2]] using the given (x, y) values.
   */
  def apply(x: Float = 0f, y: Float = 0f): Vector2[ImmutableVector2] = ImmutableVector2(x, y)

  /**
   * Destructure the [[Vector2]] for pattern-matching.
   */
  def unapply(vector: Vector2[_]): Option[(Float, Float)] = Some(vector.x -> vector.y)

  /**
   * Creates an immutable [[Vector2]] from a string with the format (x,y).
   */
  def fromString(value: String Refined XY): Vector2[ImmutableVector2] = ImmutableVector2.fromString(value)

  /**
   * The euclidean length.
   */
  def len(x: Float, y: Float): Float = JVector2.len(x, y)

  /**
   * The squared euclidean length. Faster performance than [[len]].
   */
  def len2(x: Float, y: Float): Float = JVector2.len2(x, y)

  /**
   * Distance between (x1, y1) and (x2, y2)
   */
  def dst(x1: Float, y1: Float, x2: Float, y2: Float): Float = JVector2.dst(x1, y1, x2, y2)

  /**
   * Squared distance between (x1, y1) and (x2, y2). Faster performance than [[dst]].
   */
  def dst2(x1: Float, y1: Float, x2: Float, y2: Float): Float = JVector2.dst2(x1, y1, x2, y2)

  /**
   * The dot product between the first (x, y) coordinate and the second (x, y) coordinate.
   */
  def dot(x1: Float, y1: Float, x2: Float, y2: Float): Float = JVector2.dot(x1, y1, x2, y2)
}
