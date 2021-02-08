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
package scalagdx.math.syntax

import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.Size
import scalagdx.math.ImmutableVector3
import scalagdx.math.MutableVector3
import scalagdx.math.Vector3

trait Vector3Syntax {

  implicit final def mutableVector3Ops(vector: MutableVector3): MutableVector3Ops = new MutableVector3Ops(vector)

  implicit final def mutableVector3Ops(vector: Vector3[MutableVector3]): MutableVector3Ops =
    new MutableVector3Ops(vector.toMutable)

  implicit class MutableVector3Instance(instance: MutableVector3) extends Vector3[MutableVector3] {

    override def toString(): String = instance.toString

    /**
     * @inheritdoc
     */
    override def limit2(value: Float): MutableVector3 = instance.limit2(value)

    /**
     * @inheritdoc
     */
    override def setLength2(value: Float): MutableVector3 = instance.setLength2(value)

    /**
     * @inheritdoc
     */
    override def clamp(min: Float, max: Float): MutableVector3 = instance.clamp(min, max)

    /**
     * @inheritdoc
     */
    override def -(vector: Vector3[_]): MutableVector3 = copy().sub(vector)

    /**
     * @inheritdoc
     */
    override def +(vector: Vector3[_]): MutableVector3 = copy().add(vector)

    /**
     * @inheritdoc
     */
    override def *(vector: Vector3[_]): MutableVector3 = copy().scl(vector)

    /**
     * @inheritdoc
     */
    override def /(vector: Vector3[_]): MutableVector3 = copy().div(vector)

    /**
     * @inheritdoc
     */
    override def nor: MutableVector3 = instance.nor

    /**
     * @inheritdoc
     */
    override def x: Float = instance.x

    /**
     * @inheritdoc
     */
    override def y: Float = instance.y

    /**
     * @inheritdoc
     */
    override def z: Float = instance.z

    /**
     * @inheritdoc
     */
    override def toMutable: MutableVector3 = instance

    /**
     * @inheritdoc
     */
    override def toImmutable: ImmutableVector3 = ImmutableVector3(instance.x, instance.y, instance.z)

    /**
     * @inheritdoc
     */
    override def copy(x: Float = instance.x, y: Float = instance.y, z: Float = instance.z): MutableVector3 =
      MutableVector3(x, y, z)

    /**
     * @inheritdoc
     */
    override def add(x: Float, y: Float, z: Float): MutableVector3 = instance.add(x, y, z)

    /**
     * @inheritdoc
     */
    override def sub(x: Float, y: Float, z: Float): MutableVector3 = instance.sub(x, y, z)

    /**
     * @inheritdoc
     */
    override def scl(x: Float, y: Float, z: Float): MutableVector3 = instance.scl(x, y, z)

    /**
     * @inheritdoc
     */
    override def div(x: Float, y: Float, z: Float): MutableVector3 = instance.set(
      instance.x / x,
      instance.y / y,
      instance.z / z
    )

    /**
     * @inheritdoc
     */
    override def crs(x: Float, y: Float, z: Float): MutableVector3 = instance.crs(x, y, z)

    /**
     * @inheritdoc
     */
    override def mul4x3(matrix: Seq[Float] Refined Size[W.`12`.T]): MutableVector3 =
      instance.mul4x3(matrix.value.toArray)

    /**
     * @inheritdoc
     */
    override def mul(matrix: Matrix4): MutableVector3 = instance.mul(matrix)

    /**
     * @inheritdoc
     */
    override def mul(matrix: Matrix3): MutableVector3 = instance.mul(matrix)

    /**
     * @inheritdoc
     */
    override def mul(quaternion: Quaternion): MutableVector3 = instance.mul(quaternion)

    /**
     * @inheritdoc
     */
    override def traMul(matrix: Matrix4): MutableVector3 = instance.traMul(matrix)

    /**
     * @inheritdoc
     */
    override def traMul(matrix: Matrix3): MutableVector3 = instance.traMul(matrix)

    /**
     * @inheritdoc
     */
    override def prj(matrix: Matrix4): MutableVector3 = instance.prj(matrix)

    /**
     * @inheritdoc
     */
    override def rot(matrix: Matrix4): MutableVector3 = instance.rot(matrix)

    /**
     * @inheritdoc
     */
    override def unrotate(matrix: Matrix4): MutableVector3 = instance.unrotate(matrix)

    /**
     * @inheritdoc
     */
    override def untransform(matrix: Matrix4): MutableVector3 = instance.untransform(matrix)

    /**
     * @inheritdoc
     */
    override def slerp(
        vector: Vector3[_],
        alpha: Refined[Float, scalagdx.math.Vector.Alpha]
    ): MutableVector3 = instance.slerp(vector.toMutable, alpha.value)

  }
}

final class MutableVector3Ops(private val instance: MutableVector3) extends AnyVal {

  /**
   * Mutates this vector by adding this vector with the given vector.
   */
  def +=(vector: MutableVector3): Unit = {
    instance.add(vector)
    ()
  }

  /**
   * Mutates this vector by adding this vector with the given vector.
   */
  def +=(vector: Vector3[_]): Unit = {
    instance.add(vector.x, vector.y, vector.z)
    ()
  }

  /**
   * Mutates this vector by subtracting this vector by the given vector.
   */
  def -=(vector: MutableVector3): Unit = {
    instance.sub(vector)
    ()
  }

  /**
   * Mutates this vector by subtracting this vector by the given vector.
   */
  def -=(vector: Vector3[_]): Unit = {
    instance.sub(vector.x, vector.y, vector.z)
    ()
  }

  /**
   * Mutates this vector by multiplying this vector with the given vector.
   */
  def *=(vector: MutableVector3): Unit = {
    instance.scl(vector)
    ()
  }

  /**
   * Mutates this vector by multiplying this vector with the given vector.
   */
  def *=(vector: Vector3[_]): Unit = {
    instance.scl(vector.x, vector.y, vector.z)
    ()
  }

  private def div(vector: MutableVector3, x: Float, y: Float, z: Float): Unit = {
    vector.set(vector.x / x, vector.y / y, vector.z / z)
    ()
  }

  /**
   * Mutates this vector by dividing this vector by the given vector.
   */
  def /=(vector: MutableVector3): Unit = div(instance, vector.x, vector.y, vector.z)

  /**
   * Mutates this vector by dividing this vector by the given vector.
   */
  def /=(vector: Vector3[_]): Unit = div(instance, vector.x, vector.y, vector.z)

  /**
   * Adds this vector with the given vector, returning a new instance containing the result.
   */
  def +(vector: MutableVector3): MutableVector3 = instance.cpy().add(vector)

  /**
   * Subtracts this vector by the given vector, returning a new instance containing the result.
   */
  def -(vector: MutableVector3): MutableVector3 = instance.cpy().sub(vector)

  /**
   * Multiplies this vector with the given vector, returning a new instance containing the result.
   */
  def *(vector: MutableVector3): MutableVector3 = instance.cpy().scl(vector)

  /**
   * Divides this vector by the given vector, returning a new instance containing the result.
   */
  def /(vector: MutableVector3): MutableVector3 = {
    val result = instance.cpy()
    div(result, vector.x, vector.y, vector.z)
    result
  }
}
