package scalagdx.app

import cats.effect.Sync

class ApplicationAdapter[F[_]: Sync] extends ApplicationListener[F] {

  /**
   * @inheritdoc
   */
  override def create: F[Unit] = Sync[F].unit

  /**
   * @inheritdoc
   */
  override def render: F[Unit] = Sync[F].unit

  /**
   * @inheritdoc
   */
  override def resize(width: Int, height: Int): F[Unit] = Sync[F].unit

  /**
   * @inheritdoc
   */
  override def resume: F[Unit] = Sync[F].unit

  /**
   * @inheritdoc
   */
  override def pause: F[Unit] = Sync[F].unit

  /**
   * @inheritdoc
   */
  override def dispose: F[Unit] = Sync[F].unit
}
