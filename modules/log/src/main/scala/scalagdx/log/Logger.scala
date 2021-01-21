package scalagdx.log

import cats.effect.Sync
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx

/**
 * Simple logger, requires an [[LoggerTag]] implicit to log class names
 */
trait Logger[F[_]] {

  /**
   * Logs a message on the [[Application.LOG_DEBUG]] level.
   *
   * @param message The message to be logged.
   */
  def debug(message: => String)(implicit tag: LoggerTag): F[Unit]

  /**
   * Logs a message on the [[Application.LOG_DEBUG]] level.
   *
   * @param throwable Prints the stack trace of this throwable.
   * @param message The message to be logged.
   */
  def debug(throwable: Throwable)(message: => String)(implicit tag: LoggerTag): F[Unit]

  /**
   * Logs a message on the [[Application.LOG_INFO]] level.
   *
   * @param message The message to be logged.
   */
  def info(message: => String)(implicit tag: LoggerTag): F[Unit]

  /**
   * Logs a message on the [[Application.LOG_INFO]] level.
   *
   * @param throwable Prints the stack trace of this throwable.
   * @param message The message to be logged.
   */
  def info(throwable: Throwable)(message: => String)(implicit tag: LoggerTag): F[Unit]

  /**
   * Logs a message on the [[Application.LOG_ERROR]] level.
   *
   * @param message The message to be logged.
   */
  def error(message: => String)(implicit tag: LoggerTag): F[Unit]

  /**
   * Logs a message on the [[Application.LOG_ERROR]] level.
   *
   * @param throwable Prints the stack trace of this throwable.
   * @param message The message to be logged
   */
  def error(throwable: Throwable)(message: => String)(implicit tag: LoggerTag): F[Unit]
}

object Logger {

  def apply[F[_]](implicit ev: Logger[F]): Logger[F] = ev

  /**
   * Provides a default logger, available after the [[Application]]'s creation.
   */
  def getLogger[F[_]: Sync](
      infoPrefix: InfoPrefix = InfoPrefix("[INFO]"),
      debugPrefix: DebugPrefix = DebugPrefix("[DEBUG]"),
      errorPrefix: ErrorPrefix = ErrorPrefix("[ERROR]")
  ): Logger[F] = new Logger[F] {

    override def debug(message: => String)(implicit tag: LoggerTag): F[Unit] =
      if (Gdx.app.getLogLevel >= Application.LOG_DEBUG)
        Sync[F].delay(Gdx.app.debug(s"${debugPrefix.value} ${tag.value}", message))
      else
        Sync[F].unit

    override def debug(throwable: Throwable)(message: => String)(implicit tag: LoggerTag): F[Unit] =
      if (Gdx.app.getLogLevel >= Application.LOG_DEBUG)
        Sync[F].delay(Gdx.app.debug(s"${debugPrefix.value} ${tag.value}", message, throwable))
      else
        Sync[F].unit

    override def info(message: => String)(implicit tag: LoggerTag): F[Unit] =
      if (Gdx.app.getLogLevel >= Application.LOG_INFO)
        Sync[F].delay(Gdx.app.log(s"${infoPrefix.value} ${tag.value}", message))
      else
        Sync[F].unit

    override def info(throwable: Throwable)(message: => String)(implicit tag: LoggerTag): F[Unit] =
      if (Gdx.app.getLogLevel >= Application.LOG_INFO)
        Sync[F].delay(Gdx.app.log(s"${infoPrefix.value} ${tag.value}", message, throwable))
      else
        Sync[F].unit

    override def error(message: => String)(implicit tag: LoggerTag): F[Unit] =
      if (Gdx.app.getLogLevel >= Application.LOG_ERROR)
        Sync[F].delay(Gdx.app.error(s"${errorPrefix.value} ${tag.value}", message))
      else
        Sync[F].unit

    override def error(throwable: Throwable)(message: => String)(implicit tag: LoggerTag): F[Unit] =
      if (Gdx.app.getLogLevel >= Application.LOG_ERROR)
        Sync[F].delay(Gdx.app.error(s"${errorPrefix.value} ${tag.value}", message, throwable))
      else
        Sync[F].unit
  }
}
