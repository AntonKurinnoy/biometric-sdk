package net.iriscan.sdk.core.image

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import net.iriscan.sdk.core.io.DataBytes
import net.iriscan.sdk.io.image.ImageFormat
import java.io.ByteArrayOutputStream

/**
 * @author Slava Gornostal
 */
actual typealias NativeImage = Bitmap

internal actual fun internalReadNativeImage(dataBytes: DataBytes): NativeImage =
    BitmapFactory.decodeByteArray(dataBytes, 0, dataBytes.size)

internal actual fun internalWriteNativeImage(
    image: NativeImage,
    format: ImageFormat
): DataBytes {
    val outs = ByteArrayOutputStream()
    val compressFormat = when (format) {
        ImageFormat.BMP -> throw IllegalArgumentException("BMP is not supported")
        ImageFormat.PNG -> CompressFormat.PNG
        ImageFormat.JPEG -> CompressFormat.JPEG
    }
    image.compress(compressFormat, 90, outs)
    return outs.toByteArray()
}

internal actual fun internalResizeNativeImage(image: NativeImage, newWidth: Int, newHeight: Int): NativeImage =
    Bitmap.createScaledBitmap(image, newWidth, newHeight, true)

internal actual fun internalNativeImageGetRGBPixels(image: NativeImage, x: Int, y: Int) : Color {
    val color = image.getPixel(x, y)
    return createColor(color.red(), color.green(), color.blue())
}

internal actual fun internalNativeImageGetWidth(image: NativeImage): Int = image.width

internal actual fun internalNativeImageGetHeight(image: NativeImage): Int = image.height