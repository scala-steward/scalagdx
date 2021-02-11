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

import scala.util.Sorting

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Matrix3
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import org.scalactic.source.Position
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ImmutableVector2Test extends AnyFlatSpec with Matchers {

  "ImmutableVector2" should "construct through apply method" in {
    ImmutableVector2(1f, 2f) shouldBe new ImmutableVector2(1f, 2f)
  }

  it should "have a string representation" in {
    ImmutableVector2(1f, 2f).toString shouldBe "ImmutableVector2(1.0, 2.0)"
  }

  it should "destructure" in {
    val ImmutableVector2(x, y) = ImmutableVector2(1f, 2f)
    x shouldBe 1f
    y shouldBe 2f
  }

  it should "construct through string" in {
    import scalagdx.math.syntax.vector2._
    val string = "(123.123, 321.12)"
    ImmutableVector2.fromString(Refined.unsafeApply(string)) === MutableVector2().fromString(string) shouldBe true
  }

  "Vector2" should "construct an immutable vector through apply method" in {
    Vector2(1f, 2f) shouldBe ImmutableVector2(1f, 2f)
  }

  it should "construct an immutable vector through string" in {
    import scalagdx.math.syntax.vector2._
    val string = "(123.123, 321.12)"
    Vector2.fromString(Refined.unsafeApply(string)) === MutableVector2().fromString(string) shouldBe true
  }

  private val vector: Vector2[ImmutableVector2] = ImmutableVector2(1f, 2f)
  private val mutable = vector.toMutable
  private val margin = .123f
  private def reset() = mutable.set(vector.x, vector.y)

  private def assertVector(f: Vector2[ImmutableVector2] => ImmutableVector2)(g: MutableVector2 => MutableVector2)(
      implicit P: Position
  ) = {
    import scalagdx.math.syntax.vector2._
    val result = f(vector)
    val expected = g(mutable).toImmutable
    result shouldBe expected
    result shouldNot be(theSameInstanceAs(expected))
    reset()
  }

  private def assertValue(f: Vector2[ImmutableVector2] => Any)(g: MutableVector2 => Any)(implicit P: Position) = {
    f(vector) shouldBe g(mutable)
    reset()
  }

  private def assertOperator(f: (Vector2[ImmutableVector2], Vector2[ImmutableVector2]) => ImmutableVector2)(
      g: ImmutableVector2 => ImmutableVector2
  )(implicit P: Position) = {
    val vector = ImmutableVector2(123f, 321f)
    val result = f(vector, vector)
    val expected = g(vector)
    reset()
    result shouldBe expected
  }

  "Vector2Syntax.MutableVector2Instance and MutableVector2" should "calculate length" in {
    assertValue(_.len)(_.len)
    assertValue(_.len2)(_.len2)
  }

  it should "setLength" in {
    assertVector(_.setLength(2f))(_.setLength(2f))
    assertVector(_.setLength2(2f))(_.setLength2(2f))
  }

  it should "limit" in {
    assertVector(_.limit(1f))(_.limit(1f))
    assertVector(_.limit2(1f))(_.limit2(1f))
    assertVector(_.limit2(1000f))(_.limit2(1000f))
    assertVector(it => it.limit2(it.len2))(it => it.limit2(it.len2))
  }

  it should "clamp" in {
    assertVector(_.clamp(0f, 10000000f))(_.clamp(0f, 10000000f))
    assertVector(_.clamp(10000000f, 0f))(_.clamp(10000000f, 0f))
    assertVector(_.clamp(0f, 0f))(_.clamp(0f, 0f))
    assertVector(_.clamp(.5f, .5f))(_.clamp(.5f, .5f))
    assertVector(_.clamp(10000f, 3f))(_.clamp(10000f, 3f))
  }

  it should "subtract" in {
    assertVector(_.sub(1f, 2f))(_.sub(1f, 2f))
    assertVector(_.sub(1f))(_.sub(1f, 1f))
    assertVector(it => it.sub(it))(it => it.sub(it))
  }

  it should "add" in {
    assertVector(_.add(1f, 2f))(_.add(1f, 2f))
    assertVector(_.add(10f))(_.add(10f, 10f))
    assertVector(it => it.add(it))(it => it.add(it))
  }

  it should "multiply" in {
    assertVector(_.scl(1f, 2f))(_.scl(1f, 2f))
    assertVector(_.scl(10f))(_.scl(10f, 10f))
    assertVector(it => it.scl(it))(it => it.scl(it))
  }

  it should "divide" in {
    assertVector(_.div(1f, 2f))(_.scl(1f, .5f))
    assertVector(_.div(1f))(_.scl(1f))
    assertVector(_.div(ImmutableVector2(2f, 4f)))(_.scl(.5f, .25f))
  }

  it should "create new instances on arithmetic operators" in {
    assertOperator(_ + _)(it => it.add(it))
    assertOperator(_ - _)(it => it.sub(it))
    assertOperator(_ * _)(it => it.scl(it))
    assertOperator(_ / _)(it => it.div(it))
    assertOperator(_.toImmutable + _)(it => it.add(it))
    assertOperator(_.toImmutable - _)(it => it.sub(it))
    assertOperator(_.toImmutable * _)(it => it.scl(it))
    assertOperator(_.toImmutable / _)(it => it.div(it))
  }

  it should "mulAdd" in {
    assertVector(it => it.mulAdd(it, margin))(it => it.mulAdd(it, margin))
    assertVector(it => it.mulAdd(it, it))(it => it.mulAdd(it, it))
  }

  it should "normalize" in {
    assertVector(_.nor)(_.nor)
  }

  it should "calculate distance" in {
    assertValue(_.dst(1f, 2f))(_.dst(1f, 2f))
    assertValue(_.dst2(1f, 2f))(_.dst2(1f, 2f))
    assertValue(it => it.dst(it))(it => it.dst(it))
    assertValue(it => it.dst2(it))(it => it.dst2(it))
  }

  it should "interpolate" in {
    assertVector(it => it.lerp(it, .1f))(it => it.lerp(it, .1f))
    val interpolation = new Interpolation.BounceIn(3)
    assertVector(it => it.interpolate(it, .1f, interpolation))(it => it.interpolate(it, .1f, interpolation))
  }

  it should "check is unit" in {
    assertValue(_.isUnit)(_.isUnit)
    assertValue(_.isUnit(margin))(_.isUnit(margin))
  }

  it should "check is zero" in {
    assertValue(_.isZero)(_.isZero)
    assertValue(_.isZero(margin))(_.isZero(margin))
    ImmutableVector2().isZero shouldBe ImmutableVector2().isZero
  }

  it should "check is on line" in {
    assertValue(it => it.isOnLine(it))(it => it.isOnLine(it))
    assertValue(it => it.isOnLine(it, margin))(it => it.isOnLine(it, margin))
  }

  it should "check is collinear" in {
    assertValue(it => it.isCollinear(it))(it => it.isCollinear(it))
    assertValue(it => it.isCollinear(it, margin))(it => it.isCollinear(it, margin))
  }

  it should "check is collinear opposite" in {
    assertValue(it => it.isCollinearOpposite(it))(it => it.isCollinearOpposite(it))
    assertValue(it => it.isCollinearOpposite(it, margin))(it => it.isCollinearOpposite(it, margin))
  }

  it should "check is perpendicular" in {
    assertValue(it => it.isPerpendicular(it))(it => it.isPerpendicular(it))
    assertValue(it => it.isPerpendicular(it, margin))(it => it.isPerpendicular(it, margin))
  }

  it should "check direction" in {
    assertValue(it => it.hasSameDirection(it))(it => it.hasSameDirection(it))
    assertValue(it => it.hasOppositeDirection(it))(it => it.hasOppositeDirection(it))
  }

  it should "check equality using an epsilon value" in {
    assertValue(it => it.epsilonEquals(it))(it => it.epsilonEquals(it))
    assertValue(_.epsilonEquals(.1f, .2f))(_.epsilonEquals(.1f, .2f))
    assertValue(it => it.epsilonEquals(it, margin))(it => it.epsilonEquals(it, margin))
    assertValue(_.epsilonEquals(.1f, .2f, margin))(_.epsilonEquals(.1f, .2f, margin))
  }

  it should "calculate the dot product" in {
    assertValue(_.dot(.1f, .2f))(_.dot(.1f, .2f))
    assertValue(it => it.dot(it))(it => it.dot(it))
  }

  it should "calculate the cross product" in {
    assertValue(_.crs(.1f, .2f))(_.crs(.1f, .2f))
    assertValue(it => it.crs(it))(it => it.crs(it))
  }

  it should "provide the x and y values" in {
    assertValue(_.x)(_.x)
    assertValue(_.y)(_.y)
  }

  it should "provide the same immutable instance and convert to new immutable instance" in {
    import scalagdx.math.syntax.vector2._
    val vector = ImmutableVector2()
    vector.toMutable === vector shouldBe true
    vector.toImmutable shouldBe theSameInstanceAs(vector)
  }

  it should "copy to new instance" in {
    val vector: ImmutableVector2 = ImmutableVector2(5f, 213f)
    vector shouldNot be(theSameInstanceAs(vector.copy()))
    vector.copy(x = 100f) shouldBe ImmutableVector2(100f, 213f)
  }

  it should "calculate the angle in degrees" in {
    assertValue(_.angleDeg)(_.angleDeg)
    assertValue(it => it.angleDeg(it))(it => it.angleDeg(it))
    for (i <- -100 to 100; j <- -100 to 100) {
      val vector = ImmutableVector2(i.toFloat, j.toFloat)
      val mutable = ImmutableVector2(i.toFloat, j.toFloat)
      vector.angleDeg(ImmutableVector2(j.toFloat, i.toFloat)) shouldBe
        mutable.angleDeg(ImmutableVector2(j.toFloat, i.toFloat))

      vector.angleDeg shouldBe mutable.angleDeg
    }
  }

  it should "calculate the angle in radians" in {
    assertValue(_.angleRad)(_.angleRad)
    assertValue(it => it.angleRad(it))(it => it.angleRad(it))
  }

  it should "rotate in radians" in {
    assertVector(_.rotateRad(1f))(_.rotateRad(1f))
    assertVector(it => it.rotateAroundRad(it, 1f))(it => it.rotateAroundRad(it.cpy(), 1f))
  }

  it should "rotate in degrees" in {
    assertVector(_.rotateDeg(1f))(_.rotateDeg(1f))
    assertVector(it => it.rotateAroundDeg(it, 1f))(it => it.rotateAroundDeg(it.cpy(), 1f))
  }

  it should "set the angle in degrees" in {
    assertVector(_.setAngleDeg(1f))(_.cpy() setAngleDeg (1f))
  }

  it should "set the angle in radians" in {
    assertVector(_.setAngleRad(1f))(_.setAngleRad(1f))
  }

  it should "rotate at a 90 degree angle" in {
    assertVector(_.rotate90(2))(_.rotate90(2))
    assertVector(_.rotate90(-1))(_.rotate90(-1))
  }

  it should "have the same string representation" in {
    assertValue(_.asString)(_.toString)
  }

  it should "multiply by a matrix" in {
    val matrix = new Matrix3()
    assertVector(_.mul(matrix))(_.cpy().mul(matrix))
  }

  it should "have relational operators" in {
    val v1 = ImmutableVector2()
    val v2 = ImmutableVector2(1f, 1f)
    v1 < v2 shouldBe true
    v1 > v2 shouldBe false
    v1 <= v1 shouldBe true
    v1 >= v1 shouldBe true
  }

  it should "sort" in {
    val v1 = ImmutableVector2(1000f).setLength2(1f)
    val v2 = ImmutableVector2(1000f).setLength2(2f)
    val v3 = ImmutableVector2(1000f).setLength2(3f)
    val v4 = ImmutableVector2(1000f).setLength2(4f)
    val v5 = ImmutableVector2(1000f).setLength2(5f)
    val array: Array[Vector2[_]] = Array(v4, v2, v1, v3, v5)
    Sorting.quickSort(array)
    array(0) === v1 shouldBe true
    array(1) === v2 shouldBe true
    array(2) === v3 shouldBe true
    array(3) === v4 shouldBe true
    array(4) === v5 shouldBe true
  }

  it should "have the same hashcode" in {
    assertValue(_.hashCode)(_.hashCode)
    assertValue(_.add(100f).hashCode)(_.add(100f, 100f).hashCode)
  }

  it should "check for equality" in {
    val vector = ImmutableVector2()
    val mutable = MutableVector2()

    // Vector2 can only compare to other Vector2 instances, so this should fail without an implicit conversion
    vector shouldNot be(mutable)

    // Gotcha: ScalaTiC provides === so this can look like the equality works, but it does not
    (vector === mutable) shouldBe false

    // Implicit is now imported, but using the non-type safe equality should still fail
    import scalagdx.math.syntax.vector2._
    vector shouldNot be(mutable)

    // Using === now forces an implicit conversion, which should allow Vector2 to compare to MutableVector2 now
    vector === mutable shouldBe true
    vector =!= mutable shouldBe false
  }

  it should "return the same instance" in {
    val vector = ImmutableVector2()
    vector.setLength2(1f) shouldBe theSameInstanceAs(vector)
    vector.clamp(1f, 1f) shouldBe theSameInstanceAs(vector)
    vector.nor shouldBe theSameInstanceAs(vector)
  }
}
