package scalagdx.math

import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.W

object Vector2 {

  type XY = MatchesRegex[W.`"""\\(\\s*[+-]?(\\d*\\.)?\\d+\\s*,\\s*[+-]?(\\d*\\.)?\\d+\\s*\\)"""`.T]
}