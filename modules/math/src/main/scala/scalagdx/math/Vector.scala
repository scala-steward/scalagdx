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
package scalagdx.math

import com.badlogic.gdx.math.Interpolation
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.GreaterEqual
import eu.timepit.refined.numeric.LessEqual
import scalagdx.math.Vector.Alpha

/**
 * Generic representation of a mutable or immutable vector.
 */
trait Vector[V[_], T] extends Ordered[V[_]] {

  /**
   * The euclidean length.
   */
  def len: Float

  /**
   * The squared euclidean length. Faster performance than [[len]], good for comparisons.
   */
  def len2: Float

  /**
   * Limits the length of this vector to the given value.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def limit(value: Float): T

  /**
   * Limits the length of this vector, based on the desired maximum length squared.
   * Faster performance than [[limit]].
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def limit2(value: Float): T

  /**
   * Sets the length of the vector using the given value. Does nothing if this vector is zero.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def setLength(value: Float): T

  /**
   * Sets the length of this vector, using the given value, squared. Does nothing if this vector is zero
   * Faster performance than [[setLength]]
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def setLength2(value: Float): T

  /**
   *  Clamps this vector's length to the given min and max values.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def clamp(min: Float, max: Float): T

  /**
   * Subtracts this vector by the given value.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(value: Float): T

  /**
   * Subtracts this vector by the given vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def sub(vector: V[_]): T

  /**
   * Subtracts this vector by the given vector, returning a new instance containing the result.
   */
  def -(vector: V[_]): T

  /**
   * Adds this vector with the given value.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def add(value: Float): T

  /**
   * Adds this vector with the given vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def add(vector: V[_]): T

  /**
   * Adds this vector with the given vector, returning a new instance containing the result.
   */
  def +(vector: V[_]): T

  /**
   * Multiplies this vector with the given value.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def scl(value: Float): T

  /**
   * Multiplies this vector with the given vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def scl(vector: V[_]): T

  /**
   * Multiplies this vector with the given vector, returning a new instance containing the result.
   */
  def *(vector: V[_]): T

  /**
   * Divides this vector by the given value.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def div(value: Float): T

  /**
   * Divides this vector by the given vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def div(vector: V[_]): T

  /**
   * Divides this vector by the given vector, returning a new instance containing the result.
   */
  def /(vector: V[_]): T

  /**
   * Multiplies the given vector by the given scalar value, then add it to this vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mulAdd(vector: V[_], scalar: Float): T

  /**
   * Multiplies the given vector by the given scalar vector, then add it to this vector.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def mulAdd(vector: V[_], scalar: V[_]): T

  /**
   * Normalizes this vector. Does nothing if this vector is zero.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def nor: T

  /**
   * Distance between this vector and the given vector.
   */
  def dst(vector: V[_]): Float

  /**
   * Squared distance between this vector and the given vector.
   * Faster performance than [[dst]].
   */
  def dst2(vector: V[_]): Float

  /**
   * Linearly interpolates between this vector and the target vector by alpha.
   *
   * @param vector The target vector.
   * @param alpha The interpolation coefficient, in the range [0, 1].
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def lerp(vector: V[_], alpha: Float Refined Alpha): T

  /**
   * Interpolates between this vector and the target vector by alpha.
   *
   * @param vector The target vector.
   * @param alpha The interpolation coefficient, in the range [0, 1].
   * @param interpolation The interpolation method.
   *
   * @return New instance if using an immutable vector. Otherwise returns the same instance to allow chaining methods.
   */
  def interpolate(vector: V[_], alpha: Float Refined Alpha, interpolation: Interpolation): T

  /**
   * Check if this vector is a unit length vector.
   */
  def isUnit: Boolean

  /**
   * Check if this vector is a unit length vector within the given margin.
   */
  def isUnit(margin: Float): Boolean

  /**
   * Check if this vector is a zero vector.
   */
  def isZero: Boolean

  /**
   * Check if this vector is a zero vector within the given margin.
   */
  def isZero(margin: Float): Boolean

  /**
   * Check if this vector is in line with the given vector (either in the same or the opposite direction).
   */
  def isOnLine(vector: V[_], epsilon: Float): Boolean

  /**
   * Check if this vector is in line with the given vector (either in the same or the opposite direction).
   */
  def isOnLine(vector: V[_]): Boolean

  /**
   * Check if this vector is collinear with the given vector.
   */
  def isCollinear(vector: V[_]): Boolean

  /**
   * Check if this vector is collinear with the given vector.
   */
  def isCollinear(vector: V[_], epsilon: Float): Boolean

  /**
   * Check if this vector is opposite collinear with the given vector.
   */
  def isCollinearOpposite(vector: V[_]): Boolean

  /**
   * Check if this vector is opposite collinear with the given vector.
   */
  def isCollinearOpposite(vector: V[_], epsilon: Float): Boolean

  /**
   * Check if this vector is perpendicular to the given vector.
   */
  def isPerpendicular(vector: V[_]): Boolean

  /**
   * Check if this vector is perpendicular to the given vector.
   */
  def isPerpendicular(vector: V[_], epsilon: Float): Boolean

  /**
   * Check if this vector has a similar direction to the given vector.
   */
  def hasSameDirection(vector: V[_]): Boolean

  /**
   * Check if this vector has the opposite direction to the given vector.
   */
  def hasOppositeDirection(vector: V[_]): Boolean

  /**
   * Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
   */
  def epsilonEquals(vector: V[_], epsilon: Float): Boolean

  /**
   * The dot product between this and the given vector.
   */
  def dot(vector: V[_]): Float

  /**
   * Type safe equality comparison.
   */
  def ===(vector: V[_]): Boolean = equals(vector)

  /**
   * Type safe inequality comparison.
   */
  def =!=(vector: V[_]): Boolean = !equals(vector)
}

object Vector {

  /**
   * Refined predicate which clamps a number to the range [0f, 1f].
   */
  type Alpha = GreaterEqual[W.`0f`.T] And LessEqual[W.`1f`.T]
}
