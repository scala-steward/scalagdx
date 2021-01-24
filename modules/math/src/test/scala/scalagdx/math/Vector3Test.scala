package scalagdx.math

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.{Vector3 => JVector3}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalagdx.math.ext.Vector3Ops

class Vector3Test extends AnyFlatSpec with Matchers {

  private val vector = Vector3()

  it should "convert to and from java" in {
    Vector3() shouldBe new JVector3().asScala
    Vector3(1f, 2f, 3f).asJava shouldBe new JVector3(1f, 2f, 3f)
    Vector3(Vector2(), 0f).asJava shouldBe new JVector3()
  }

  it should "construct from string" in {
    Vector3.fromString("(1,   3,  3)") shouldBe Vector3(1f, 3f, 3f)
    Vector3.fromString("(1,5,2)") shouldBe Vector3(1f, 5f, 2f)
    Vector3.fromString("(    0.2, 53   , 32.005)") shouldBe Vector3(0.2f, 53f, 32.005f)
    Vector3.fromString(Refined.unsafeApply(Vector3(1f, 2f, 3f).asString)) shouldBe Vector3(1f, 2f, 3f)
  }

  it should "add" in {
    vector.add(1f, 2f, 3f) shouldBe Vector3(1f, 2f, 3f)
    vector.add(1f) shouldBe Vector3(1f, 1f, 1f)
    vector.add(vector) shouldBe vector
    vector + vector shouldBe vector
  }

  it should "subtract" in {
    vector.subtract(1f, 2f, 3f) shouldBe Vector3(-1f, -2f, -3f)
    vector.subtract(1f) shouldBe Vector3(-1f, -1f, -1f)
    vector.subtract(vector) shouldBe vector
    vector - vector shouldBe vector
  }

  it should "multiply" in {
    vector.multiply(1f, 2f, 3f) shouldBe vector
    vector.multiply(1f) shouldBe vector
    vector.multiply(vector) shouldBe vector
    vector * vector shouldBe vector
  }

  it should "divide" in {
    vector.divide(1f, 2f, 3f) shouldBe vector
    vector.divide(1f) shouldBe vector
    vector.divide(Vector3(1f, 2f, 3f)) shouldBe vector
    val vector2 = vector / vector
    vector2.x.isNaN shouldBe true
    vector2.y.isNaN shouldBe true
    Vector3(1f, 2f, 3f).divide(0f) shouldBe
      Vector3(Float.PositiveInfinity, Float.PositiveInfinity, Float.PositiveInfinity)
  }

  it should "have equivalent methods as the java version" in {
    val scala = Vector3(1f, 2f, 3f)
    val java = new JVector3(1f, 2f, 3f)
    def reset = java.set(1f, 2f, 3f)

    Vector3.length(1f, 2f, 3f) shouldBe JVector3.len(1f, 2f, 3f)
    Vector3.length2(1f, 2f, 3f) shouldBe JVector3.len2(1f, 2f, 3f)
    scala.length shouldBe java.len
    scala.length2 shouldBe java.len2

    scala.limit(1f) shouldBe java.limit(1f).asScala
    reset
    scala.limit2(.1f) shouldBe java.limit2(.1f).asScala
    reset
    scala.limit2(10000f) shouldBe java.limit2(10000f).asScala
    reset

    scala.setLength(1f) shouldBe java.setLength(1f).asScala
    reset
    scala.setLength2(1f) shouldBe java.setLength2(1f).asScala
    reset

    for (i <- 0 until 1000; j <- 0 until 1000) {
      scala.clamp(i.toFloat, j.toFloat) shouldBe java.clamp(i.toFloat, j.toFloat).asScala
      reset
    }

    val matrix4 = new Matrix4()
    val matrix3 = new Matrix3()
    scala.multiply(matrix4) shouldBe java.mul(matrix4).asScala
    reset
    scala.multiply(matrix3) shouldBe java.mul(matrix3).asScala
    reset
    scala.multiply4x3(matrix4.getValues: _*) shouldBe java.mul4x3(matrix4.getValues).asScala
    reset
    scala.multiplyTranspose(matrix4) shouldBe java.traMul(matrix4).asScala
    reset
    scala.multiplyTranspose(matrix3) shouldBe java.traMul(matrix3).asScala
    reset

    scala.normalize shouldBe java.nor.asScala
    reset

    Vector3.distance(1f, 2f, 3f, 4f, 5f, 6) shouldBe JVector3.dst(1f, 2f, 3f, 4f, 5f, 6f)
    Vector3.distance2(1f, 2f, 3f, 4f, 5f, 6f) shouldBe JVector3.dst2(1f, 2f, 3f, 4f, 5f, 6f)
    scala.distance(1f, 2f, 3f) shouldBe java.dst(1f, 2f, 3f)
    scala.distance(scala) shouldBe java.dst(java)
    scala.distance2(1f, 2f, 3f) shouldBe java.dst2(1f, 2f, 3f)
    scala.distance2(scala) shouldBe java.dst2(java)

    Vector3.dot(1f, 2f, 3f, 4f, 5f, 6f) shouldBe JVector3.dot(1f, 2f, 3f, 4f, 5f, 6f)
    scala.dot(1f, 2f, 3f) shouldBe java.dot(1f, 2f, 3f)
    reset

    scala.lerp(vector, .75f) shouldBe java.lerp(vector.asJava, .75f).asScala
    reset

    val interpolation = new Interpolation.BounceIn(3)
    scala.interpolate(vector, .95f, interpolation) shouldBe java.interpolate(vector.asJava, .95f, interpolation).asScala
    reset

    val margin = 0.001f
    scala.isZero shouldBe java.isZero
    scala.isZero(margin) shouldBe java.isZero(margin)
    scala.isUnit shouldBe java.isUnit
    scala.isUnit(margin) shouldBe java.isUnit(margin)
    vector.isZero shouldBe vector.asJava.isZero

    scala.isOnLine(vector) shouldBe java.isOnLine(vector.asJava)
    scala.isOnLine(vector, margin) shouldBe java.isOnLine(vector.asJava, margin)

    scala.hasSameDirection(vector) shouldBe java.hasSameDirection(vector.asJava)
    scala.hasOppositeDirection(vector) shouldBe java.hasOppositeDirection(vector.asJava)

    scala.isCollinear(vector) shouldBe java.isCollinear(vector.asJava)
    scala.isCollinear(vector, margin) shouldBe java.isCollinear(vector.asJava, margin)
    scala.isCollinearOpposite(vector) shouldBe java.isCollinearOpposite(vector.asJava)
    scala.isCollinearOpposite(vector, margin) shouldBe java.isCollinearOpposite(vector.asJava, margin)
    scala.isPerpendicular(vector) shouldBe java.isPerpendicular(vector.asJava)
    scala.isPerpendicular(vector, margin) shouldBe java.isPerpendicular(vector.asJava, margin)

    scala.epsilonEquals(1f, 2f, 3f, margin) shouldBe java.epsilonEquals(1f, 2f, 3f, margin)
    scala.epsilonEquals(2f, 2f, 3f, margin) shouldBe java.epsilonEquals(2f, 2f, 3f, margin)
    scala.epsilonEquals(1f, 1f, 3f, margin) shouldBe java.epsilonEquals(1f, 1f, 3f, margin)
    scala.epsilonEquals(1f, 2f, 2f, margin) shouldBe java.epsilonEquals(1f, 2f, 2f, margin)
    scala.epsilonEquals(1f, 2f, 3f) shouldBe java.epsilonEquals(1f, 2f, 3f)
    scala.epsilonEquals(vector, margin) shouldBe java.epsilonEquals(vector.asJava, margin)
    scala.epsilonEquals(vector) shouldBe java.epsilonEquals(vector.asJava)

    scala.scaleAdd(scala, margin) shouldBe java.mulAdd(java, margin).asScala
    reset
    scala.scaleAdd(scala, scala) shouldBe java.mulAdd(java, java).asScala
    reset

    scala.crossProduct(1f, 2f, 3f) shouldBe java.crs(1f, 2f, 3f).asScala
    reset
    scala.crossProduct(scala) shouldBe java.crs(java).asScala
    reset

    scala.project(matrix4) shouldBe java.prj(matrix4).asScala
    reset

    scala.rotate(matrix4) shouldBe java.rot(matrix4).asScala
    reset
    scala.rotate(1f, 2f, 3f, 4f) shouldBe java.rotate(1f, 2f, 3f, 4f).asScala
    reset
    scala.rotate(1f, scala) shouldBe java.rotate(java, 1f).asScala
    reset
    scala.rotate(1f, 2f, 3f, 4f) shouldBe java.rotate(1f, 2f, 3f, 4f).asScala
    reset
    scala.rotateRad(1f, scala) shouldBe java.rotateRad(java, 1f).asScala
    reset
    scala.rotateRad(1f, 2f, 3f, 4f) shouldBe java.rotateRad(1f, 2f, 3f, 4f).asScala
    reset

    scala.unrotate(matrix4) shouldBe java.unrotate(matrix4).asScala
    reset
    scala.untransform(matrix4) shouldBe java.untransform(matrix4).asScala
    reset

    for (i <- 0 until 100; j <- 0 until 100; k <- 0 until 100) {
      val vec3 = Vector3(i.toFloat, j.toFloat, k.toFloat)
      vec3.slerp(scala, 1f) shouldBe vec3.asJava.slerp(java, 1f).asScala
    }
    vector.slerp(vector, 1f) shouldBe vector.asJava.slerp(vector.asJava, 1f).asScala

    Vector3.fromSpherical(2f, 3f) shouldBe java.setFromSpherical(2f, 3f).asScala
  }

  it should "return the same instance" in {
    vector.setLength2(1f) should be theSameInstanceAs vector
    vector.clamp(1f, 1f) should be theSameInstanceAs vector
  }
}
