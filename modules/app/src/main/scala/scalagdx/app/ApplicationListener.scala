package scalagdx.app

import scalagdx.utils.Disposable

trait ApplicationListener[F[_]] extends Disposable[F] {

  def create: F[Unit]

  def render: F[Unit]

  def resize(width: Int, height: Int): F[Unit]

  def pause: F[Unit]

  def resume: F[Unit]
}
