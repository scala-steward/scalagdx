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

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._

object MutableVector3 {

  import scalagdx.math.Vector3.XYZ

  /**
   * Constructs a new [[MutableVector3]] using the given (x, y) values.
   */
  def apply(x: Float = 0f, y: Float = 0f, z: Float = 0f): MutableVector3 = new MutableVector3(x, y, z)

  /**
   * Destructure the [[MutableVector3]] for pattern-matching.
   */
  def unapply(vector: MutableVector3): Option[(Float, Float, Float)] = Some((vector.x, vector.y, vector.z))

  /**
   * Creates a new [[MutableVector3]] from a string with the format (x,y).
   */
  def fromString(value: String Refined XYZ): MutableVector3 = MutableVector3().fromString(value)
}
