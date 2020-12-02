package scalagdx.app

trait ApplicationListener[F[_]] {

  def create: F[Unit]
  def dispose: F[Unit]
  def resize(width: Int, height: Int): F[Unit]
  def pause: F[Unit]
  def resume: F[Unit]
  def render: F[Unit]
}
