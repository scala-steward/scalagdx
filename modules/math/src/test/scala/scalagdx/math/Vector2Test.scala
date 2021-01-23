package scalagdx.math

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.badlogic.gdx.math.{Vector2 => JVector2}
import scalagdx.math.ext.Vector2Ops
import eu.timepit.refined.auto._
import com.badlogic.gdx.math.Interpolation
import eu.timepit.refined.api.Refined

class Vector2Test extends AnyFlatSpec with Matchers {

  it should "convert to and from java" in {
    Vector2().asJava shouldBe new JVector2()
    Vector2(1f, 1f).asJava shouldBe new JVector2(1f, 1f)
    Vector2(1f, 2f) shouldBe new JVector2(1f, 2f).asScala
  }

  it should "construct from string" in {
    Vector2.fromString("(1,   3)") shouldBe Vector2(1f, 3f)
    Vector2.fromString("(1,5)") shouldBe Vector2(1f, 5f)
    Vector2.fromString("(    0.2, 53    )") shouldBe Vector2(0.2f, 53f)
  }

  it should "add" in {
    Vector2(12f, 10f).add(123f) shouldBe Vector2(135f, 133f)
    Vector2(0f, 0f).add(Vector2(1f, 2f)) shouldBe Vector2(1f, 2f)
    Vector2(10f, -10f).add(-10f, 10f) shouldBe Vector2(0f, 0f)
    Vector2(100f, 100f) + Vector2(100f, 200f) shouldBe Vector2(200f, 300f)
  }

  it should "subtract" in {
    Vector2(100f, 10f).subtract(3f) shouldBe Vector2(97f, 7f)
    Vector2(.5f, .5f).subtract(Vector2(.5f, 0f)) shouldBe Vector2(0f, .5f)
    Vector2(10f, 10f).subtract(3f, 5f) shouldBe Vector2(7f, 5f)
    Vector2(100f, 100f) - Vector2(50f, 1.5f) shouldBe Vector2(50f, 98.5f)
  }

  it should "multiply" in {
    Vector2(1f, 1f).multiply(100f) shouldBe Vector2(100f, 100f)
    Vector2(.5f, .5f).multiply(Vector2(10f, 2f)) shouldBe Vector2(5f, 1f)
    Vector2(3f, 3f).multiply(2f, 2f) shouldBe Vector2(6f, 6f)
    Vector2(10f, 1f) * Vector2(10f, 10f) shouldBe Vector2(100f, 10f)
  }

  it should "divide" in {
    Vector2(1f, 1f).divide(2f) shouldBe Vector2(.5f, .5f)
    Vector2(20f, 10f).divide(2f, 1f) shouldBe Vector2(10f, 10f)
    Vector2(3f, 3f).divide(3f, 6f) shouldBe Vector2(1f, .5f)
    Vector2(100f, 100f) / Vector2(50f, 25f) shouldBe Vector2(2f, 4f)
    Vector2(1f, 1f) / Vector2(0f, 0f) shouldBe Vector2(Float.PositiveInfinity, Float.PositiveInfinity)
    val div0 = Vector2(0f, 0f) / Vector2(0f, 0f)
    div0.x.isNaN shouldBe true
    div0.y.isNaN shouldBe true
  }

  it should "have equivalent methods as the java version" in {
    val scala = Vector2(1f, 2f)
    val java = scala.asJava

    def reset(): Unit = {
      java.set(scala.x, scala.y)
      ()
    }

    scala.normalize shouldBe java.nor.asScala
    reset()

    scala.length shouldBe java.len()
    scala.length2 shouldBe java.len2()

    scala.limit(0.5f) shouldBe java.limit(0.5f).asScala
    reset()
    scala.limit2(0.5f) shouldBe java.limit2(0.5f).asScala
    reset()
    scala.limit2(10000000f) shouldBe java.limit2(10000000f).asScala
    reset()

    scala.setLength(5f) shouldBe java.setLength(5f).asScala
    reset()
    scala.setLength2(5f) shouldBe java.setLength2(5f).asScala
    reset()

    scala.clamp(0.5f, 0.5f) shouldBe java.clamp(0.5f, 0.5f).asScala
    reset()
    scala.clamp(10000f, 3f) shouldBe java.clamp(10000f, 3f).asScala
    reset()
    scala.clamp(0f, 0f) shouldBe java.clamp(0f, 0f).asScala
    reset()

    Vector2.distance(1f, 2f, 3f, 4f) shouldBe JVector2.dst(1f, 2f, 3f, 4f)
    Vector2.distance2(4f, 3f, 2f, 1) shouldBe JVector2.dst2(4f, 3f, 2f, 1f)
    Vector2.distance(1f, 2f, 3f, 4f) shouldBe scala.distance(3f, 4f)
    Vector2.distance2(1f, 2f, 3f, 4f) shouldBe scala.distance2(3f, 4f)
    Vector2.distance(1f, 2f, 3f, 4f) shouldBe scala.distance(Vector2(3f, 4f))
    Vector2.distance2(1f, 2f, 3f, 4f) shouldBe scala.distance2(Vector2(3f, 4f))

    scala.lerp(Vector2(100f, 100f), 0.7f) shouldBe java.lerp(new JVector2(100f, 100f), 0.7f).asScala
    reset()
    val interpolation = new Interpolation.BounceIn(3)
    scala.interpolate(Vector2(123f, 321f), 0.85f, interpolation) shouldBe
      java.interpolate(new JVector2(123f, 321f), 0.85f, interpolation).asScala
    reset()

    val margin = 0.1f
    scala.isUnit shouldBe java.isUnit
    scala.isUnit(margin) shouldBe java.isUnit(margin)
    scala.isZero shouldBe java.isZero
    scala.isZero(margin) shouldBe java.isZero(margin)
    Vector2().isZero shouldBe JVector2.Zero.isZero
    scala.isOnLine(scala) shouldBe java.isOnLine(java)
    scala.isOnLine(scala, margin) shouldBe java.isOnLine(java, margin)
    scala.isCollinear(scala) shouldBe java.isCollinear(java)
    scala.isCollinear(scala, margin) shouldBe java.isCollinear(java, margin)
    scala.isCollinearOpposite(scala) shouldBe java.isCollinearOpposite(java)
    scala.isCollinearOpposite(scala, margin) shouldBe java.isCollinearOpposite(java, margin)
    scala.isPerpendicular(scala) shouldBe java.isPerpendicular(java)
    scala.isPerpendicular(scala, margin) shouldBe java.isPerpendicular(java, margin)
    scala.hasSameDirection(scala) shouldBe java.hasSameDirection(java)
    scala.hasOppositeDirection(scala) shouldBe java.hasOppositeDirection(java)
    scala.epsilonEquals(2f, 2f, margin) shouldBe java.epsilonEquals(2f, 2f, margin)
    scala.epsilonEquals(1f, 1f, margin) shouldBe java.epsilonEquals(1f, 1f, margin)
    scala.epsilonEquals(scala, margin) shouldBe java.epsilonEquals(java, margin)
    scala.epsilonEquals(scala) shouldBe java.epsilonEquals(java)

    scala.scaleAdd(scala, margin) shouldBe java.mulAdd(java, margin).asScala
    reset()
    scala.scaleAdd(scala, scala) shouldBe java.mulAdd(java, java).asScala
    reset()

    scala.dot(2f, 2f) shouldBe java.dot(2f, 2f)
    scala.dot(scala) shouldBe java.dot(java)

    scala.asString shouldBe java.toString

    val string = "( 54243.23234 ,   6453543.1)"
    Vector2.fromString(Refined.unsafeApply(string)) shouldBe java.fromString(string).asScala

  }

  it should "return the same instance" in {
    val scala = Vector2()

    scala.setLength2(0f) should be theSameInstanceAs scala
    scala.clamp(123f, 321f) should be theSameInstanceAs scala
    scala.normalize should be theSameInstanceAs scala
  }
}
