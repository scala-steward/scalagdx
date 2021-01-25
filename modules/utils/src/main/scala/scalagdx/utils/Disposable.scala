package scalagdx.utils

/**
 * Represents a resource which needs to be disposed of.
 */
trait Disposable[F[_]] {

  /**
   * Releases all resources of this object.
   */
  def dispose: F[Unit]
}
