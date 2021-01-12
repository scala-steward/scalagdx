package scalagdx.app

import com.badlogic.gdx.Application
import cats.effect.Sync

class ApplicationAdapter[F[_]: Sync] extends ApplicationListener[F] {

  /**
   * @inheritdoc
   */
  override def create: F[Unit] = Sync[F].delay(())

  /**
   * @inheritdoc
   */
  override def render: F[Unit] = Sync[F].delay(())

  /**
   * @inheritdoc
   */
  override def resize(width: Int, height: Int): F[Unit] = Sync[F].delay(())

  /**
   * @inheritdoc
   */
  override def resume: F[Unit] = Sync[F].delay(())

  /**
   * @inheritdoc
   */
  override def pause: F[Unit] = Sync[F].delay(())

  /**
   * @inheritdoc
   */
  override def dispose: F[Unit] = Sync[F].delay(())
}
