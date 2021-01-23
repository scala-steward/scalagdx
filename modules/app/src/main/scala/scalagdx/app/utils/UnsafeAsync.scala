package scalagdx.app.utils

import cats.effect.IO

/**
 * Type class for running side effects on an IO monad.
 */
trait UnsafeAsync[F[_]] {

  /**
   * Triggers the evaluation of the source and any suspended
   * side effects, re-throwing the error if unhandled
   */
  def unsafeRunAsync(ev: F[Unit]): Unit

  protected final def handleError(cb: Either[Throwable, Unit]): Unit = cb match {
    case Left(exception) => throw exception
    case _ => ()
  }
}

object UnsafeAsync {

  def apply[F[_]](implicit ev: UnsafeAsync[F]): UnsafeAsync[F] = ev
}

/**
 * Allows [[IO]] to run from Libgdx's [[com.badlogic.gdx.ApplicationListener]].
 * Should only be provided once at the top-level.
 */
trait CatsUnsafeAsync {

  /**
   * Provides a [[UnsafeAsync]] instance for cat's [[IO]] monad.
   */
  implicit lazy val catsUnsafeAsync: UnsafeAsync[IO] = new UnsafeAsync[IO] {

    override def unsafeRunAsync(ev: IO[Unit]): Unit = ev.unsafeRunAsync(handleError)
  }
}
