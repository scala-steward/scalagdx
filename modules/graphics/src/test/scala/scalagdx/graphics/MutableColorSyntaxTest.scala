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
package scalagdx.graphics

import scala.util.Sorting

import com.badlogic.gdx.graphics.{Color => JColor}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import org.scalactic.source.Position
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalagdx.graphics.Color.Alpha
import scalagdx.graphics.syntax.ColorSyntax

class MutableColorSyntaxTest extends AnyFlatSpec with Matchers {

  "MutableColor" should "construct through apply method" in {
    MutableColor(.1f, .2f, .3f, .4f) shouldBe new MutableColor(.1f, .2f, .3f, .4f)
  }

  it should "destructure" in {
    val MutableColor(r, g, b, a) = MutableColor(.1f, .2f, .3f, .4f)
    r shouldBe .1f
    g shouldBe .2f
    b shouldBe .3f
    a shouldBe .4f
  }

  it should "be an alias for com.badlogic.gdx.graphics.Color" in {
    MutableColor() shouldBe a[com.badlogic.gdx.graphics.Color]
  }

  "Vector2" should "destructure MutableVector2" in {
    def assertExpectedResult(eR: Float, eG: Float, eB: Float, eA: Float)(r: Float, g: Float, b: Float, a: Float)(
        implicit P: Position
    ) = {
      r shouldBe eR
      g shouldBe eG
      b shouldBe eB
      a shouldBe eA
    }
    def assertResult = assertExpectedResult(.1f, .2f, .3f, .4f) _
    assertThrows[MatchError] {
      // Color can only destructure Color instances, which MutableColor is not
      val Color(r, g, b, a) = MutableColor(.1f, .2f, .3f, .4f)
      assertResult(r, g, b, a) // Will not run
    }
    import scalagdx.graphics.syntax.color._
    assertThrows[MatchError] {
      // Implicit conversion method is imported, but Color still cannot destructure MutableColors
      val Color(r, g, b, a) = MutableColor(.1f, .2f, .3f, .4f)
      assertResult(r, g, b, a) // Will not run
    }
    // Color now forces an implicit conversion, so destructuring with MutableColor works
    val Color(r, g, b, a) = MutableColor(.1f, .2f, .3f, .4f): Color[MutableColor]
    assertResult(r, g, b, a)
  }

  object M extends ColorSyntax
  private val color = M.MutableColorInstance(MutableColor())
  private val mutable = MutableColor()
  private val alpha: Float Refined Alpha = .123f

  private def reset(r: Float = .4f, g: Float = .3f, b: Float = .2f, a: Float = .1f) = {
    color.toMutable.set(r, g, b, a)
    mutable.set(r, g, b, a)
  }
  reset()

  private def assertColor(
      f: M.MutableColorInstance => MutableColor
  )(g: MutableColor => MutableColor)(implicit P: Position) = {
    val result = f(color)
    result shouldBe g(mutable)
    result shouldBe theSameInstanceAs(color.toMutable)
    reset()
  }

  private def assertValue(f: M.MutableColorInstance => Any)(g: MutableColor => Any)(implicit P: Position) = {
    f(color) shouldBe g(mutable)
    reset()
  }

  private def assertMutation(
      f: Color[MutableColor] => Unit
  )(g: MutableColor => MutableColor)(implicit P: Position) = {
    val result = MutableColor(.1f, .2f, .3f, .4f)
    val expected = g(result.cpy())
    f(M.MutableColorInstance(result))
    result shouldBe expected
  }

  private def assertOperator(
      f: (Color[MutableColor], Color[MutableColor]) => MutableColor
  )(g: MutableColor => MutableColor)(implicit P: Position) = {
    val color = M.MutableColorInstance(MutableColor(.123f, .456f, .789f, .987f))
    val result = f(color, color)
    val expected = g(color.toMutable)
    result shouldBe expected
    result shouldNot be(theSameInstanceAs(color.toMutable))
  }

  "ColorSyntax.MutableColorInstance and MutableColor" should "have the same hashcode" in {
    assertValue(_.hashCode)(_.hashCode)
    M.MutableColorInstance(MutableColor()).hashCode shouldBe MutableColor().hashCode
  }

  it should "copy to a new instance" in {
    val color = M.MutableColorInstance(MutableColor(.1f, .2f, .3f, .4f))
    color.copy(g = .9f) shouldBe MutableColor(.1f, .9f, .3f, .4f)
    color.copy() shouldNot be(theSameInstanceAs(color))
  }

  it should "multiply" in {
    assertColor(_.mul(1f, 2f, 3f, 4f))(_.mul(1f, 2f, 3f, 4f))
    assertColor(it => it.mul(it))(it => it.mul(it))
    assertColor(_.mul(2f))(_.mul(2f))
  }

  it should "add" in {
    assertColor(_.add(1f, 2f, 3f, 4f))(_.add(1f, 2f, 3f, 4f))
    assertColor(it => it.add(it))(it => it.add(it))
    assertColor(_.add(2f))(_.add(2f, 2f, 2f, 2f))
  }

  it should "subtract" in {
    assertColor(_.sub(1f, 2f, 3f, 4f))(_.sub(1f, 2f, 3f, 4f))
    assertColor(it => it.sub(it))(it => it.sub(it))
    assertColor(_.sub(2f))(_.sub(2f, 2f, 2f, 2f))
  }

  it should "divide" in {
    assertColor(_.div(1f, 2f, 4f, 10f))(_.mul(1f, .5f, .25f, .1f))
    assertColor(_.div(Color(1f, 1f, 1f, 1f)))(_.mul(1f, 1f, 1f, 1f))
    assertColor(_.div(1f))(_.mul(1f, 1f, 1f, 1f))

    val color = M.MutableColorInstance(MutableColor(0f, 1f))
    color.div(0f)
    color.r.value.isNaN shouldBe true
    color.g.value shouldBe 1f // Float.PositiveInfinity rounded down to 1f
  }

  it should "create new instances on arithmetic operators" in {
    import scalagdx.graphics.syntax.color._
    assertOperator(_ + _)(it => it.add(it))
    assertOperator(_ - _)(it => it.sub(it))
    assertOperator(_ * _)(it => it.mul(it))
    assertOperator(_ / _)(it => it.div(it))
    assertOperator(_.toMutable + _)(it => it.add(it))
    assertOperator(_.toMutable - _)(it => it.sub(it))
    assertOperator(_.toMutable * _)(it => it.mul(it))
    assertOperator(_.toMutable / _)(it => it.div(it))
  }

  it should "mutate through assignment operators" in {
    import scalagdx.graphics.syntax.color._
    assertMutation(it => it += it / it - it * it + it)(it => it.add(it / it - it * it + it))
    assertMutation(it => it -= it / it - it * it + it)(it => it.sub(it / it - it * it + it))
    assertMutation(it => it *= it / it - it * it + it)(it => it.mul(it / it - it * it + it))
    assertMutation(it => it /= it / it - it * it + it)(it => it.div(it / it - it * it + it))
    assertMutation(it => it.toMutable += M.MutableColorInstance(it / it - it * it))(it => it.add(it / it - it * it))
    assertMutation(it => it.toMutable -= M.MutableColorInstance(it / it - it * it))(it => it.sub(it / it - it * it))
    assertMutation(it => it.toMutable *= M.MutableColorInstance(it / it - it * it))(it => it.mul(it / it - it * it))
    assertMutation(it => it.toMutable /= M.MutableColorInstance(it / it - it * it))(it => it.div(it / it - it * it))
  }

  it should "linearly interpolate" in {
    assertColor(_.lerp(1f, 2f, 3f, 4f, alpha))(_.lerp(1f, 2f, 3f, 4f, alpha))
    assertColor(it => it.lerp(it, alpha))(it => it.lerp(it, alpha))
  }

  it should "premultiply alpha" in {
    assertColor(_.premultiplyAlpha)(_.premultiplyAlpha)
  }

  it should "convert to int bits" in {
    assertValue(_.toIntBits)(_.toIntBits)
    Color.toIntBits(50, 100, 150, 200) shouldBe JColor.toIntBits(50, 100, 150, 200)
  }

  it should "convert to float bits" in {
    assertValue(_.toFloatBits)(_.toFloatBits)
    Color.toFloatBits(.1f, .2f, .3f, .4f) shouldBe JColor.toFloatBits(.1f, .2f, .3f, .4f)
    Color.toFloatBits(50, 100, 150, 200) shouldBe JColor.toFloatBits(50, 100, 150, 200)
  }

  it should "pack the alpha value" in {
    Color.alpha(.2f) shouldBe JColor.alpha(.2f)
  }

  it should "pack the luminance alpha" in {
    Color.luminanceAlpha(.5f, .6f) shouldBe JColor.luminanceAlpha(.5f, .6f)
  }

  it should "pack rgb565" in {
    Color.toRgb565(1, 2, 3) shouldBe JColor.rgb565(1, 2, 3)
    assertValue(_.toRgb565)(JColor.rgb565(_))

    val color = MutableColor()
    JColor.rgb565ToColor(color, 1)
    MutableColor.fromRgb565(1) shouldBe color
  }

  it should "pack rgba4444" in {
    Color.toRgba4444(1f, 2f, 3f, 4f) shouldBe JColor.rgba4444(1f, 2f, 3f, 4f)
    assertValue(_.toRgba4444)(JColor.rgba4444(_))

    val color = MutableColor()
    JColor.rgba4444ToColor(color, 1)
    MutableColor.fromRgba4444(1) shouldBe color
  }

  it should "pack rgb888" in {
    Color.toRgb888(.1f, .2f, .3f) shouldBe JColor.rgb888(.1f, .2f, .3f)
    assertValue(_.toRgb888)(JColor.rgb888(_))

    val color = MutableColor()
    JColor.rgb888ToColor(color, 1)
    MutableColor.fromRgb888(1) shouldBe color
  }

  it should "pack rgba8888" in {
    Color.toRgba8888(.1f, .2f, .3f, .4f) shouldBe JColor.rgba8888(.1f, .2f, .3f, .4f)
    assertValue(_.toRgba8888)(JColor.rgba8888(_))

    val color = MutableColor()
    JColor.rgba8888ToColor(color, 1)
    MutableColor.fromRgba8888(1) shouldBe color
  }

  it should "pack argb8888" in {
    Color.toArgb8888(.1f, .2f, .3f, .4f) shouldBe JColor.argb8888(.1f, .2f, .3f, .4f)
    assertValue(_.toArgb8888)(JColor.argb8888(_))

    val color = MutableColor()
    JColor.argb8888ToColor(color, 1)
    MutableColor.fromArgb8888(1) shouldBe color
  }

  it should "convert to hex" in {
    assertValue(_.toHex)("#" + _.toString)
    assertValue(_.toString)(_.toString)
    M.MutableColorInstance(MutableColor()).toHex shouldBe "#" + MutableColor().toString
    MutableColor.fromHex("#FFFFFF") shouldBe JColor.valueOf("#FFFFFF")
  }

  it should "convert to hsv" in {
    def mutableHsv(color: MutableColor) = {
      val array = Array(0f, 0f, 0f)
      color.toHsv(array)
      Hsv(array(0), array(1), array(2))
    }

    for (i <- 0 until 1000; j <- 0 until 1000) {
      val a = i.toFloat / 1000f
      val b = j.toFloat / 1000f
      val c = a - 1f
      val d = b - 1f
      val color = MutableColor(a, b, if (c < 0f) -c else c, if (d < 0f) -d else d)
      assertValue(_ => M.MutableColorInstance(color).toHsv)(_ => mutableHsv(color))
    }
    assertValue(_ => M.MutableColorInstance(MutableColor()).toHsv)(_ => mutableHsv(MutableColor()))
    MutableColor.fromHsv(1f, 2f, 3f) shouldBe MutableColor().fromHsv(1f, 2f, 3f)
    MutableColor.fromHsv(Hsv(1f, 2f, 3f)) shouldBe MutableColor.fromHsv(1f, 2f, 3f)
  }

  it should "convert from abgr8888" in {
    val color = MutableColor()
    JColor.abgr8888ToColor(color, 1f)
    MutableColor.fromAbgr8888(1f) shouldBe color
  }

  it should "sort" in {
    import scalagdx.graphics.syntax.color._
    val c1 = MutableColor()
    val c2 = MutableColor(.1f, .1f, .1f, .1f)
    val c3 = MutableColor(.2f, .2f, .2f, .2f)
    val c4 = MutableColor(.3f, .3f, .3f, .3f)
    val array: Array[Color[_]] = Array(c4, c2, c1, c3)
    Sorting.quickSort(array)
    array(0).toMutable shouldBe theSameInstanceAs(c1)
    array(1).toMutable shouldBe theSameInstanceAs(c2)
    array(2).toMutable shouldBe theSameInstanceAs(c3)
    array(3).toMutable shouldBe theSameInstanceAs(c4)
  }

  it should "check for equality" in {
    val wrapped = M.MutableColorInstance(MutableColor())
    val color = MutableColor()

    // Regular equality should fail
    wrapped shouldNot be(color)

    // Gotcha: Implicit is imported but equality still fails
    import scalagdx.graphics.syntax.color._
    wrapped shouldNot be(color)

    // === forces an implicit conversion, which now correctly checks for equality
    wrapped === color shouldBe true
    wrapped =!= color shouldBe false
  }

  it should "convert to immutable and provide same mutable instance" in {
    import scalagdx.graphics.syntax.color._
    val color = M.MutableColorInstance(MutableColor())
    val immutable = color.toImmutable
    val mutable = color.toMutable
    mutable === immutable shouldBe true
    mutable shouldBe theSameInstanceAs(color.toMutable)
  }
}
