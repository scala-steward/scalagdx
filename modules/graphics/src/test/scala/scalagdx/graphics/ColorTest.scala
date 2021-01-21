package scalagdx.graphics

import com.badlogic.gdx.graphics.{Color => JColor}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalagdx.graphics.ext.ColorOps

class ColorTest extends AnyFlatSpec with Matchers {

  it should "convert to and from java" in {
    val color = Color(0.1f, 0.2f, 0.3f, 0.4f)
    val jcolor = color.asJava
    color.r.value shouldBe jcolor.r
    color.g.value shouldBe jcolor.g
    color.b.value shouldBe jcolor.b
    color.a.value shouldBe jcolor.a

    val jcolor2 = new JColor(4f, 1.1f, 0.1f, -1f)
    val color2 = jcolor2.asScala
    color2.r.value shouldBe jcolor2.r
    color2.g.value shouldBe jcolor2.g
    color2.b.value shouldBe jcolor2.b
    color2.a.value shouldBe jcolor2.a
    color2.r.value shouldBe 1f
    color2.g.value shouldBe 1f
    color2.b.value shouldBe 0.1f
    color2.a.value shouldBe 0f
  }

  it should "have the same default colors" in {
    Color.White.asJava shouldBe JColor.WHITE
    Color.LightGray.asJava shouldBe JColor.LIGHT_GRAY
    Color.Gray.asJava shouldBe JColor.GRAY
    Color.DarkGray.asJava shouldBe JColor.DARK_GRAY
    Color.Black.asJava shouldBe JColor.BLACK
    Color.Clear.asJava shouldBe JColor.CLEAR
    Color.Blue.asJava shouldBe JColor.BLUE
    Color.Navy.asJava shouldBe JColor.NAVY
    Color.Royal.asJava shouldBe JColor.ROYAL
    Color.Slate.asJava shouldBe JColor.SLATE
    Color.Sky.asJava shouldBe JColor.SKY
    Color.Cyan.asJava shouldBe JColor.CYAN
    Color.Teal.asJava shouldBe JColor.TEAL
    Color.Green.asJava shouldBe JColor.GREEN
    Color.Chartreuse.asJava shouldBe JColor.CHARTREUSE
    Color.Lime.asJava shouldBe JColor.LIME
    Color.Forest.asJava shouldBe JColor.FOREST
    Color.Olive.asJava shouldBe JColor.OLIVE
    Color.Yellow.asJava shouldBe JColor.YELLOW
    Color.Gold.asJava shouldBe JColor.GOLD
    Color.Goldenrod.asJava shouldBe JColor.GOLDENROD
    Color.Orange.asJava shouldBe JColor.ORANGE
    Color.Brown.asJava shouldBe JColor.BROWN
    Color.Tan.asJava shouldBe JColor.TAN
    Color.Firebrick.asJava shouldBe JColor.FIREBRICK
    Color.Red.asJava shouldBe JColor.RED
    Color.Scarlet.asJava shouldBe JColor.SCARLET
    Color.Coral.asJava shouldBe JColor.CORAL
    Color.Salmon.asJava shouldBe JColor.SALMON
    Color.Pink.asJava shouldBe JColor.PINK
    Color.Magenta.asJava shouldBe JColor.MAGENTA
    Color.Purple.asJava shouldBe JColor.PURPLE
    Color.Violet.asJava shouldBe JColor.VIOLET
    Color.Maroon.asJava shouldBe JColor.MAROON
  }

  it should "add" in {
    Color(1f, 1f, 1f, 1f) + Color(1f, 1f, 1f, 1f) shouldBe Color(1f, 1f, 1f, 1f)
    Color(.5f, .5f, .5f, .5f) + Color(.2f, .2f, .2f, .2f) shouldBe Color(.7f, .7f, .7f, .7f)
    Color(.1f, .1f, .1f, .1f) + Color(.1f, .1f, .1f, .1f) + Color(.1f, .1f, .1f, .1f) shouldBe Color(.3f, .3f, .3f, .3f)
    Color(1f, 1f, 1f, 1f).add(Color(1f, 1f, 1f, 1f)) shouldBe a[Left[_, Color]]
    Color(.2f, .2f, .2f, .2f).add(Color(.2f, .2f, .2f, .2f)) shouldBe a[Right[_, Color]]
    Color(.1f, .2f, .3f, .4f).add(.2f) shouldBe Right(Color(.3f, .4f, .5f, .6f))
    Color(.1f, .2f, .3f, .4f).addClamped(2f) shouldBe Color(1f, 1f, 1f, 1f)
  }

  it should "subtract" in {
    Color(0f, 0f, 0f, 0f) - Color(1f, 1f, 1f, 1f) shouldBe Color(0f, 0f, 0f, 0f)
    Color(.5f, .5f, .5f, .5f) - Color(.2f, .2f, .2f, .2f) shouldBe Color(.3f, .3f, .3f, .3f)
    Color(.5f, .5f, .5f, .5f) - Color(.1f, .1f, .1f, .1f) - Color(.1f, .1f, .1f, .1f) shouldBe Color(.3f, .3f, .3f, .3f)
    Color(0f, 0f, 0f, 0f).subtract(Color(1f, 1f, 1f, 1f)) shouldBe a[Left[_, Color]]
    Color(.2f, .2f, .2f, .2f).subtract(Color(.2f, .2f, .2f, .2f)) shouldBe a[Right[_, Color]]
    Color(1f, .9f, .8f, .7f).subtract(.2f) shouldBe Right(Color(.8f, .7f, .6f, .5f))
    Color().subtractClamped(2f) shouldBe Color()
  }

  it should "multiply" in {
    Color(1f, 1f, 1f, 1f) * Color(.5f, .5f, .5f, .5f) shouldBe Color(.5f, .5f, .5f, .5f)
    Color(1f, 1f, 1f, 1f) * Color(.5f, .5f, .5f, .5f) * Color(.5f, .5f, .5f, .5f) shouldBe Color(.25f, .25f, .25f, .25f)
    Color(1f, 1f, 1f, 1f).multiply(Color(0f, 0f, 0f, 0f)) shouldBe a[Right[_, Color]]
    Color(1f, 1f, 1f, 1f).multiplyClamped(2f) shouldBe Color(1f, 1f, 1f, 1f)
    Color(.1f, .2f, .3f, .4f).multiplyClamped(2f, 1f, 2f, 1f) shouldBe Color(.2f, .2f, .6f, .4f)
    Color(.4f, .4f, .4f, .4f).multiply(2f) shouldBe Right(Color(.8f, .8f, .8f, .8f))
  }

  it should "linearly interpolate" in {
    Color(.2f, .2f, .2f, .2f).lerp(.3f, .4f) shouldBe Right(
      new JColor(.2f, .2f, .2f, .2f).lerp(.3f, .3f, .3f, .3f, .4f).asScala
    )
    Color(0f, 0f, 0f, 0f).lerp(Color(1f, 1f, 1f, 1f), 2f) shouldBe a[Left[_, Color]]
    Color(.2f, .2f, .2f, .2f)
      .lerpClamped(.3f, .4f) shouldBe new JColor(.2f, .2f, .2f, .2f).lerp(.3f, .3f, .3f, .3f, .4f).asScala
    Color(1f, 1f, 1f, 1f).lerpClamped(Color(1f, 1f, 1f, 1f), 2f) shouldBe Color(1f, 1f, 1f, 1f)
  }

  it should "premultiply alpha" in {
    Color(1f, 1f, 1f, .5f).premultiplyAlpha shouldBe Color(.5f, .5f, .5f, .5f)
    Color(0f, 0f, 0f, .5f).premultiplyAlpha shouldBe Color(0f, 0f, 0f, .5f)
    Color(.2f, .2f, .2f, 1f).premultiplyAlpha shouldBe Color(.2f, .2f, .2f, 1f)
  }

  it should "convert to float bits" in {
    Color(1f, 1f, 1f, 1f).toFloatBits shouldBe new JColor(1f, 1f, 1f, 1f).toFloatBits
    Color(.1f, .2f, .3f, .4f).toFloatBits shouldBe new JColor(.1f, .2f, .3f, .4f).toFloatBits
    Color(1f, 1f, 1f, 1f).toFloatBits shouldBe JColor.toFloatBits(255, 255, 255, 255)
    Color.toFloatBits(50, 100, 150, 200) shouldBe JColor.toFloatBits(50, 100, 150, 200)
  }

  it should "convert to int bits" in {
    Color(1f, 1f, 1f, 1f).toIntBits shouldBe new JColor(1f, 1f, 1f, 1f).toIntBits
    Color(.1f, .2f, .3f, .4f).toIntBits shouldBe new JColor(.1f, .2f, .3f, .4f).toIntBits
    Color(1f, 1f, 1f, 1f).toIntBits shouldBe JColor.toIntBits(255, 255, 255, 255)
    Color.toIntBits(50, 100, 150, 200) shouldBe JColor.toIntBits(50, 100, 150, 200)
  }

  it should "have equivalent methods as the java version" in {
    Color.alpha(2f) shouldBe JColor.alpha(2f)
    Color.luminanceAlpha(2f, 3f) shouldBe JColor.luminanceAlpha(2f, 3f)

    Color.rgb565(1f, 2f, 3f) shouldBe JColor.rgb565(1f, 2f, 3f)
    Color(.1f, .2f, .3f, .4f).rgb565 shouldBe JColor.rgb565(new JColor(.1f, .2f, .3f, .4f))

    Color.rgba4444(1f, 2f, 3f, 4f) shouldBe JColor.rgba4444(1f, 2f, 3f, 4f)
    Color(.1f, .2f, .3f, .4f).rgba4444 shouldBe JColor.rgba4444(new JColor(.1f, .2f, .3f, .4f))

    Color.rgb888(1f, 2f, 3f) shouldBe JColor.rgb888(1f, 2f, 3f)
    Color(.1f, .2f, .3f, .4f).rgb888 shouldBe JColor.rgb888(new JColor(.1f, .2f, .3f, .4f))

    Color.rgba8888(1f, 2f, 3f, 4f) shouldBe JColor.rgba8888(1f, 2f, 3f, 4f)
    Color(.1f, .2f, .3f, .4f).rgba8888 shouldBe JColor.rgba8888(new JColor(.1f, .2f, .3f, .4f))

    Color.argb8888(1f, 2f, 3f, 4f) shouldBe JColor.argb8888(1f, 2f, 3f, 4f)
    Color(.1f, .2f, .3f, .4f).argb8888 shouldBe JColor.argb8888(new JColor(.1f, .2f, .3f, .4f))

    val rgb565 = new JColor()
    JColor.rgb565ToColor(rgb565, 100)
    Color.fromRgb565(100) shouldBe rgb565.asScala

    val rgba4444 = new JColor()
    JColor.rgba4444ToColor(rgba4444, 100)
    Color.fromRgba4444(100) shouldBe rgba4444.asScala

    val rgb888 = new JColor()
    JColor.rgb888ToColor(rgb888, 100)
    Color.fromRgb888(100) shouldBe rgb888.asScala

    val rgba8888 = new JColor()
    JColor.rgba8888ToColor(rgba8888, 100)
    Color.fromRgba8888(100) shouldBe rgba8888.asScala

    val argb8888 = new JColor()
    JColor.argb8888ToColor(argb8888, 100)
    Color.fromArgb8888(100) shouldBe argb8888.asScala

    Color.fromHsv(200, 0f, 1f) shouldBe new JColor().fromHsv(200, 0f, 1f).asScala

    Color.fromHex("#FFFFFFFF") shouldBe JColor.valueOf("#FFFFFFFF").asScala
    Color.fromHex("#000000") shouldBe JColor.valueOf("#000000").asScala
    Color(1f, 1f, 1f, 1f).toHex shouldBe "#" + new JColor(1f, 1f, 1f, 1f).toString
    Color.fromHex(Refined.unsafeApply(Color.White.toHex)) shouldBe Color.White
  }
}
