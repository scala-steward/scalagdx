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
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.{Vector3 => JVector3}
import com.badlogic.gdx.utils.NumberUtils
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.Size
import eu.timepit.refined.string.MatchesRegex

import math._

/**
 * Representation of a mutable or immutable 3D vector.
 */
trait Vector3[T] extends Vector[Vector3, T] {

  import scalagdx.math.Vector3.internalMatrix

  /**
   * The x value of this vector.
   */
  def x: Float

  /**
   * The y value of this vector.
   */
  def y: Float

  /**
   * The z value of this vector.
   */
  def z: Float

  /**
   * Converts this vector to the mutable equivalent.<br>
   * If this vector is already mutable, returns the same instance.
   */
  def toMutable: MutableVector3

  /**
   * Converts this vector to the immutable equivalent.<br>
   * If this vector is already immutable, returns the same instance.
   */
  def toImmutable: ImmutableVector3

  /**
   * Converts this vector to a string in the format (x,y,z).
   */
  def asString: String = s"($x,$y,$z)"

  override def equals(obj: Any): Boolean = {
    def check(a: Float, b: Float) = NumberUtils.floatToIntBits(a) == NumberUtils.floatToIntBits(b)
    obj match {
      case Vector3(x, y, z) => check(this.x, x) && check(this.y, y) && check(this.z, z)
      case _ => false
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  override def hashCode(): Int = {
    var hash = 1
    hash = 31 * hash + NumberUtils.floatToIntBits(x)
    hash = 31 * hash + NumberUtils.floatToIntBits(y)
    hash = 31 * hash + NumberUtils.floatToIntBits(z)
    hash
  }

  /**
   * Creates a copy of this vector, using the given values.
   */
  def copy(x: Float = this.x, y: Float = this.y, z: Float = this.z): T

  /**
   * Compares this vector with the given (x, y, z) values, using the supplied epsilon for fuzzy equality testing.
   */
  def epsilonEquals(x: Float, y: Float, z: Float, epsilon: Float): Boolean =
    !(abs((x - this.x).toDouble).toFloat > epsilon) &&
      !(abs((y - this.y).toDouble).toFloat > epsilon) &&
      !(abs((z - this.z).toDouble).toFloat > epsilon)

  /**
   * Compares this vector with the given (x, y, z) values, using MathUtils for fuzzy equality testing.
   */
  def epsilonEquals(x: Float, y: Float, z: Float): Boolean = epsilonEquals(x, y, z, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * @inheritdoc
   */
  def epsilonEquals(vector: Vector3[_], epsilon: Float): Boolean = epsilonEquals(vector.x, vector.y, vector.z, epsilon)

  /**
   * Compares this vector with the given vector, using MathUtils for fuzzy equality testing.
   */
  def epsilonEquals(vector: Vector3[_]): Boolean = epsilonEquals(vector, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * Adds this vector with the given (x, y, z) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def add(x: Float, y: Float, z: Float): T

  /**
   * @inheritdoc
   */
  def add(value: Float): T = add(value, value, value)

  /**
   * @inheritdoc
   */
  def add(vector: Vector3[_]): T = add(vector.x, vector.y, vector.z)

  /**
   * Subtracts this vector by the given (x, y, z) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(x: Float, y: Float, z: Float): T

  /**
   * @inheritdoc
   */
  def sub(value: Float): T = sub(value, value, value)

  /**
   * @inheritdoc
   */
  def sub(vector: Vector3[_]): T = sub(vector.x, vector.y, vector.z)

  /**
   * Multiplies this vector with the given (x, y, z) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def scl(x: Float, y: Float, z: Float): T

  /**
   * @inheritdoc
   */
  def scl(value: Float): T = scl(value, value, value)

  /**
   * @inheritdoc
   */
  def scl(vector: Vector3[_]): T = scl(vector.x, vector.y, vector.z)

  /**
   * Divides this vector by the given (x, y, z) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def div(x: Float, y: Float, z: Float): T

  /**
   * @inheritdoc
   */
  def div(value: Float): T = div(value, value, value)

  /**
   * @inheritdoc
   */
  def div(vector: Vector3[_]): T = div(vector.x, vector.y, vector.z)

  /**
   * Whether this and the other vector's (x, y, z) values are equal.
   */
  def idt(vector: Vector3[_]): Boolean = x == vector.x && y == vector.y && z == vector.z

  /**
   * Distance between this vector and the given (x, y, z) values.
   */
  def dst(x: Float, y: Float, z: Float): Float = Vector3.dst(this.x, this.y, this.z, x, y, z)

  /**
   * @inheritdoc
   */
  def dst(vector: Vector3[_]): Float = Vector3.dst(x, y, z, vector.x, vector.y, vector.z)

  /**
   * Squared distance between this vector and the given (x, y, z) values.
   * Faster performance than [[dst]].
   */
  def dst2(x: Float, y: Float, z: Float): Float = Vector3.dst2(this.x, this.y, this.z, x, y, z)

  /**
   * @inheritdoc
   */
  def dst2(vector: Vector3[_]): Float = Vector3.dst2(x, y, z, vector.x, vector.y, vector.z)

  /**
   * The dot product between this and the given (x, y, z) values.
   */
  def dot(x: Float, y: Float, z: Float): Float = Vector3.dot(this.x, this.y, this.z, x, y, z)

  /**
   * @inheritdoc
   */
  def dot(vector: Vector3[_]): Float = Vector3.dot(x, y, z, vector.x, vector.y, vector.z)

  /**
   * Calculates cross product between this and the given (x, y, z) values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def crs(x: Float, y: Float, z: Float): T

  /**
   * Calculates the cross product between this and the given vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def crs(vector: Vector3[_]): T = crs(vector.x, vector.y, vector.z)

  /**
   * Left-multiplies the vector by the given 4x3 column major matrix. The matrix  should be composed
   * by a 3x3 matrix representing rotation and scale, plus a 1x3 matrix representing the translation.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mul4x3(matrix: Seq[Float] Refined Size[W.`12`.T]): T

  /**
   * Left-multiplies the vector by the given matrix, assuming the fourth (w) component of the vector is 1.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(matrix: Matrix4): T

  /**
   * Left-multiplies the vector by the given matrix.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(matrix: Matrix3): T

  /**
   * Multiplies the vector by the given quarternion.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mul(quaternion: Quaternion): T

  /**
   * Multiplies the vector by the transpose of the given matrix, assuming the fourth (w) component of the vector is 1.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def traMul(matrix: Matrix4): T

  /**
   * Multiplies the vector by the transpose of the given matrix.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def traMul(matrix: Matrix3): T

  /**
   * Multiplies this vector by the given matrix dividing by w, assuming the fourth (w) component of the vector is 1.
   * This is mostly used to project/unproject vectors via a perspective projection matrix.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def prj(matrix: Matrix4): T

  /**
   * Multiplies this vector by the first three columns of the matrix, essentialy only applying rotation and scaling.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rot(matrix: Matrix4): T

  /**
   * Multiplies this vector by the transpose of the first three columns of the matrix.<br>
   * Note: only works for translation and rotation, does not work for scaling.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def unrotate(matrix: Matrix4): T

  /**
   * Translates this vector in the direction opposite to the translation of the matrix and then multiplies
   * this vector by the transpose of the first three columns of the matrix.<br>
   * Note: only works for translation and rotation, does not work for scaling.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def untransform(matrix: Matrix4): T

  /**
   * Rotates this vector by the given angle in degrees around the given axis.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotate(degrees: Float, axisX: Float, axisY: Float, axisZ: Float): T =
    mul(internalMatrix.setToRotation(axisX, axisY, axisZ, degrees))

  /**
   * Rotates this vector by the given angle in degrees around the given axis.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotate(axis: Vector3[_], degrees: Float): T =
    rotate(degrees, axis.x, axis.y, axis.z)

  /**
   * Rotates this vector by the given angle in radians around the given axis.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotateRad(radians: Float, axisX: Float, axisY: Float, axisZ: Float): T =
    mul(internalMatrix.setToRotationRad(axisX, axisY, axisZ, radians))

  /**
   * Rotates this vector by the given angle in radians around the given axis.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def rotateRad(axis: Vector3[_], radians: Float): T =
    rotateRad(radians, axis.x, axis.y, axis.z)

  /**
   * Spherically interpolates between this vector and the given vector by the given alpha, which is in the range [0,1].
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def slerp(vector: Vector3[_], alpha: Float Refined Vector.Alpha): T

  /**
   * @inheritdoc
   */
  def len: Float = Vector3.len(x, y, z)

  /**
   * @inheritdoc
   */
  def len2: Float = Vector3.len2(x, y, z)

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
  def lerp(vector: Vector3[_], alpha: Refined[Float, Vector.Alpha]): T = add(
    x = alpha * (vector.x - x),
    y = alpha * (vector.y - y),
    z = alpha * (vector.z - z)
  )

  /**
   * @inheritdoc
   */
  def interpolate(vector: Vector3[_], alpha: Refined[Float, Vector.Alpha], interpolation: Interpolation): T =
    lerp(vector, Refined.unsafeApply(interpolation(0f, 1f, alpha)))

  /**
   * @inheritdoc
   */
  def mulAdd(vector: Vector3[_], scalar: Float): T = add(
    x = vector.x * scalar,
    y = vector.y * scalar,
    z = vector.z * scalar
  )

  /**
   * @inheritdoc
   */
  def mulAdd(vector: Vector3[_], scalar: Vector3[_]): T = add(
    x = vector.x * scalar.x,
    y = vector.y * scalar.y,
    z = vector.z * scalar.z
  )

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
  def isZero: Boolean = x == 0f && y == 0f && z == 0f

  /**
   * @inheritdoc
   */
  def isZero(margin: Float): Boolean = len2 < margin

  /**
   * @inheritdoc
   */
  def isOnLine(vector: Vector3[_]): Boolean = isOnLine(vector, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * @inheritdoc
   */
  def isOnLine(vector: Vector3[_], epsilon: Float): Boolean =
    Vector3.len2(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x) <= epsilon

  /**
   * @inheritdoc
   */
  def isCollinear(vector: Vector3[_]): Boolean = isOnLine(vector) && hasSameDirection(vector)

  /**
   * @inheritdoc
   */
  def isCollinear(vector: Vector3[_], epsilon: Float): Boolean = isOnLine(vector, epsilon) && hasSameDirection(vector)

  /**
   * @inheritdoc
   */
  def isCollinearOpposite(vector: Vector3[_]): Boolean = isOnLine(vector) && hasOppositeDirection(vector)

  /**
   * @inheritdoc
   */
  def isCollinearOpposite(vector: Vector3[_], epsilon: Float): Boolean =
    isOnLine(vector, epsilon) && hasOppositeDirection(vector)

  /**
   * @inheritdoc
   */
  def isPerpendicular(vector: Vector3[_]): Boolean = MathUtils.isZero(dot(vector))

  /**
   * @inheritdoc
   */
  def isPerpendicular(vector: Vector3[_], epsilon: Float): Boolean = MathUtils.isZero(dot(vector), epsilon)

  /**
   * @inheritdoc
   */
  def hasSameDirection(vector: Vector3[_]): Boolean = dot(vector) > 0f

  /**
   * @inheritdoc
   */
  def hasOppositeDirection(vector: Vector3[_]): Boolean = dot(vector) < 0f

  /**
   * @inheritdoc
   */
  def compare(that: Vector3[_]): Int = len2.compare(that.len2)
}

object Vector3 {

  /**
   * Used internally for methods requiring a [[Matrix4]] instance.
   */
  private val internalMatrix = new Matrix4()

  /**
   * Refined predicate for strings in the format (x,y,z)
   */
  type XYZ = MatchesRegex[
    W.`"""\\(\\s*[+-]?(\\d*\\.)?\\d+\\s*,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\)"""`.T
  ]

  /**
   * Creates an immutable [[Vector3]] using the given (x, y, z) values.
   */
  def apply(x: Float = 0f, y: Float = 0f, z: Float = 0f): Vector3[ImmutableVector3] = ImmutableVector3(x, y, z)

  /**
   * Destructure the [[Vector3]] for pattern-matching.
   */
  def unapply(vector: Vector3[_]): Option[(Float, Float, Float)] = Some((vector.x, vector.y, vector.z))

  /**
   * Creates an immutable [[Vector3]] from the given spherical coordinate.
   *
   * @param azimuthalAngle The angle between x-axis in radians [0, 2pi]
   * @param polarAngle The angle between z-axis in radians [0, pi]
   */
  def fromSpherical(azimuthalAngle: Float, polarAngle: Float): Vector3[ImmutableVector3] =
    ImmutableVector3.fromSpherical(azimuthalAngle, polarAngle)

  /**
   * Creates an immutable [[Vector3]] from the given string in the format (x,y,z).
   */
  def fromString(value: String Refined XYZ): Vector3[ImmutableVector3] = ImmutableVector3.fromString(value)

  /**
   * The euclidean length.
   */
  def len(x: Float, y: Float, z: Float): Float = JVector3.len(x, y, z)

  /**
   * The squared euclidean length.
   */
  def len2(x: Float, y: Float, z: Float): Float = JVector3.len2(x, y, z)

  /**
   * Distance between the first (x, y, z) coordinate and the second (x, y, z) coordinate.
   */
  def dst(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float =
    JVector3.dst(x1, y1, z1, x2, y2, z2)

  /**
   * Squared distance between the first (x, y, z) coordinate and the second (x, y, z) coordinate.
   */
  def dst2(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float =
    JVector3.dst2(x1, y1, z1, x2, y2, z2)

  /**
   * The dot product between the first (x, y, z) coordinate and the second (x, y, z) coordinate.
   */
  def dot(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float =
    JVector3.dot(x1, y1, z1, x2, y2, z2)
}
