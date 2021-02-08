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
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import scalagdx.math.ImmutableVector2
import scalagdx.math.MutableVector2
import scalagdx.math.Vector2

trait Vector2Syntax {

  implicit final def mutableVector2Ops(vector: MutableVector2): MutableVector2Ops = new MutableVector2Ops(vector)

  implicit final def mutableVector2Ops(vector: Vector2[MutableVector2]): MutableVector2Ops =
    new MutableVector2Ops(vector.toMutable)

  implicit class MutableVector2Instance(instance: MutableVector2) extends Vector2[MutableVector2] {

    override def toString(): String = instance.toString

    /**
     * @inheritdoc
     */
    override def limit2(value: Float): MutableVector2 = instance.limit2(value)

    /**
     * @inheritdoc
     */
    override def setLength2(value: Float): MutableVector2 = instance.setLength2(value)

    /**
     * @inheritdoc
     */
    override def clamp(min: Float, max: Float): MutableVector2 = instance.clamp(min, max)

    /**
     * @inheritdoc
     */
    override def mulAdd(vector: Vector2[_], scalar: Float): MutableVector2 = instance.set(
      instance.x + (vector.x * scalar),
      instance.y + (vector.y * scalar)
    )

    /**
     * @inheritdoc
     */
    override def mulAdd(vector: Vector2[_], scalar: Vector2[_]): MutableVector2 = instance.set(
      instance.x + (vector.x * scalar.x),
      instance.y + (vector.y * scalar.y)
    )

    /**
     * @inheritdoc
     */
    override def nor: MutableVector2 = instance.nor

    /**
     * @inheritdoc
     */
    override def lerp(vector: Vector2[_], alpha: Refined[Float, scalagdx.math.Vector.Alpha]): MutableVector2 = {
      val invAlpha = 1f - alpha
      instance.set(
        (x * invAlpha) + (vector.x * alpha),
        (y * invAlpha) + (vector.y * alpha)
      )
    }

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
    override def toMutable: MutableVector2 = instance

    /**
     * @inheritdoc
     */
    override def toImmutable: ImmutableVector2 = ImmutableVector2(instance.x, instance.y)

    /**
     * @inheritdoc
     */
    override def copy(x: Float = instance.x, y: Float = instance.y): MutableVector2 = MutableVector2(x, y)

    /**
     * @inheritdoc
     */
    override def add(x: Float, y: Float): MutableVector2 = instance.add(x, y)

    /**
     * @inheritdoc
     */
    override def sub(x: Float, y: Float): MutableVector2 = instance.sub(x, y)

    /**
     * @inheritdoc
     */
    override def scl(x: Float, y: Float): MutableVector2 = instance.scl(x, y)

    /**
     * @inheritdoc
     */
    override def div(x: Float, y: Float): MutableVector2 = instance.set(instance.x / x, instance.y / y)

    /**
     * @inheritdoc
     */
    override def mul(matrix: Matrix3): MutableVector2 = instance.mul(matrix)

    /**
     * @inheritdoc
     */
    override def setAngleRad(value: Float): MutableVector2 = instance.setAngleRad(value)

    /**
     * @inheritdoc
     */
    override def rotateRad(value: Float): MutableVector2 = instance.rotateRad(value)

    /**
     * @inheritdoc
     */
    override def rotateAroundDeg(vector: Vector2[_], value: Float): MutableVector2 =
      sub(vector.x, vector.y).rotateDeg(value).add(vector.x, vector.y)

    /**
     * @inheritdoc
     */
    override def rotateAroundRad(vector: Vector2[_], value: Float): MutableVector2 =
      sub(vector).rotateRad(value).add(vector)

    /**
     * @inheritdoc
     */
    override def rotate90(direction: Int): MutableVector2 = instance.rotate90(direction)

    /**
     * @inheritdoc
     */
    override def +(vector: Vector2[_]): MutableVector2 = copy().add(vector)

    /**
     * @inheritdoc
     */
    override def -(vector: Vector2[_]): MutableVector2 = copy().sub(vector)

    /**
     * @inheritdoc
     */
    override def *(vector: Vector2[_]): MutableVector2 = copy().scl(vector)

    /**
     * @inheritdoc
     */
    override def /(vector: Vector2[_]): MutableVector2 = copy().div(vector)
  }
}

final class MutableVector2Ops(private val instance: MutableVector2) extends AnyVal {

  /**
   * Mutates this vector by adding this vector with the given vector.
   */
  def +=(vector: MutableVector2): Unit = {
    instance.add(vector)
    ()
  }

  /**
   * Mutates this vector by adding this vector with the given vector.
   */
  def +=(vector: Vector2[_]): Unit = {
    instance.add(vector.x, vector.y)
    ()
  }

  /**
   * Mutates this vector by subtracting this vector by the given vector.
   */
  def -=(vector: MutableVector2): Unit = {
    instance.sub(vector)
    ()
  }

  /**
   * Mutates this vector by subtracting this vector by the given vector.
   */
  def -=(vector: Vector2[_]): Unit = {
    instance.sub(vector.x, vector.y)
    ()
  }

  /**
   * Mutates this vector by multiplying this vector with the given vector.
   */
  def *=(vector: MutableVector2): Unit = {
    instance.scl(vector)
    ()
  }

  /**
   * Mutates this vector by multiplying this vector with the given vector.
   */
  def *=(vector: Vector2[_]): Unit = {
    instance.scl(vector.x, vector.y)
    ()
  }

  private def div(vector: MutableVector2, x: Float, y: Float): Unit = {
    vector.set(vector.x / x, vector.y / y)
    ()
  }

  /**
   * Mutates this vector by dividing this vector by the given vector.
   */
  def /=(vector: MutableVector2): Unit = div(instance, vector.x, vector.y)

  /**
   * Mutates this vector by dividing this vector by the given vector.
   */
  def /=(vector: Vector2[_]): Unit = div(instance, vector.x, vector.y)

  /**
   * Adds this vector with the given vector, returning a new instance containing the result.
   */
  def +(vector: MutableVector2): MutableVector2 = instance.cpy().add(vector)

  /**
   * Subtracts this vector by the given vector, returning a new instance containing the result.
   */
  def -(vector: MutableVector2): MutableVector2 = instance.cpy().sub(vector)

  /**
   * Multiplies this vector with the given vector, returning a new instance containing the result.
   */
  def *(vector: MutableVector2): MutableVector2 = instance.cpy().scl(vector)

  /**
   * Divides this vector by the given vector, returning a new instance containing the result.
   */
  def /(vector: MutableVector2): MutableVector2 = {
    val result = instance.cpy()
    div(result, vector.x, vector.y)
    result
  }
}
