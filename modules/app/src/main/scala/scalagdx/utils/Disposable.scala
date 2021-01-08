package scalagdx.utils

trait Disposable[F[_]] {

  def dispose: F[Unit]
}
