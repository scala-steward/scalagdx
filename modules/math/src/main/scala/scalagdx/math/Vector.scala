package scalagdx.math

import com.badlogic.gdx.math.Interpolation
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric.GreaterEqual
import eu.timepit.refined.numeric.LessEqual
import scalagdx.math.Vector.Alpha

/**
 * Immutable vector representation. All methods that return T gives a new vector.
 */
trait Vector[T <: Vector[T]] {

  /**
   * The euclidean length
   */
  def length: Float

  /**
   * Faster performance by avoiding having to sqrt. Do not use for actual length.
   * Useful for comparisons.
   */
  def length2: Float

  /**
   * Limits the length of this vector, based on the desired maximum length
   */
  def limit(length: Float): T

  /**
   * Limits the length of this vector, based on the desired maximum length squared.
   * Slightly faster performance compared to [[limit]]
   */
  def limit2(length: Float): T

  /**
   * Sets the length of the vector. Does nothing if this vector is zero.
   */
  def setLength(length: Float): T

  /**
   * Sets the length of this vector, based on the square of the desired length. Does nothing if this vector is zero
   * Slightly faster performance compared to [[setLength]]
   */
  def setLength2(length: Float): T

  /**
   *  Clamps this vector's length to given min and max values
   */
  def clamp(min: Float, max: Float): T

  /**
   * Subtracts this vector by the given vector.
   */
  def subtract(vector: T): T

  /**
   * @see [[subtract]]
   */
  def -(vector: T): T

  /**
   * Adds this vector by the given vector.
   */
  def add(vector: T): T

  /**
   * @see [[add]]
   */
  def +(vector: T): T

  /**
   * Normalizes this vector. Does nothing if this vector is zero.
   */
  def normalize: T

  /**
   * Scales this vector by a scalar
   */
  def scale(scalar: Float): T

  /**
   * Scales this vector by the given vector
   */
  def scale(vector: T): T

  /**
   * Distance between this vector and the given vector
   */
  def distance(vector: T): Float

  /**
   * Distance between this vector and the given vector
   */
  def distance2(vector: T): Float

  /**
   * Linearly interpolates between this vector and the target vector by alpha.
   *
   * @param vector The target vector
   * @param alpha The interpolation coefficient, in the range [0, 1]
   */
  def lerp(vector: T, alpha: Float Refined Alpha): T

  /**
   * Interpolates between this vector and the target vector by alpha.
   *
   * @param vector The target vector
   * @param alpha The interpolation coefficient, in the range [0, 1]
   * @param interpolation The interpolation method
   */
  def interpolate(vector: T, alpha: Float Refined Alpha, interpolation: Interpolation): T

  // TODO: Maybe take in a seed to keep the method referentially transparent?
  //def setToRandomDirection: T

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
   * Check if this vector is in line with the given vector (either in the same or the opposite direction)
   */
  def isOnLine(vector: T, epsilon: Float): Boolean

  /**
   * Check if this vector is in line with the given vector (either in the same or the opposite direction)
   */
  def isOnLine(vector: T): Boolean

  /**
   * Check if this vector is collinear with the given vector
   */
  def isCollinear(vector: T): Boolean

  /**
   * Check if this vector is collinear with the given vector
   */
  def isCollinear(vector: T, epsilon: Float): Boolean

  /**
   * Check if this vector is opposite collinear with the given vector
   */
  def isCollinearOpposite(vector: T)

  /**
   * Check if this vector is opposite collinear with the given vector
   */
  def isCollinearOpposite(vector: T, epsilon: Float): Boolean

  /**
   * Check if this vector is perpendicular to the given vector
   */
  def isPerpendicular(vector: T): Boolean

  /**
   * Check if this vector is perpendicular to the given vector
   */
  def isPerpendicular(vector: T, epsilon: Float): Boolean

  /**
   * Check if this vector has a similar direction to the given vector
   */
  def hasSameDirection(vector: T): Boolean

  /**
   * Check if this vector has the opposite direction to the given vector
   */
  def hasOppositeDirection(vector: T): Boolean

  /**
   * Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
   */
  def epsilonEquals(vector: T, epsilon: Float): Boolean

  /**
   * Scale the given vector by the given scalar value, then add it to this.
   */
  def scaleAdd(vector: T, scalar: Float): T

  /**
   * Scale the given vector by the given scalar vector, then add it to this.
   */
  def scaleAdd(vector: T, scalar: T): T
}

object Vector {

  /**
   * Refined predicate which clamps a number to the range [0f, 1f]
   */
  type Alpha = GreaterEqual[W.`0f`.T] And LessEqual[W.`1f`.T]
}
