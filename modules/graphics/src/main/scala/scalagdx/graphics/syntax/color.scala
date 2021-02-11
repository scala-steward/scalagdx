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
package scalagdx.graphics.syntax

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import scalagdx.graphics.Color
import scalagdx.graphics.Color.RGBA
import scalagdx.graphics.ImmutableColor
import scalagdx.graphics.MutableColor

trait ColorSyntax {

  implicit def mutableColorOps(color: MutableColor): MutableColorOps = new MutableColorOps(color)

  implicit def mutableColorOps(color: Color[MutableColor]): MutableColorOps = new MutableColorOps(color.toMutable)

  implicit class MutableColorInstance(instance: MutableColor) extends Color[MutableColor] {

    override def toString(): String = instance.toString

    override def r: Float Refined RGBA = Refined.unsafeApply(instance.r)

    override def g: Float Refined RGBA = Refined.unsafeApply(instance.g)

    override def b: Float Refined RGBA = Refined.unsafeApply(instance.b)

    override def a: Float Refined RGBA = Refined.unsafeApply(instance.a)

    override def toMutable: MutableColor = instance

    override def toImmutable: ImmutableColor = ImmutableColor(r, g, b, a)

    override def copy(
        r: Float Refined RGBA = this.r,
        g: Float Refined RGBA = this.g,
        b: Float Refined RGBA = this.b,
        a: Float Refined RGBA = this.a
    ): MutableColor = MutableColor(r, g, b, a)

    private def operator(r: Float, g: Float, b: Float, a: Float)(f: (Float, Float) => Float) = {
      instance.set(
        f(instance.r, r),
        f(instance.g, g),
        f(instance.b, b),
        f(instance.a, a)
      )
      instance.clamp
    }

    override def mul(r: Float, g: Float, b: Float, a: Float): MutableColor = operator(r, g, b, a)(_ * _)

    override def *(color: Color[_]): MutableColor = instance.cpy().mul(color.r, color.g, color.b, color.a)

    override def add(r: Float, g: Float, b: Float, a: Float): MutableColor = operator(r, g, b, a)(_ + _)

    override def +(color: Color[_]): MutableColor = instance.cpy().add(color.r, color.g, color.b, color.a)

    override def sub(r: Float, g: Float, b: Float, a: Float): MutableColor = operator(r, g, b, a)(_ - _)

    override def -(color: Color[_]): MutableColor = instance.cpy().sub(color.r, color.g, color.b, color.a)

    override def div(r: Float, g: Float, b: Float, a: Float): MutableColor = operator(r, g, b, a)(_ / _)

    override def /(color: Color[_]): MutableColor = instance.cpy().div(color.r, color.g, color.b, color.a)
  }
}

final class MutableColorOps(private val instance: MutableColor) extends AnyVal {

  /**
   * Mutates this color by adding this color with the given color.
   */
  def +=(color: MutableColor): Unit = {
    instance.add(color)
    ()
  }

  /**
   * Mutates this color by adding this color with the given color.
   */
  def +=(color: Color[_]): Unit = {
    instance.add(color.r, color.g, color.b, color.a)
    ()
  }

  /**
   * Mutates this color by subtracting this color by the given color.
   */
  def -=(color: MutableColor): Unit = {
    instance.sub(color)
    ()
  }

  /**
   * Mutates this color by subtracting this color by the given color.
   */
  def -=(color: Color[_]): Unit = {
    instance.sub(color.r, color.g, color.b, color.a)
    ()
  }

  /**
   * Mutates this color by multiplying this color with the given color.
   */
  def *=(color: MutableColor): Unit = {
    instance.mul(color)
    ()
  }

  /**
   * Mutates this color by multiplying this color with the given color.
   */
  def *=(color: Color[_]): Unit = {
    instance.mul(color.r, color.g, color.b, color.a)
    ()
  }

  private def div(color: MutableColor, r: Float, g: Float, b: Float, a: Float): Unit = {
    color.set(color.r / r, color.g / g, color.b / b, color.a / a)
    color.clamp
    ()
  }

  /**
   * Mutates this color by dividing this color by the given color.
   */
  def /=(color: MutableColor): Unit = div(instance, color.r, color.g, color.b, color.a)

  /**
   * Mutates this color by dividing this color by the given color.
   */
  def /=(color: Color[_]): Unit = div(instance, color.r, color.g, color.b, color.a)

  /**
   * Adds this color with the given color, returning a new instance containing the result.
   */
  def +(color: MutableColor): MutableColor = instance.cpy().add(color)

  /**
   * Subtracts this color by the given color, returning a new instance containing the result.
   */
  def -(color: MutableColor): MutableColor = instance.cpy().sub(color)

  /**
   * Multiplies this color with the given color, returning a new instance containing the result.
   */
  def *(color: MutableColor): MutableColor = instance.cpy().mul(color)

  /**
   * Divides this color by the given color, returning a new instance containing the result.
   */
  def /(color: MutableColor): MutableColor = {
    val result = instance.cpy()
    div(result, color.r, color.g, color.b, color.a)
    result
  }
}
