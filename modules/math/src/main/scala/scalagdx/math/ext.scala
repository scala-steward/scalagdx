package scalagdx.math

import com.badlogic.gdx.math.{Vector2 => JVector2}

object ext {

  implicit class Vector2Ops(vector: JVector2) {

    /**
     * Converts a java [[JVector2]] to the scalagdx [[Vector2]]
     */
    def asScala: Vector2 = Vector2(vector.x, vector.y)
  }
}
