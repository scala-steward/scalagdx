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

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.Size
import scalagdx.math.syntax.vector3._

import math._

/**
 * Representation of an immutable 3D vector.
 */
final case class ImmutableVector3(x: Float = 0f, y: Float = 0f, z: Float = 0f) extends Vector3[ImmutableVector3] {

  override def hashCode: Int = super.hashCode

  /**
   * @inheritdoc
   */
  override def toMutable: MutableVector3 = MutableVector3(x, y, z)

  /**
   * @inheritdoc
   */
  override def toImmutable: ImmutableVector3 = this

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
  override def copy(x: Float = this.x, y: Float = this.y, z: Float = this.z): ImmutableVector3 =
    ImmutableVector3(x, y, z)

  /**
   * @inheritdoc
   */
  override def limit2(value: Float): ImmutableVector3 =
    if (len2 > value) scl(sqrt((value / len2).toDouble).toFloat)
    else this

  /**
   * @inheritdoc
   */
  override def setLength2(value: Float): ImmutableVector3 =
    if (len2 == 0 || len2 == value) this
    else scl(sqrt((value / len2).toDouble).toFloat)

  /**
   * @inheritdoc
   */
  override def clamp(min: Float, max: Float): ImmutableVector3 = {
    val len2 = this.len2
    if (len2 == 0f) this
    else {
      val max2 = max * max
      if (len2 > max2) scl(sqrt((max2 / len2).toDouble).toFloat)
      else {
        val min2 = min * min
        if (len2 < min2) scl(sqrt((min2 / len2).toDouble).toFloat)
        else this
      }
    }
  }

  /**
   * @inheritdoc
   */
  override def nor: ImmutableVector3 =
    if (len2 == 0f || len2 == 1f) this
    else scl(1f / sqrt(len2.toDouble).toFloat)

  /**
   * @inheritdoc
   */
  override def -(vector: Vector3[_]): ImmutableVector3 = sub(vector)

  /**
   * @inheritdoc
   */
  override def *(vector: Vector3[_]): ImmutableVector3 = scl(vector)

  /**
   * @inheritdoc
   */
  override def /(vector: Vector3[_]): ImmutableVector3 = div(vector)

  /**
   * @inheritdoc
   */
  override def +(vector: Vector3[_]): ImmutableVector3 = add(vector)

  /**
   * @inheritdoc
   */
  override def add(x: Float, y: Float, z: Float): ImmutableVector3 = ImmutableVector3(
    x = this.x + x,
    y = this.y + y,
    z = this.z + z
  )

  /**
   * @inheritdoc
   */
  override def sub(x: Float, y: Float, z: Float): ImmutableVector3 = ImmutableVector3(
    x = this.x - x,
    y = this.y - y,
    z = this.z - z
  )

  /**
   * @inheritdoc
   */
  override def scl(x: Float, y: Float, z: Float): ImmutableVector3 = ImmutableVector3(
    x = this.x * x,
    y = this.y * y,
    z = this.z * z
  )

  /**
   * @inheritdoc
   */
  override def div(x: Float, y: Float, z: Float): ImmutableVector3 = ImmutableVector3(
    x = this.x / x,
    y = this.y / y,
    z = this.z / z
  )

  /**
   * @inheritdoc
   */
  override def crs(x: Float, y: Float, z: Float): ImmutableVector3 = ImmutableVector3(
    x = this.y * z - this.z * y,
    y = this.z * x - this.x * z,
    z = this.x * y - this.y * x
  )

  /**
   * @inheritdoc
   */
  override def mul4x3(matrix: Seq[Float] Refined Size[W.`12`.T]): ImmutableVector3 = ImmutableVector3(
    x = x * matrix(0) + y * matrix(3) + z * matrix(6) + matrix(9),
    y = x * matrix(1) + y * matrix(4) + z * matrix(7) + matrix(10),
    z = x * matrix(2) + y * matrix(5) + z * matrix(8) + matrix(11)
  )

  /**
   * @inheritdoc
   */
  override def mul(matrix: Matrix4): ImmutableVector3 = ImmutableVector3(
    x = x * matrix.`val`(Matrix4.M00) + y * matrix.`val`(Matrix4.M01) +
      z * matrix.`val`(Matrix4.M02) + matrix.`val`(Matrix4.M03),
    y = x * matrix.`val`(Matrix4.M10) + y * matrix.`val`(Matrix4.M11) +
      z * matrix.`val`(Matrix4.M12) + matrix.`val`(Matrix4.M13),
    z = x * matrix.`val`(Matrix4.M20) + y * matrix.`val`(Matrix4.M21) +
      z * matrix.`val`(Matrix4.M22) + matrix.`val`(Matrix4.M23)
  )

  /**
   * @inheritdoc
   */
  override def mul(matrix: Matrix3): ImmutableVector3 = ImmutableVector3(
    x = x * matrix.`val`(Matrix3.M00) + y * matrix.`val`(Matrix3.M01) + z * matrix.`val`(Matrix3.M02),
    y = x * matrix.`val`(Matrix3.M10) + y * matrix.`val`(Matrix3.M11) + z * matrix.`val`(Matrix3.M12),
    z = x * matrix.`val`(Matrix3.M20) + y * matrix.`val`(Matrix3.M21) + z * matrix.`val`(Matrix3.M22)
  )

  /**
   * @inheritdoc
   */
  override def mul(quaternion: Quaternion): ImmutableVector3 = quaternion.transform(toMutable).toImmutable

  /**
   * @inheritdoc
   */
  override def traMul(matrix: Matrix4): ImmutableVector3 = ImmutableVector3(
    x = x * matrix.`val`(Matrix4.M00) + y * matrix.`val`(Matrix4.M01) +
      z * matrix.`val`(Matrix4.M02) + matrix.`val`(Matrix4.M03),
    y = x * matrix.`val`(Matrix4.M10) + y * matrix.`val`(Matrix4.M11) +
      z * matrix.`val`(Matrix4.M12) + matrix.`val`(Matrix4.M13),
    z = x * matrix.`val`(Matrix4.M20) + y * matrix.`val`(Matrix4.M21) +
      z * matrix.`val`(Matrix4.M22) + matrix.`val`(Matrix4.M23)
  )

  /**
   * @inheritdoc
   */
  override def traMul(matrix: Matrix3): ImmutableVector3 = ImmutableVector3(
    x = x * matrix.`val`(Matrix3.M00) + y * matrix.`val`(Matrix3.M10) +
      z * matrix.`val`(Matrix3.M20),
    y = x * matrix.`val`(Matrix3.M01) + y * matrix.`val`(Matrix3.M11) +
      z * matrix.`val`(Matrix3.M21),
    z = x * matrix.`val`(Matrix3.M02) + y * matrix.`val`(Matrix3.M12) +
      z * matrix.`val`(Matrix3.M22)
  )

  /**
   * @inheritdoc
   */
  override def prj(matrix: Matrix4): ImmutableVector3 = {
    val w = 1f / (x * matrix.`val`(Matrix4.M30) + y * matrix.`val`(Matrix4.M31) +
      z * matrix.`val`(Matrix4.M32) + matrix.`val`(Matrix4.M33))
    ImmutableVector3(
      x = (x * matrix.`val`(Matrix4.M00) + y * matrix.`val`(Matrix4.M01) + z *
        matrix.`val`(Matrix4.M02) + matrix.`val`(Matrix4.M03)) * w,
      y = (x * matrix.`val`(Matrix4.M10) + y * matrix.`val`(Matrix4.M11) + z *
        matrix.`val`(Matrix4.M12) + matrix.`val`(Matrix4.M13)) * w,
      z = (x * matrix.`val`(Matrix4.M20) + y * matrix.`val`(Matrix4.M21) + z *
        matrix.`val`(Matrix4.M22) + matrix.`val`(Matrix4.M23)) * w
    )
  }

  /**
   * @inheritdoc
   */
  override def rot(matrix: Matrix4): ImmutableVector3 = ImmutableVector3(
    x = x * matrix.`val`(Matrix4.M00) + y * matrix.`val`(Matrix4.M01) + z * matrix.`val`(Matrix4.M02),
    y = x * matrix.`val`(Matrix4.M10) + y * matrix.`val`(Matrix4.M11) + z * matrix.`val`(Matrix4.M12),
    z = x * matrix.`val`(Matrix4.M20) + y * matrix.`val`(Matrix4.M21) + z * matrix.`val`(Matrix4.M22)
  )

  /**
   * @inheritdoc
   */
  override def unrotate(matrix: Matrix4): ImmutableVector3 = ImmutableVector3(
    x = x * matrix.`val`(Matrix4.M00) + y * matrix.`val`(Matrix4.M10) + z * matrix.`val`(Matrix4.M20),
    y = x * matrix.`val`(Matrix4.M01) + y * matrix.`val`(Matrix4.M11) + z * matrix.`val`(Matrix4.M21),
    z = x * matrix.`val`(Matrix4.M02) + y * matrix.`val`(Matrix4.M12) + z * matrix.`val`(Matrix4.M22)
  )

  /**
   * @inheritdoc
   */
  override def untransform(matrix: Matrix4): ImmutableVector3 = {
    val x = this.x - matrix.`val`(Matrix4.M03)
    val y = this.y - matrix.`val`(Matrix4.M03)
    val z = this.z - matrix.`val`(Matrix4.M03)
    ImmutableVector3(
      x = x * matrix.`val`(Matrix4.M00) + y * matrix.`val`(Matrix4.M10) + z * matrix.`val`(Matrix4.M20),
      y = x * matrix.`val`(Matrix4.M01) + y * matrix.`val`(Matrix4.M11) + z * matrix.`val`(Matrix4.M21),
      z = x * matrix.`val`(Matrix4.M02) + y * matrix.`val`(Matrix4.M12) + z * matrix.`val`(Matrix4.M22)
    )
  }

  /**
   * @inheritdoc
   */
  override def slerp(vector: Vector3[_], alpha: Refined[Float, Vector.Alpha]): ImmutableVector3 = {
    val dot = this.dot(vector)
    if (dot > 0.9995f || dot < -0.9995f) lerp(vector, alpha)
    else {
      val theta0: Float = acos(dot.toDouble).toFloat
      val theta = theta0 * alpha.value
      val st = sin(theta.toDouble).toFloat
      val tx = vector.x - x * dot
      val ty = vector.y - y * dot
      val tz = vector.z - z * dot
      val l2 = tx * tx + ty * ty + tz * tz
      val dl = st * (if (l2 < 0.0001f) 1f else 1f / sqrt(l2.toDouble).toFloat)
      toMutable.scl(cos(theta.toDouble).toFloat).add(tx * dl, ty * dl, tz * dl).nor.toImmutable
    }
  }
}

object ImmutableVector3 {

  import scalagdx.math.Vector3.XYZ

  /**
   * Creates an [[ImmutableVector3]] from the given spherical coordinate.
   *
   * @param azimuthalAngle The angle between x-axis in radians [0, 2pi]
   * @param polarAngle The angle between z-axis in radians [0, pi]
   */
  def fromSpherical(azimuthalAngle: Float, polarAngle: Float): ImmutableVector3 = {
    val sinPolar = MathUtils.sin(polarAngle)
    ImmutableVector3(
      x = MathUtils.cos(azimuthalAngle) * sinPolar,
      y = MathUtils.sin(azimuthalAngle) * sinPolar,
      z = MathUtils.cos(polarAngle)
    )
  }

  /**
   * Creates an [[ImmutableVector3]] from the given string in the format (x,y,z).
   */
  def fromString(value: String Refined XYZ): ImmutableVector3 = {
    val values = value.substring(1, value.length - 1).split(",")
    ImmutableVector3(
      x = values(0).toFloat,
      y = values(1).toFloat,
      z = values(2).toFloat
    )
  }
}
