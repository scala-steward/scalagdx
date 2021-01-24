package scalagdx.math

import com.badlogic.gdx.math.{Vector2 => JVector2, Vector3 => JVector3}

object ext {

  implicit class Vector2Ops(vector: JVector2) {

    /**
     * Converts a java [[JVector2]] to the scalagdx [[Vector2]]
     */
    def asScala: Vector2 = Vector2(vector.x, vector.y)
  }

  implicit class Vector3Ops(vector: JVector3) {

    /**
     * Converts a java [[JVector3]] to the scalagdx [[Vector3]]
     */
    def asScala: Vector3 = Vector3(vector.x, vector.y, vector.z)
  }
}
