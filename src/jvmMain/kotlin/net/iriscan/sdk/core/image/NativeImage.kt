package net.iriscan.sdk.core.image

import net.iriscan.sdk.core.io.DataBytes
import net.iriscan.sdk.io.image.ImageFormat
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/**
 * @author Slava Gornostal
 */
actual typealias NativeImage = BufferedImage

internal actual fun internalReadNativeImage(dataBytes: DataBytes): NativeImage =
    ImageIO.read(ByteArrayInputStream(dataBytes))

internal actual fun internalWriteNativeImage(
    image: NativeImage,
    format: ImageFormat
): DataBytes {
    val writeFormat = when (format) {
        ImageFormat.BMP -> "bmp"
        ImageFormat.PNG -> "png"
        ImageFormat.JPEG -> "jpg"
    }
    val outs = ByteArrayOutputStream()
    ImageIO.write(image, writeFormat, outs)
    return outs.toByteArray()
}

internal actual fun internalResizeNativeImage(image: NativeImage, newWidth: Int, newHeight: Int): NativeImage {
    val resizedImage = BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB)
    var graphics2D = resizedImage.createGraphics()
    graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null)
    graphics2D.dispose()
    val tmp = image.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_AREA_AVERAGING)
    graphics2D = resizedImage.createGraphics()
    graphics2D.drawImage(tmp, 0, 0, null)
    graphics2D.dispose()
    return resizedImage
}

internal actual fun internalNativeImageGetRGBPixels(image: NativeImage, x: Int, y: Int): Color {
    val color = image.getRGB(x, y)
    return createColor(color.red(), color.green(), color.blue())
}

internal actual fun internalNativeImageGetWidth(image: NativeImage): Int = image.width

internal actual fun internalNativeImageGetHeight(image: NativeImage): Int = image.height