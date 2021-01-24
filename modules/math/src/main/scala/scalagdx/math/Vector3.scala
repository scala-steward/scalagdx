package scalagdx.math

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.{Vector3 => JVector3}
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.string.MatchesRegex

import math.sqrt
import math.abs
import math.acos
import math.sin
import math.cos

/**
 * Immutable representation of a 2D vector.
 */
final case class Vector3(x: Float, y: Float, z: Float) extends Vector[Vector3] {

  import scalagdx.math.Vector3.internalMatrix

  /**
   * Converts this into the java equivalent.
   */
  def asJava: JVector3 = new JVector3(x, y, z)

  /**
   * @inheritdoc
   */
  override lazy val length: Float = Vector3.length(x, y, z)

  /**
   * @inheritdoc
   */
  override lazy val length2: Float = Vector3.length2(x, y, z)

  /**
   * @inheritdoc
   */
  override def limit(value: Float): Vector3 = limit2(value * value)

  /**
   * @inheritdoc
   */
  override def limit2(value: Float): Vector3 =
    if (length2 > value) multiply(sqrt((value / length2).toDouble).toFloat)
    else this

  /**
   * @inheritdoc
   */
  override def setLength(value: Float): Vector3 = setLength2(value * value)

  /**
   * @inheritdoc
   */
  override def setLength2(value: Float): Vector3 =
    if (length2 == 0 || length2 == value) this
    else multiply(sqrt((value / length2).toDouble).toFloat)

  /**
   * @inheritdoc
   */
  override def clamp(min: Float, max: Float): Vector3 =
    if (length2 == 0f) this
    else {
      val max2 = max * max
      if (length2 > max2) multiply(sqrt((max2 / length2).toDouble).toFloat)
      else {
        val min2 = min * min
        if (length2 < min2) multiply(sqrt((min2 / length2).toDouble).toFloat)
        else this
      }
    }

  /**
   * Subtracts this vector by the given (x, y, z) values.
   */
  def subtract(x: Float, y: Float, z: Float): Vector3 = Vector3(this.x - x, this.y - y, this.z - z)

  /**
   * Subtracts this vector by the given value.
   */
  def subtract(value: Float): Vector3 = subtract(value, value, value)

  /**
   * @inheritdoc
   */
  override def subtract(vector: Vector3): Vector3 = subtract(vector.x, vector.y, vector.z)

  /**
   * @inheritdoc
   */
  override def -(vector: Vector3): Vector3 = subtract(vector)

  /**
   * Adds this vector with the given (x, y, z) values.
   */
  def add(x: Float, y: Float, z: Float): Vector3 = Vector3(this.x + x, this.y + y, this.z + z)

  /**
   * Adds this vector with the given value.
   */
  def add(value: Float): Vector3 = add(value, value, value)

  /**
   * @inheritdoc
   */
  override def add(vector: Vector3): Vector3 = add(vector.x, vector.y, vector.z)

  /**
   * @inheritdoc
   */
  override def +(vector: Vector3): Vector3 = add(vector)

  /**
   * Multiples this vector with the given (x, y, z) values.
   */
  def multiply(x: Float, y: Float, z: Float): Vector3 = Vector3(this.x * x, this.y * y, this.z * z)

  /**
   * Multiples this vector with the given value.
   */
  def multiply(value: Float): Vector3 = multiply(value, value, value)

  /**
   * @inheritdoc
   */
  override def multiply(vector: Vector3): Vector3 = multiply(vector.x, vector.y, vector.z)

  /**
   * @inheritdoc
   */
  override def *(vector: Vector3): Vector3 = multiply(vector)

  /**
   * Multiplies the vector by the given 4x3 column major matrix. The matrix should be composed by a 3x3 matrix representing
   * rotation and scale plus a 1x3 matrix representing the translation.
   */
  def multiply4x3(matrices: Float*): Vector3 = Vector3(
    x = x * matrices(0) + y * matrices(3) + z * matrices(6) + matrices(9),
    y = x * matrices(1) + y * matrices(4) + z * matrices(7) + matrices(10),
    z = x * matrices(2) + y * matrices(5) + z * matrices(8) + matrices(11)
  )

  /**
   * Multiplies this vector by the given matrix.
   */
  def multiply(matrix: Matrix4): Vector3 = {
    val matrices = matrix.getValues
    Vector3(
      x = x * matrices(Matrix4.M00) + y * matrices(Matrix4.M01) + z * matrices(Matrix4.M02) + matrices(Matrix4.M03),
      y = x * matrices(Matrix4.M10) + y * matrices(Matrix4.M11) + z * matrices(Matrix4.M12) + matrices(Matrix4.M13),
      z = x * matrices(Matrix4.M20) + y * matrices(Matrix4.M21) + z * matrices(Matrix4.M22) + matrices(Matrix4.M23)
    )
  }

  /**
   * Multiplies this vector by the given matrix.
   */
  def multiply(matrix: Matrix3): Vector3 = {
    val matrices = matrix.getValues
    Vector3(
      x = x * matrices(Matrix3.M00) + y * matrices(Matrix3.M01) + z * matrices(Matrix3.M02),
      y = x * matrices(Matrix3.M10) + y * matrices(Matrix3.M11) + z * matrices(Matrix3.M12),
      z = x * matrices(Matrix3.M20) + y * matrices(Matrix3.M21) + z * matrices(Matrix3.M22)
    )
  }

  /**
   * Multiplies the vector by the transpose of the given matrix
   */
  def multiplyTranspose(matrix: Matrix4): Vector3 = {
    val matrices = matrix.getValues
    Vector3(
      x = x * matrices(Matrix4.M00) + y * matrices(Matrix4.M10) + z * matrices(Matrix4.M20) + matrices(Matrix4.M30),
      y = x * matrices(Matrix4.M01) + y * matrices(Matrix4.M11) + z * matrices(Matrix4.M21) + matrices(Matrix4.M31),
      z = x * matrices(Matrix4.M02) + y * matrices(Matrix4.M12) + z * matrices(Matrix4.M22) + matrices(Matrix4.M32)
    )
  }

  /**
   * Multiplies the vector by the transpose of the given matrix
   */
  def multiplyTranspose(matrix: Matrix3): Vector3 = {
    val matrices = matrix.getValues
    Vector3(
      x = x * matrices(Matrix3.M00) + y * matrices(Matrix3.M10) + z * matrices(Matrix3.M20),
      y = x * matrices(Matrix3.M01) + y * matrices(Matrix3.M11) + z * matrices(Matrix3.M21),
      z = x * matrices(Matrix3.M02) + y * matrices(Matrix3.M12) + z * matrices(Matrix3.M22)
    )
  }

  /**
   * Divides this vector by the given (x, y, z) values.
   */
  def divide(x: Float, y: Float, z: Float): Vector3 = Vector3(this.x / x, this.y / y, this.z / z)

  /**
   * Divides this vector by the given value.
   */
  def divide(value: Float): Vector3 = divide(value, value, value)

  /**
   * @inheritdoc
   */
  override def divide(vector: Vector3): Vector3 = divide(vector.x, vector.y, vector.z)

  /**
   * @inheritdoc
   */
  override def /(vector: Vector3): Vector3 = divide(vector)

  /**
   * @inheritdoc
   */
  override def normalize: Vector3 =
    if (length2 == 0f || length2 == 1f) this
    else multiply(1f / sqrt(length2.toDouble).toFloat)

  /**
   * The euclidean distance between the two (x, y, z) values.
   */
  def distance(x: Float, y: Float, z: Float): Float = Vector3.distance(this.x, this.y, this.z, x, y, z)

  /**
   * @inheritdoc
   */
  override def distance(vector: Vector3): Float = distance(vector.x, vector.y, vector.z)

  /**
   * The squared distance between the two (x, y, z) values.
   */
  def distance2(x: Float, y: Float, z: Float): Float = Vector3.distance2(this.x, this.y, this.z, x, y, z)

  /**
   * @inheritdoc
   */
  override def distance2(vector: Vector3): Float = distance2(vector.x, vector.y, vector.z)

  /**
   * @inheritdoc
   */
  override def lerp(vector: Vector3, alpha: Refined[Float, Vector.Alpha]): Vector3 = Vector3(
    x = x + alpha * (vector.x - x),
    y = y + alpha * (vector.y - y),
    z = z + alpha * (vector.z - z)
  )

  /**
   * @inheritdoc
   */
  override def interpolate(
      vector: Vector3,
      alpha: Refined[Float, Vector.Alpha],
      interpolation: Interpolation
  ): Vector3 = lerp(vector, Refined.unsafeApply(interpolation(0f, 1f, alpha)))

  /**
   * @inheritdoc
   */
  override def isUnit: Boolean = isUnit(0.000000001f)

  /**
   * @inheritdoc
   */
  override def isUnit(margin: Float): Boolean = abs((length2 - 1f).toDouble) < margin

  /**
   * @inheritdoc
   */
  override def isZero: Boolean = x == 0f && y == 0f && z == 0f

  /**
   * @inheritdoc
   */
  override def isZero(margin: Float): Boolean = length2 < margin

  /**
   * @inheritdoc
   */
  override def isOnLine(vector: Vector3, epsilon: Float): Boolean = Vector3.length2(
    x = y * vector.z - z * vector.y,
    y = z * vector.x - x * vector.z,
    z = x * vector.y - y * vector.x
  ) <= epsilon

  /**
   * @inheritdoc
   */
  override def isOnLine(vector: Vector3): Boolean = Vector3.length2(
    x = y * vector.z - z * vector.y,
    y = z * vector.x - x * vector.z,
    z = x * vector.y - y * vector.x
  ) <= MathUtils.FLOAT_ROUNDING_ERROR

  /**
   * @inheritdoc
   */
  override def isCollinear(vector: Vector3): Boolean =
    isOnLine(vector) && hasSameDirection(vector)

  /**
   * @inheritdoc
   */
  override def isCollinear(vector: Vector3, epsilon: Float): Boolean =
    isOnLine(vector, epsilon) && hasSameDirection(vector)

  /**
   * @inheritdoc
   */
  override def isCollinearOpposite(vector: Vector3): Boolean =
    isOnLine(vector) && hasOppositeDirection(vector)

  /**
   * @inheritdoc
   */
  override def isCollinearOpposite(vector: Vector3, epsilon: Float): Boolean =
    isOnLine(vector, epsilon) && hasOppositeDirection(vector)

  /**
   * @inheritdoc
   */
  override def isPerpendicular(vector: Vector3): Boolean = MathUtils.isZero(dot(vector))

  /**
   * @inheritdoc
   */
  override def isPerpendicular(vector: Vector3, epsilon: Float): Boolean = MathUtils.isZero(dot(vector), epsilon)

  /**
   * @inheritdoc
   */
  override def hasSameDirection(vector: Vector3): Boolean = dot(vector) > 0f

  /**
   * @inheritdoc
   */
  override def hasOppositeDirection(vector: Vector3): Boolean = dot(vector) < 0f

  /**
   * Compares this vector with the given (x, y, z) values, using the epsilon for fuzzy equality.
   */
  def epsilonEquals(x: Float, y: Float, z: Float, epsilon: Float): Boolean =
    if (abs((x - this.x).toDouble) > epsilon) false
    else if (abs((y - this.y).toDouble) > epsilon) false
    else if (abs((z - this.z).toDouble) > epsilon) false
    else true

  /**
   * @inheritdoc
   */
  override def epsilonEquals(vector: Vector3, epsilon: Float): Boolean =
    epsilonEquals(vector.x, vector.y, vector.z, epsilon)

  /**
   * Compares this vector with the given (x, y, z) values, using MathUtils for fuzzy equality.
   */
  def epsilonEquals(x: Float, y: Float, z: Float): Boolean = epsilonEquals(x, y, z, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * Compares this vector with the given (x, y, z) values, using MathUtils for fuzzy equality.
   */
  def epsilonEquals(vector: Vector3): Boolean =
    epsilonEquals(vector.x, vector.y, vector.z, MathUtils.FLOAT_ROUNDING_ERROR)

  /**
   * @inheritdoc
   */
  override def scaleAdd(vector: Vector3, scalar: Float): Vector3 = Vector3(
    x = x + vector.x * scalar,
    y = y + vector.y * scalar,
    z = z + vector.z * scalar
  )

  /**
   * @inheritdoc
   */
  override def scaleAdd(vector: Vector3, scalar: Vector3): Vector3 = Vector3(
    x = x + vector.x * scalar.x,
    y = y + vector.y * scalar.y,
    z = z + vector.z * scalar.z
  )

  /**
   * The dot product between this vector and the given (x, y, z) values.
   */
  def dot(x: Float, y: Float, z: Float): Float = Vector3.dot(this.x, this.y, this.z, x, y, z)

  /**
   * @inheritdoc
   */
  override def dot(vector: Vector3): Float = dot(vector.x, vector.y, vector.z)

  /**
   * Cross product between this vector and the given (x, y, z) values.
   */
  def crossProduct(x: Float, y: Float, z: Float): Vector3 = Vector3(
    x = this.y * z - this.z * y,
    y = this.z * x - this.x * z,
    z = this.x * y - this.y * x
  )

  /**
   * Cross product between this vector and the given vector.
   */
  def crossProduct(vector: Vector3): Vector3 = crossProduct(vector.x, vector.y, vector.z)

  /**
   * Project/unproject vector using the given matrix.
   */
  def project(matrix: Matrix4): Vector3 = {
    val matrices = matrix.getValues
    val w = 1f / (x * matrices(Matrix4.M30) + y * matrices(Matrix4.M31) +
      z * matrices(Matrix4.M32) + matrices(Matrix4.M33))
    Vector3(
      x = (x * matrices(Matrix4.M00) + y * matrices(Matrix4.M01) + z *
        matrices(Matrix4.M02) + matrices(Matrix4.M03)) * w,
      y = (x * matrices(Matrix4.M10) + y * matrices(Matrix4.M11) + z *
        matrices(Matrix4.M12) + matrices(Matrix4.M13)) * w,
      z = (x * matrices(Matrix4.M20) + y * matrices(Matrix4.M21) + z *
        matrices(Matrix4.M22) + matrices(Matrix4.M23)) * w
    )
  }

  /**
   * Rotates this vector by the given matrix.
   */
  def rotate(matrix: Matrix4): Vector3 = {
    val matrices = matrix.getValues
    Vector3(
      x = x * matrices(Matrix4.M00) + y * matrices(Matrix4.M01) + z * matrices(Matrix4.M02),
      y = x * matrices(Matrix4.M10) + y * matrices(Matrix4.M11) + z * matrices(Matrix4.M12),
      z = x * matrices(Matrix4.M20) + y * matrices(Matrix4.M21) + z * matrices(Matrix4.M22)
    )
  }

  /**
   * Rotates this vector by the given angle in degrees around the given (x, y, z) axis values.
   */
  def rotate(degrees: Float, x: Float, y: Float, z: Float): Vector3 =
    multiply(internalMatrix.setToRotation(x, y, z, degrees))

  /**
   * Rotates this vector by the given angle in degrees around the given vector axis.
   */
  def rotate(degrees: Float, axis: Vector3): Vector3 = rotate(degrees, axis.x, axis.y, axis.z)

  /**
   * Rotates this vector by the given angle in radians around the given (x, y, z) axis values.
   */
  def rotateRad(radians: Float, x: Float, y: Float, z: Float): Vector3 =
    multiply(internalMatrix.setToRotationRad(x, y, z, radians))

  /**
   * Rotates this vector by the given angle in radians around the given vector axis.
   */
  def rotateRad(radians: Float, axis: Vector3): Vector3 = rotateRad(radians, axis.x, axis.y, axis.z)

  /**
   * Multiplies this vector by the transpose of the first three columns of the matrix.
   * Does not apply scaling.
   */
  def unrotate(matrix: Matrix4): Vector3 = {
    val matrices = matrix.getValues
    Vector3(
      x = x * matrices(Matrix4.M00) + y * matrices(Matrix4.M10) + z * matrices(Matrix4.M20),
      y = x * matrices(Matrix4.M01) + y * matrices(Matrix4.M11) + z * matrices(Matrix4.M21),
      z = x * matrices(Matrix4.M02) + y * matrices(Matrix4.M12) + z * matrices(Matrix4.M22)
    )
  }

  /**
   * Translates this vector in the direction opposite to the translation of the matrix and then
   * multiplies this vector by the transpose of the first three columns of the matrix.
   * Does not apply scaling.
   */
  def untransform(matrix: Matrix4): Vector3 = {
    val matrices = matrix.getValues
    val x = this.x - matrices(Matrix4.M03)
    val y = this.y - matrices(Matrix4.M03)
    val z = this.z - matrices(Matrix4.M03)
    Vector3(
      x = x * matrices(Matrix4.M00) + y * matrices(Matrix4.M10) + z * matrices(Matrix4.M20),
      y = x * matrices(Matrix4.M01) + y * matrices(Matrix4.M11) + z * matrices(Matrix4.M21),
      z = x * matrices(Matrix4.M02) + y * matrices(Matrix4.M12) + z * matrices(Matrix4.M22)
    )
  }

  /**
   * Spherically interpolates between this vector and the given vector
   * by an alpha value in the range of [0f, 1f]
   */
  def slerp(vector: Vector3, alpha: Float Refined Vector.Alpha): Vector3 = {
    val dot = this.dot(vector)
    if (dot > 0.9995f || dot < -0.9995f) lerp(vector, alpha)
    else {
      val theta0 = acos(dot.toDouble).toFloat
      val theta = theta0 * alpha
      val sinTheta = sin(theta.toDouble).toFloat
      val targetX = vector.x - x * dot
      val targetY = vector.y - y * dot
      val targetZ = vector.z - z * dot
      val l2 = targetX * targetX + targetY * targetY + targetZ * targetZ
      val dl = sinTheta * (if (l2 < 0.0001f) 1f else 1f / sqrt(l2.toDouble).toFloat)
      multiply(cos(theta.toDouble).toFloat)
        .add(
          x = targetX * dl,
          y = targetY * dl,
          z = targetZ * dl
        )
        .normalize
    }
  }

  /**
   * Converts this vector to a string in the format (x,y,z)
   */
  def asString: String = s"($x,$y,$z)"
}

object Vector3 {

  /**
   * Refined predicate for strings in the format (x,y,z)
   */
  type XYZ = MatchesRegex[
    W.`"""\\(\\s*[+-]?(\\d*\\.)?\\d+\\s*,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\)"""`.T
  ]

  /**
   * Used internally to avoid creating new [[Matrix4]] objects on each operation
   */
  private val internalMatrix = new Matrix4()

  def apply(): Vector3 = Vector3(0f, 0f, 0f)

  /**
   * Constructs a new [[Vector3]] using the given [[Vector2]] and z values.
   */
  def apply(vector: Vector2, z: Float): Vector3 = Vector3(vector.x, vector.y, z)

  /**
   * Constructs a new [[Vector3]] from the given spherical coordinate.
   *
   * @param azimuthalAngle The angle between x-axis in radisn [0, 2pi]
   * @param polarAngle The angle between z-axis in radians [0, pi]
   */
  def fromSpherical(azimuthalAngle: Float, polarAngle: Float): Vector3 = {
    val sinPolar = MathUtils.sin(polarAngle)
    Vector3(
      x = MathUtils.cos(azimuthalAngle) * sinPolar,
      y = MathUtils.sin(azimuthalAngle) * sinPolar,
      z = MathUtils.cos(polarAngle)
    )
  }

  /**
   * Constructs a new [[Vector3]] from the given string in the format (x,y,z)
   */
  def fromString(value: String Refined XYZ): Vector3 = {
    val values = value.substring(1, value.length - 1).split(",")
    Vector3(
      x = values(0).toFloat,
      y = values(1).toFloat,
      z = values(2).toFloat
    )
  }

  /**
   * The euclidean length
   */
  def length(x: Float, y: Float, z: Float): Float = sqrt(length2(x, y, z).toDouble).toFloat

  /**
   * The squared euclidean length
   */
  def length2(x: Float, y: Float, z: Float): Float = x * x + y * y + z * z

  /**
   * The euclidean distance between the two (x, y, z) values.
   */
  def distance(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float = length(
    x = x2 - x1,
    y = y2 - y1,
    z = z2 - z1
  )

  /**
   * The squared distance between the two (x, y, z) values.
   */
  def distance2(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float = length2(
    x = x2 - x1,
    y = y2 - y1,
    z = z2 - z1
  )

  /**
   * The dot product between the two (x, y, z) values.
   */
  def dot(x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float): Float = x1 * x2 + y1 * y2 + z1 * z2
}
