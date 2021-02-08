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
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import org.scalactic.source.Position
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalagdx.math.syntax.Vector3Syntax

@SuppressWarnings(Array("org.wartremover.warts.ToString"))
class MutableVector3SyntaxTest extends AnyFlatSpec with Matchers {

  "MutableVector3" should "construct through apply method" in {
    MutableVector3() shouldBe new MutableVector3()
    MutableVector3(1f, 2f, 3f) shouldBe new MutableVector3(1f, 2f, 3f)
  }

  it should "construct through string" in {
    val string = "(123.123, 321.12  ,-345456.1234124)"
    MutableVector3.fromString(Refined.unsafeApply(string)) shouldBe MutableVector3().fromString(string)
  }

  it should "be an alias for com.badlogic.gdx.math.Vector3" in {
    MutableVector3() shouldBe a[com.badlogic.gdx.math.Vector3]
  }

  it should "should destructure through unapply method" in {
    val MutableVector3(x, y, z) = MutableVector3(1f, 2f, 3f)
    x shouldBe 1f
    y shouldBe 2f
    z shouldBe 3f
  }

  "Vector3" should "destructure a mutable vector" in {
    assertThrows[MatchError] {
      // Vector2 can only destruct Vector3 instances, which MutableVector3 is not
      val Vector3(x, y, z) = MutableVector3(1f, 2f, 3f)
      x shouldBe 1f // Will not run
      y shouldBe 2f // Will not run
      z shouldBe 3f // Will not run
    }
    import scalagdx.math.syntax.vector3._
    assertThrows[MatchError] {
      // Implicit conversion method is imported, but Vector3 still cannot destructure MutableVector3s
      val Vector3(x, y, z) = MutableVector3(1f, 2f, 3f)
      x shouldBe 1f // Will not run
      y shouldBe 2f // Will not run
      z shouldBe 3f // Will not run
    }
    // MutableVector2 now forces an implicit conversion, so destructuring with Vector3 works
    val Vector3(x, y, z) = MutableVector3(1f, 2f, 3f): Vector3[MutableVector3]
    x shouldBe 1f
    y shouldBe 2f
    z shouldBe 3f
  }

  object M extends Vector3Syntax
  private val vector: M.MutableVector3Instance = M.MutableVector3Instance(MutableVector3())
  private val mutable: MutableVector3 = MutableVector3()
  private val margin = .123f
  private val matrix3: Matrix3 = new Matrix3()
  private val matrix4: Matrix4 = new Matrix4()

  private def reset(x: Float = 123.321f, y: Float = -321.123f, z: Float = 9999f) = {
    vector.toMutable.set(x, y, z)
    mutable.set(x, y, z)
  }

  private def assertVector(
      f: M.MutableVector3Instance => MutableVector3
  )(g: MutableVector3 => MutableVector3)(implicit P: Position) = {
    val result = f(vector)
    result shouldBe g(mutable)
    result shouldBe theSameInstanceAs(vector.toMutable)
    reset()
  }

  private def assertValue(f: Vector3[MutableVector3] => Any)(g: MutableVector3 => Any)(implicit P: Position) = {
    f(vector) shouldBe g(mutable)
    reset()
  }

  private def assertMutation(
      f: Vector3[MutableVector3] => Unit
  )(g: MutableVector3 => MutableVector3)(implicit P: Position) = {
    val result = MutableVector3(100f, -200f, -43598.321f)
    val expected = g(result.cpy())
    f(M.MutableVector3Instance(result))
    result shouldBe expected
  }

  private def assertOperator(f: (Vector3[MutableVector3], Vector3[MutableVector3]) => MutableVector3)(
      g: MutableVector3 => MutableVector3
  )(implicit P: Position) = {
    val vector = M.MutableVector3Instance(MutableVector3(123f, 321f, -564.1234f))
    val result = f(vector, vector)
    val expected = g(vector.toMutable)
    result shouldBe expected
    result shouldNot be(theSameInstanceAs(vector.toMutable))
  }

  "Vector3Syntax.MutableVector3Instance and MutableVector3" should "have the same string representation" in {
    assertValue(_.asString)(_.toString)
    assertValue(_.toString)(_.toString)
  }

  it should "convert to new immutable vector and provide the same mutable instance" in {
    import scalagdx.math.syntax.vector3._
    val vector = MutableVector3(1f, 2f, 3f)

    vector.toImmutable shouldNot be(theSameInstanceAs(vector))
    vector.toMutable shouldBe theSameInstanceAs(vector)
  }

  it should "copy to a new instance" in {
    import scalagdx.math.syntax.vector3._
    val vector: MutableVector3 = MutableVector3(1f, 2f, 3f)
    vector.copy() shouldNot be(theSameInstanceAs(vector))
    vector.copy(y = 123f) === MutableVector3(1f, 123f, 3f) shouldBe true
  }

  it should "check for equality using an epsilon" in {
    assertValue(_.epsilonEquals(1f, 2f, 3f, margin))(_.epsilonEquals(1f, 2f, 3f, margin))
    assertValue(_.epsilonEquals(1f, 2f, 3f))(_.epsilonEquals(1f, 2f, 3f))
    assertValue(it => it.epsilonEquals(it))(it => it.epsilonEquals(it))
    assertValue(it => it.epsilonEquals(it, margin))(it => it.epsilonEquals(it, margin))
  }

  it should "add" in {
    assertVector(_.add(1f))(_.add(1f))
    assertVector(_.add(1f, 2f, 3f))(_.add(1f, 2f, 3f))
    assertVector(it => it.add(it))(it => it.add(it))
  }

  it should "subtract" in {
    assertVector(_.sub(1f))(_.sub(1f))
    assertVector(_.sub(1f, 2f, 3f))(_.sub(1f, 2f, 3f))
    assertVector(it => it.sub(it))(it => it.sub(it))
  }

  it should "multiply" in {
    assertVector(_.scl(1f))(_.scl(1f))
    assertVector(_.scl(1f, 2f, 3f))(_.scl(1f, 2f, 3f))
    assertVector(it => it.scl(it))(it => it.scl(it))
  }

  it should "divide" in {
    assertVector(_.div(1f))(_.scl(1f))
    assertVector(_.div(1f, 2f, 4f))(_.scl(1f, .5f, .25f))
    assertVector(it => it.div(M.MutableVector3Instance(MutableVector3(1f, 2f, 4f))))(_.scl(1f, .5f, .25f))
  }

  it should "create new instances on arithmetic operators" in {
    import scalagdx.math.syntax.vector3._
    assertOperator(_ + _)(it => it.add(it))
    assertOperator(_ - _)(it => it.sub(it))
    assertOperator(_ * _)(it => it.scl(it))
    assertOperator(_ / _)(it => it.div(it))
    assertOperator(_.toMutable + _)(it => it.add(it))
    assertOperator(_.toMutable - _)(it => it.sub(it))
    assertOperator(_.toMutable * _)(it => it.scl(it))
    assertOperator(_.toMutable / _)(it => it.div(it))
  }

  it should "mutate through assignment operators" in {
    import scalagdx.math.syntax.vector3._
    assertMutation(it => it += it / it - it * it + it)(it => it.add(it / it - it * it + it))
    assertMutation(it => it -= it / it - it * it + it)(it => it.sub(it / it - it * it + it))
    assertMutation(it => it *= it / it - it * it + it)(it => it.scl(it / it - it * it + it))
    assertMutation(it => it /= it / it - it * it + it)(it => it.div(it / it - it * it + it))
    assertMutation(it => it.toMutable += M.MutableVector3Instance(it / it - it * it))(it => it.add(it / it - it * it))
    assertMutation(it => it.toMutable -= M.MutableVector3Instance(it / it - it * it))(it => it.sub(it / it - it * it))
    assertMutation(it => it.toMutable *= M.MutableVector3Instance(it / it - it * it))(it => it.scl(it / it - it * it))
    assertMutation(it => it.toMutable /= M.MutableVector3Instance(it / it - it * it))(it => it.div(it / it - it * it))
  }

  it should "have exact equality" in {
    assertValue(it => it.idt(it))(it => it.idt(it))
  }

  it should "calculate distance" in {
    assertValue(it => it.dst(it))(it => it.dst(it))
    assertValue(_.dst(1f, 2f, 3f))(_.dst(1f, 2f, 3f))
    assertValue(it => it.dst2(it))(it => it.dst2(it))
    assertValue(_.dst2(1f, 2f, 3f))(_.dst2(1f, 2f, 3f))
  }

  it should "calculate dot product" in {
    assertValue(_.dot(1f, 2f, 3f))(_.dot(1f, 2f, 3f))
    assertValue(it => it.dot(it))(it => it.dot(it))
  }

  it should "calculate cross product" in {
    assertVector(it => it.crs(it))(it => it.crs(it))
  }

  it should "left multiply by a matrix" in {
    val matrix = Seq(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f)
    assertVector(_.mul4x3(Refined.unsafeApply(matrix)))(_.mul4x3(matrix.toArray))

    assertVector(_.mul(matrix3))(_.mul(matrix3))
    assertVector(_.mul(matrix4))(_.mul(matrix4))

    val quaternion = new Quaternion()
    assertVector(_.mul(quaternion))(_.mul(quaternion))
  }

  it should "multiply by the transpose of a matrix" in {
    assertVector(_.traMul(matrix3))(_.traMul(matrix3))
    assertVector(_.traMul(matrix4))(_.traMul(matrix4))
  }

  it should "project" in {
    assertVector(_.prj(matrix4))(_.prj(matrix4))
  }

  it should "rotate" in {
    assertVector(_.rot(matrix4))(_.rot(matrix4))
    assertVector(_.rotate(1f, 2f, 3f, 4f))(_.rotate(1f, 2f, 3f, 4f))
    assertVector(it => it.rotate(it, 1f))(it => it.rotate(it, 1f))
    assertVector(_.rotateRad(1f, 2f, 3f, 4f))(_.rotateRad(1f, 2f, 3f, 4f))
    assertVector(it => it.rotateRad(it, 1f))(it => it.rotateRad(it, 1f))
  }

  it should "unrotate" in {
    assertVector(_.unrotate(matrix4))(_.unrotate(matrix4))
  }

  it should "untransform" in {
    assertVector(_.untransform(matrix4))(_.untransform(matrix4))
  }

  it should "interpolate" in {
    assertVector(it => it.lerp(it, .1f))(it => it.lerp(it, .1f))

    val interpolation = new Interpolation.ExpOut(1f, 2f)
    assertVector(it => it.interpolate(it, .1f, interpolation))(it => it.interpolate(it, .1f, interpolation))

    for (i <- 0 until 100; j <- 0 until 100; k <- 0 until 100) {
      reset(1f, 2f, 3f)
      val vec3 = Vector3(i.toFloat, j.toFloat, k.toFloat)
      assertVector(_.slerp(vec3, 1f))(_.slerp(vec3.toMutable, 1f))
    }
    assertVector(it => it.slerp(it, 1f))(it => it.slerp(it, 1f))
  }

  it should "calculate length" in {
    assertValue(_.len)(_.len)
    assertValue(_.len2)(_.len2)
  }

  it should "limit" in {
    assertVector(_.limit(margin))(_.limit(margin))
    assertVector(_.limit2(margin))(_.limit2(margin))
    Vector3().limit2(margin).toMutable shouldBe MutableVector3().limit2(margin)
  }

  it should "set length" in {
    assertVector(_.setLength(margin))(_.setLength(margin))
    assertVector(_.setLength2(margin))(_.setLength2(margin))
    Vector3().setLength2(0f).toMutable shouldBe MutableVector3().setLength2(0f)
  }

  it should "clamp" in {
    for (i <- 0 until 1000; j <- 0 until 1000) {
      assertVector(_.clamp(i.toFloat, j.toFloat))(_.clamp(i.toFloat, j.toFloat))
    }
    Vector3().clamp(1f, 1f).toMutable shouldBe MutableVector3().clamp(1f, 1f)
  }

  it should "normalize" in {
    assertVector(_.nor)(_.nor)
    Vector3().nor.toMutable shouldBe MutableVector3().nor
  }

  it should "check is unit" in {
    assertValue(_.isUnit)(_.isUnit)
    assertValue(_.isUnit(margin))(_.isUnit(margin))
  }

  it should "check is zero" in {
    assertValue(_.isZero)(_.isZero)
    assertValue(_.isZero(margin))(_.isZero(margin))
    Vector3().isZero shouldBe MutableVector3().isZero
  }

  it should "check is on line" in {
    assertValue(it => it.isOnLine(it))(it => it.isOnLine(it))
    assertValue(it => it.isOnLine(vector, margin))(it => it.isOnLine(it, margin))
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

  it should "multiply by a scalar and add" in {
    assertVector(it => it.mulAdd(it, margin))(it => it.mulAdd(it, margin))
    assertVector(it => it.mulAdd(it, it))(it => it.mulAdd(it, it))
  }

  it should "have relational operators" in {
    import scalagdx.math.syntax.vector3._
    val v1 = MutableVector3()
    val v2 = MutableVector3(1f, 1f, 1f)
    v1 < v2 shouldBe true
    v1 > v2 shouldBe false
    v1 <= v1 shouldBe true
    v1 >= v1 shouldBe true
  }

  it should "have the same hashcode" in {
    assertValue(_.hashCode)(_.hashCode)
  }

  it should "construct from spherical coordinate" in {
    Vector3.fromSpherical(1f, 2f).toMutable shouldBe MutableVector3().setFromSpherical(1f, 2f)
  }
}
