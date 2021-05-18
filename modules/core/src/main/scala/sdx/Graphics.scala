/**
 * Copyright (c) 2021 scalagdx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This product includes software developed at libGDX (https://libgdx.com/).
 */
package sdx

import com.badlogic.gdx.Graphics.BufferFormat
import com.badlogic.gdx.Graphics.DisplayMode
import com.badlogic.gdx.Graphics.GraphicsType
import com.badlogic.gdx.Graphics.Monitor
import com.badlogic.gdx.graphics.Cursor
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.glutils.GLVersion
import fs2.Stream
import sdx.graphics.GL20
import sdx.graphics.GL30

/**
 * Encapsualtes communication with the graphics processor.
 */
trait Graphics[F[_]] {

  /**
   * Checks if GL30 is available.
   *
   * @return True if OpenGL ES 3.0 is available, false otherwise.
   */
  def isGL30Available: F[Boolean]

  /**
   * The [[sdx.graphics.GL20]] instance.
   */
  def gl20: GL20[F]

  /**
   * The [[sdx.graphics.GL30]] instance, if it exists.
   */
  def gl30: Option[GL30[F]]

  /**
   * Sets the [[sdx.graphics.GL20]] instance.
   *
   * @param gl20 The gl20 instance to set to.
   */
  def setGL20(gl20: GL20[F]): F[Unit]

  /**
   * Sets the [[sdx.graphics.GL30]] instance.
   *
   * @param gl30 The gl30 instance to set to.
   */
  def setGL30(gl30: GL30[F]): F[Unit]

  /**
   * The width of the client area in logical pixels.
   */
  def width: F[Int]

  /**
   * The height of the client area in logical pixels.
   */
  def height: F[Int]

  /**
   * The width of the framebuffer in physical pixels.
   */
  def backBufferWidth: F[Int]

  /**
   * The height of the framebuffer in physical pixels.
   */
  def backBufferHeight: F[Int]

  /**
   * The amount of pixels per logical pixel (point).
   */
  def backBufferScale: F[Float]

  /**
   * The inset from the left which avoids cutouts in logical pixels.
   */
  def safeInsetLeft: F[Int]

  /**
   * The inset from the top which avoids cutouts in logical pixels.
   */
  def safeInsetTop: F[Int]

  /**
   * The inset from the bottom which avoids cutouts in logical pixels.
   */
  def safeInsetBottom: F[Int]

  /**
   * The inset from the right which avoids cutouts in logical pixels.
   */
  def safeInsetRight: F[Int]

  /**
   * The id of the current frame. The general contract of this method is that the id is incremented
   * only when the application is in the running state right before calling the [[ApplicationListener.render]] method.
   *
   * The first frame is 0; the id of subsequent frame is guaranteed to to take increasing values for
   * 2<sup>63</sup>-1 rendering cycles.
   *
   * @return The id of the current frame.
   */
  def frameId: F[Long]

  /**
   * The time span between the current and previous frame in seconds.
   */
  def deltaTime: F[Float]

  /**
   * The average number of frames per second.
   */
  def framesPerSecond: F[Int]

  /**
   * The graphics type of this instance.
   */
  def graphicsType: GraphicsType

  /**
   * The gl version of this instance.
   */
  def glVersion: GLVersion

  /**
   * The pixels per inch on the x-axis.
   */
  def ppix: F[Float]

  /**
   * The pixels per inch on the y-axis.
   */
  def ppiy: F[Float]

  /**
   * The pixels per centimeter on the x-axis.
   */
  def ppcx: F[Float]

  /**
   * The pixels per centimeter on the y-axis.
   *
   * @return
   */
  def ppyx: F[Float]

  /**
   * This is a scaling factor for the Density Independent Pixel unit, following the same conventions as
   * android.util.DisplayMetrics#density, where one DIP is one pixel on an approximately 160 dpi screen.<br>
   * Thus on a 160dpi screen this density value will be 1; on a 120 dpi screen it would be .75; etc.
   *
   * @return The density independent pixel factor of the display.
   */
  def density: F[Float]

  /**
   * True if display mode changes are supported, false otherwise.
   */
  def supportsDisplayModeChange: F[Boolean]

  /**
   * The primary monitor.
   */
  def primaryMonitor: F[Monitor]

  /**
   * The monitor the application's window is currently on.
   */
  def monitor: F[Monitor]

  /**
   * The currently connected monitors.
   */
  def monitors: Stream[F, Monitor]

  /**
   * The supported fullscreen displaymodes of the current monitor.
   *
   * @return A stream of the supported displaymodes.
   */
  def displayModes: Stream[F, DisplayMode]

  /**
   * The supported fullscreen displaymodes of the given monitor.
   *
   * @param monitor The monitor to check for displaymodes.
   * @return A stream of the supported displaymodes.
   */
  def displayModes(monitor: Monitor): Stream[F, DisplayMode]

  /**
   * Sets the window to full-screen mode.
   *
   * @param displayMode The display mode.
   * @return True if the window was set successfully, false otherwise.
   */
  def setFullscreenMode(displayMode: DisplayMode): F[Boolean]

  /**
   * Sets the window to windowed mode.
   *
   * @param width The width in pixels.
   * @param height The height in pixels.
   * @return True if the window was set successfully, false otherwise.
   */
  def setWindowedMode(width: Int, height: Int): F[Boolean]

  /**
   * Sets the window's title. Ignored on android.
   *
   * @param title The new title.
   */
  def setTitle(title: String): F[Unit]

  /**
   * Sets the window decoration as enabled or disabled. On android, this will enable/disable the menu bar.
   *
   * @param undecorated True if the window border should be hidden, false otherwise.
   */
  def setUndecorated(undecorated: Boolean): F[Unit]

  /**
   * Sets whether or not the window should be resizable. Ignored on android.
   *
   * @param resizable True if the window should be resizable, false otherwise.
   */
  def setResizable(resizable: Boolean): F[Unit]

  /**
   * Sets wheter or not vsync should be enabled. This might not work on all platforms.
   *
   * @param vsync True if vsync should be enabled, false otherwise.
   */
  def setVSync(vsync: Boolean): F[Unit]

  /**
   * Sets the target framerate for the application when using continuous rendering.<br>
   * This might not work on all platforms. Generally not advised to be used on mobile platforms.
   *
   * @param fps The target framerate
   */
  def setForegroundFPS(fps: Int): F[Unit]

  /**
   * The format of the color, depth, and stencil buffer in a buffer format instance.
   */
  def bufferFormat: BufferFormat

  /**
   * Checks if an extension is supported.
   *
   * @param extension The extension to check.
   * @return True if the extension is supported, false otherwise.
   */
  def supportsExtension(extension: String): F[Boolean]

  /**
   * Sets whether to render continuously.
   *
   * @param isContinuous True if rendering should be continuous, false otherwise.
   */
  def setContinuousRendering(isContinuous: Boolean): F[Unit]

  /**
   * True if rendering is continuous, false otherwise.
   */
  def isContinuousRendering: F[Boolean]

  /**
   * Requests a new frame to be rendered if the rendering mode is non-continuous.
   */
  def requestRendering: F[Unit]

  /**
   * True if the application window is in full-screen mode, false otherwise.
   */
  def isFullscreen: F[Boolean]

  /**
   * Creates a new cursor represented by the pixmap.
   *
   * @param pixmap The pixmap in a RGBA8888 format
   * @param xHotspot The x location of the hotspot pixel within the cursor image (origin top-left corner)
   * @param yHotspot The y location of the hotspot pixel within the cursor image (origin top-left corner)
   * @return A cursor object that can be used by calling [[setCursor]], or None if not supported.
   */
  def newCursor(pixmap: Pixmap, xHotspot: Int, yHotspot: Int): F[Option[Cursor]]

  /**
   * Only available on the lwjgl-backend and on the gwt-backend.
   *
   * Browsers that support cursor:url() and support the png format (the pixmap is converted to a data-url
   * of type image/png) should also support custom cursors. Will set the mouse cursor image to
   * the image represented by the cursor.
   *
   * @param cursor The cursor to set to.
   */
  def setCursor(cursor: Cursor): F[Unit]

  /**
   * Sets one of the predefined system cursors.
   *
   * @param systemCursor The system cursor to set to.
   */
  def setSystemCursor(systemCursor: Cursor.SystemCursor): F[Unit]
}
