package scalagdx.math

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.W

import com.badlogic.gdx.math.Interpolation
import eu.timepit.refined.api.Refined
import scala.math.sqrt
import scala.math.abs
import eu.timepit.refined.auto._
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.{Vector2 => JVector2}

/**
 * Immutable representation of a 2D vector.
 */
final case class Vector2(x: Float, y: Float) extends Vector[Vector2] {

  /**
   * Converts this into the java equivalent.
   */
  lazy val asJava: JVector2 = new JVector2(x, y)

  /**
   * @inheritdoc
   */
  override lazy val length: Float = Vector2.length(x, y)

  /**
   * @inheritdoc
   */
  override lazy val length2: Float = Vector2.length2(x, y)

  /**
   * @inheritdoc
   */
  override def limit(limit: Float): Vector2 = limit2(limit * limit)

  /**
   * @inheritdoc
   */
  override def limit2(limit: Float): Vector2 =
    if (length2 > limit) multiply(sqrt((limit / length2).toDouble).toFloat)
    else this

  /**
   * @inheritdoc
   */
  override def setLength(value: Float): Vector2 = setLength2(value * value)

  /**
   * @inheritdoc
   */
  override def setLength2(value: Float): Vector2 =
    if (length2 == 0 || length2 == value) this
    else multiply(sqrt((value / length2).toDouble).toFloat)

  /**
   * @inheritdoc
   */
  override def clamp(min: Float, max: Float): Vector2 =
    if (length2 == 0f) this
    else {
      val max2 = max * max
      if (length2 > max2)
        multiply(sqrt((max2 / length2).toDouble).toFloat)
      else {
        val min2 = min * min
        if (length2 < min2)
          multiply(sqrt((min2 / length2).toDouble).toFloat)
        else this
      }
    }

  /**
   * Subtracts this vector by the given (x, y) values.
   */
  def subtract(x: Float, y: Float): Vector2 = Vector2(this.x - x, this.y - y)

  /**
   * Subtracts this vector's (x, y) values by the given value.
   */
  def subtract(value: Float): Vector2 = Vector2(this.x - value, this.y - value)

  /**
   * @inheritdoc
   */
  override def subtract(vector: Vector2): Vector2 = subtract(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  override def -(vector: Vector2): Vector2 = subtract(vector)

  /**
   * Adds this vector by the given (x, y) values.
   */
  def add(x: Float, y: Float): Vector2 = Vector2(this.x + x, this.y + y)

  /**
   * Adds this vector's (x, y) values by the given value.
   */
  def add(value: Float): Vector2 = Vector2(this.x + value, this.y + value)

  /**
   * @inheritdoc
   */
  override def add(vector: Vector2): Vector2 = add(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  override def +(vector: Vector2): Vector2 = add(vector)

  /**
   * Multiplies this vector with the given (x, y) values.
   */
  def multiply(x: Float, y: Float): Vector2 = Vector2(this.x * x, this.y * y)

  /**
   * Multiples this vector's (x, y) values by the given value.
   */
  def multiply(value: Float): Vector2 = Vector2(this.x * value, this.y * value)

  /**
   * @inheritdoc
   */
  override def multiply(vector: Vector2): Vector2 = multiply(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  override def *(vector: Vector2): Vector2 = multiply(vector)

  /**
   * Divides this vector by the given (x, y) values.
   */
  def divide(x: Float, y: Float): Vector2 = Vector2(this.x / x, this.y / y)

  /**
   * Divides this vector's (x, y) values by the given value.
   */
  def divide(value: Float): Vector2 = Vector2(this.x / value, this.y / value)

  /**
   * @inheritdoc
   */
  override def divide(vector: Vector2): Vector2 = divide(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  override def /(vector: Vector2): Vector2 = divide(vector)

  /**
   * @inheritdoc
   */
  override def normalize: Vector2 =
    if (length != 0) Vector2(x / length, y / length)
    else this

  /**
   * Distance between this vector and the given vector
   */
  def distance(x: Float, y: Float): Float = Vector2.distance(this.x, this.y, x, y)

  /**
   * @inheritdoc
   */
  override def distance(vector: Vector2): Float = Vector2.distance(x, y, vector.x, vector.y)

  /**
   * Distance between this vector and the given vector.
   * Slightly faster performance compared to [[distance]]
   */
  def distance2(x: Float, y: Float): Float = Vector2.distance2(this.x, this.y, x, y)

  /**
   * @inheritdoc
   */
  override def distance2(vector: Vector2): Float = Vector2.distance2(x, y, vector.x, vector.y)

  /**
   * @inheritdoc
   */
  override def lerp(vector: Vector2, alpha: Refined[Float, Vector.Alpha]): Vector2 = {
    val invAlpha = 1f - alpha
    Vector2(
      x = (x * invAlpha) + (vector.x * alpha),
      y = (y * invAlpha) + (vector.y * alpha)
    )
  }

  /**
   * @inheritdoc
   */
  override def interpolate(
      vector: Vector2,
      alpha: Refined[Float, Vector.Alpha],
      interpolation: Interpolation
  ): Vector2 = lerp(vector, Refined.unsafeApply(interpolation(alpha)))

  /**
   * @inheritdoc
   */
  override def isUnit: Boolean = isUnit(0.000000001f)

  /**
   * @inheritdoc
   */
  override def isUnit(margin: Float): Boolean = abs(length2 - 1f) < margin

  /**
   * @inheritdoc
   */
  override def isZero: Boolean = x == 0 && y == 0

  /**
   * @inheritdoc
   */
  override def isZero(margin: Float): Boolean = length2 < margin

  /**
   * @inheritdoc
   */
  override def isOnLine(vector: Vector2, epsilon: Float): Boolean =
    MathUtils.isZero(x * vector.y - y * vector.x, epsilon)

  /**
   * @inheritdoc
   */
  override def isOnLine(vector: Vector2): Boolean =
    MathUtils.isZero(x * vector.y - y * vector.x)

  /**
   * @inheritdoc
   */
  override def isCollinear(vector: Vector2): Boolean = isOnLine(vector) && dot(vector) > 0f

  /**
   * @inheritdoc
   */
  override def isCollinear(vector: Vector2, epsilon: Float): Boolean = isOnLine(vector, epsilon) && dot(vector) > 0f

  /**
   * @inheritdoc
   */
  override def isCollinearOpposite(vector: Vector2): Boolean = isOnLine(vector) && dot(vector) < 0f

  /**
   * @inheritdoc
   */
  override def isCollinearOpposite(vector: Vector2, epsilon: Float): Boolean =
    isOnLine(vector, epsilon) && dot(vector) < 0f

  /**
   * @inheritdoc
   */
  override def isPerpendicular(vector: Vector2): Boolean = MathUtils.isZero(dot(vector))

  /**
   * @inheritdoc
   */
  override def isPerpendicular(vector: Vector2, epsilon: Float): Boolean = MathUtils.isZero(dot(vector), epsilon)

  /**
   * @inheritdoc
   */
  override def hasSameDirection(vector: Vector2): Boolean = dot(vector) > 0

  /**
   * @inheritdoc
   */
  override def hasOppositeDirection(vector: Vector2): Boolean = dot(vector) < 0

  /**
   * Compares this vector with the given values, using the supplied epsilon for fuzzy equality testing
   */
  def epsilonEquals(x: Float, y: Float, epsilon: Float): Boolean =
    if (abs(x - this.x) > epsilon) false
    else if (abs(y - this.y) > epsilon) false
    else true

  /**
   * @inheritdoc
   */
  override def epsilonEquals(vector: Vector2, epsilon: Float): Boolean = epsilonEquals(x, y, epsilon)

  /**
   * Compares this vector with the given values, using MathUtils for fuzzy equality testing
   */
  def epsilonEquals(x: Float, y: Float): Boolean = epsilonEquals(x, y, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * Compares this vector with the given vector, using MathUtils for fuzzy equality testing
   */
  def epsilonEquals(vector: Vector2): Boolean = epsilonEquals(vector.x, vector.y)

  /**
   * @inheritdoc
   */
  override def scaleAdd(vector: Vector2, scalar: Float): Vector2 = Vector2(
    x = this.x + (vector.x * scalar),
    y = this.y + (vector.y * scalar)
  )

  /**
   * @inheritdoc
   */
  override def scaleAdd(vector: Vector2, scalar: Vector2): Vector2 = Vector2(
    x = this.x + (vector.x * scalar.x),
    y = this.y + (vector.y * scalar.y)
  )

  /**
   * The dot product between this and the given (x, y) values
   */
  def dot(x: Float, y: Float): Float = this.x * x + this.y * y

  /**
   * @inheritdoc
   */
  override def dot(vector: Vector2): Float = dot(vector.x, vector.y)

  /**
   * Converts this vector to a string in the format (x,y)
   */
  def asString: String = s"($x,$y)"
}

object Vector2 {

  /**
   * Refined predicate for strings in the format (x,y)
   */
  type XY = MatchesRegex[W.`"""\\(\\s*[+-]?(\\d*\\.)?\\d+\\s*,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\)"""`.T]

  /**
   * Creates a new vector with a value of (0, 0)
   */
  def apply(): Vector2 = Vector2(0f, 0f)

  /**
   * Creates a [[Vector2]] from a string with the format (x,y).
   */
  def fromString(value: String Refined XY): Vector2 = {
    val values = value.substring(1, value.length - 1).split(",")
    Vector2(
      x = values(0).toFloat,
      y = values(1).toFloat
    )
  }

  /**
   * The euclidean length
   */
  def length(x: Float, y: Float): Float = sqrt((x * x + y * y).toDouble).toFloat

  /**
   * Faster performance by avoiding having to sqrt. Do not use for actual length.
   * Useful for comparisons.
   */
  def length2(x: Float, y: Float): Float = x * x + y * y

  /**
   * Distance between (x1, y1) and (x2, y2)
   */
  def distance(x1: Float, y1: Float, x2: Float, y2: Float): Float =
    sqrt(distance2(x1, y1, x2, y2).toDouble).toFloat

  /**
   * Distance between this vector and the given vector.
   * Slightly faster performance compared to [[distance]]
   */
  def distance2(x1: Float, y1: Float, x2: Float, y2: Float): Float = {
    val xDistance = x2 - x1
    val yDistance = y2 - y1
    xDistance * xDistance + yDistance * yDistance
  }
}
