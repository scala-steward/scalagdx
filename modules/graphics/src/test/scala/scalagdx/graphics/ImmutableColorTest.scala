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

class ImmutableColorSyntaxTest extends AnyFlatSpec with Matchers {

  "ImmutableColor" should "construct through apply method" in {
    import scalagdx.graphics.syntax.color._
    ImmutableColor(.1f, .2f, .3f, .4f) === new MutableColor(.1f, .2f, .3f, .4f) shouldBe true
  }

  it should "provide toString implementation" in {
    ImmutableColor().toString shouldBe "ImmutableColor(0.0, 0.0, 0.0, 0.0)"
  }

  it should "destructure" in {
    val ImmutableColor(r, g, b, a) = ImmutableColor(.1f, .2f, .3f, .4f)
    r shouldBe .1f
    g shouldBe .2f
    b shouldBe .3f
    a shouldBe .4f
  }

  "Vector2" should "destructure MutableVector2" in {
    val Color(r, g, b, a) = ImmutableColor(.1f, .2f, .3f, .4f)
    r shouldBe .1f
    g shouldBe .2f
    b shouldBe .3f
    a shouldBe .4f
  }

  private val color = ImmutableColor(.1f, .2f, .3f, .4f)
  private val mutable = MutableColor()
  private val alpha: Float Refined Alpha = .123f

  private def reset(r: Float = color.r, g: Float = color.g, b: Float = color.b, a: Float = color.a) = {
    mutable.set(r, g, b, a)
  }
  reset()

  private def assertColor(
      f: ImmutableColor => ImmutableColor
  )(g: MutableColor => MutableColor)(implicit P: Position) = {
    val result = f(color)
    result.toMutable shouldBe g(mutable)
    reset()
  }

  private def assertValue(f: ImmutableColor => Any)(g: MutableColor => Any)(implicit P: Position) = {
    f(color) shouldBe g(mutable)
    reset()
  }

  private def assertOperator(
      f: (ImmutableColor, ImmutableColor) => ImmutableColor
  )(g: MutableColor => MutableColor)(implicit P: Position) = {
    import scalagdx.graphics.syntax.color._
    val color = ImmutableColor(.123f, .456f, .789f, .987f)
    val result = f(color, color)
    val expected = g(color.toMutable)
    result shouldBe expected.toImmutable
    reset()
  }

  "ImmutableColor and MutableColor" should "have the same hashcode" in {
    assertValue(_.hashCode)(_.hashCode)
    ImmutableColor().hashCode shouldBe MutableColor().hashCode
  }

  it should "copy to a new instance" in {
    val color = ImmutableColor(.1f, .2f, .3f, .4f)
    color.copy(g = .9f) shouldBe ImmutableColor(.1f, .9f, .3f, .4f)
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

    val color = ImmutableColor(0f, 1f).div(0f)
    color.r.value.isNaN shouldBe true
    color.g.value shouldBe 1f // Float.PositiveInfinity rounded down to 1f
  }

  it should "have arithmetic operators" in {
    import scalagdx.graphics.syntax.color._
    assertOperator(_ + _)(it => it.add(it))
    assertOperator(_ - _)(it => it.sub(it))
    assertOperator(_ * _)(it => it.mul(it))
    assertOperator(_ / _)(it => it.div(it))
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
  }

  it should "convert to float bits" in {
    assertValue(_.toFloatBits)(_.toFloatBits)
  }

  it should "pack rgb565" in {
    assertValue(_.toRgb565)(JColor.rgb565(_))
    assertColor(it => Color.fromRgb565(it.toRgb565).toImmutable) { it =>
      val color = MutableColor()
      JColor.rgb565ToColor(color, JColor.rgb565(it))
      color
    }
  }

  it should "pack rgba4444" in {
    assertValue(_.toRgba4444)(JColor.rgba4444(_))
    assertColor(it => Color.fromRgba4444(it.toRgba4444).toImmutable) { it =>
      JColor.rgba4444ToColor(it, JColor.rgba4444(it))
      it
    }
  }

  it should "pack rgb888" in {
    assertValue(_.toRgb888)(JColor.rgb888(_))
    assertColor(it => Color.fromRgb888(it.toRgb888).toImmutable) { it =>
      val color = MutableColor()
      JColor.rgb888ToColor(color, JColor.rgb888(it))
      color
    }
  }

  it should "pack rgba8888" in {
    assertValue(_.toRgba8888)(JColor.rgba8888(_))
    assertColor(it => Color.fromRgba8888(it.toRgba8888).toImmutable) { it =>
      JColor.rgba8888ToColor(it, JColor.rgba8888(it))
      it
    }
  }

  it should "pack argb8888" in {
    assertValue(_.toArgb8888)(JColor.argb8888(_))
    assertColor(it => Color.fromArgb8888(it.toArgb8888).toImmutable) { it =>
      JColor.argb8888ToColor(it, JColor.argb8888(it))
      it
    }
  }

  it should "convert from abgr8888" in {
    assertColor(it => Color.fromAbgr8888(1f).toImmutable) { _ =>
      val color = MutableColor()
      JColor.abgr8888ToColor(color, 1f)
      color
    }
  }

  it should "convert to hex" in {
    assertValue(_.toHex)("#" + _.toString)
    ImmutableColor().toHex shouldBe "#" + MutableColor().toString
    assertColor(it => Color.fromHex(Refined.unsafeApply(it.toHex)).toImmutable) { it =>
      JColor.valueOf(it.toString)
    }
    ImmutableColor.fromHex("#FFFFFF").toMutable shouldBe JColor.valueOf("#FFFFFF")
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
      val color = ImmutableColor(
        Refined.unsafeApply(a),
        Refined.unsafeApply(b),
        Refined.unsafeApply(if (c < 0f) -c else c),
        Refined.unsafeApply(if (d < 0f) -d else d)
      )
      assertValue(_ => color.toHsv)(_ => mutableHsv(color.toMutable))
      assertColor { _ =>
        val Hsv(h, s, v) = color.toHsv
        Color.fromHsv(h, s, v).toImmutable
      } { _ =>
        val mutable = color.toMutable
        mutable.a = 0f
        mutable.fromHsv(mutable.toHsv(Array(0f, 0f, 0f)))
      }
    }
    assertValue(_ => ImmutableColor().toHsv)(_ => mutableHsv(MutableColor()))
    Color.fromHsv(Hsv(1f, 2f, 3f)) shouldBe Color.fromHsv(1f, 2f, 3f)
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

  it should "convert to mutable and provide same immutable instance" in {
    import scalagdx.graphics.syntax.color._
    val color = ImmutableColor()
    val immutable = color.toImmutable
    val mutable = color.toMutable
    mutable === immutable shouldBe true
    immutable shouldBe theSameInstanceAs(color)
  }
}
