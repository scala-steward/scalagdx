package scalagdx.graphics

import com.badlogic.gdx.graphics.{Color => JColor}
import eu.timepit.refined.api.Refined

object ext {

  implicit class ColorOps(color: JColor) {

    /**
     * Converts a java [[JColor]] to the scalagdx [[Color]]
     */
    def asScala: Color = Color(
      Refined.unsafeApply(color.r),
      Refined.unsafeApply(color.g),
      Refined.unsafeApply(color.b),
      Refined.unsafeApply(color.a),
    )
  }
}
