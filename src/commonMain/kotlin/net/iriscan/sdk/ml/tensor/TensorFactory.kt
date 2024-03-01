package net.iriscan.sdk.ml.tensor

import kotlinx.io.Buffer
import kotlinx.io.writeFloat
import net.iriscan.sdk.core.image.NativeImage
import net.iriscan.sdk.core.image.internalNativeImageGetHeight
import net.iriscan.sdk.core.image.internalNativeImageGetRGBPixels
import net.iriscan.sdk.core.image.internalNativeImageGetWidth
import net.iriscan.sdk.ml.model.OrderType


/**
 * @author Anton Kurinnoy
 */
object TensorFactory {
    fun fromImage(image: NativeImage, order: OrderType): Tensor {
        val width = internalNativeImageGetWidth(image)
        val height = internalNativeImageGetHeight(image)
        val floatArray = FloatArray(3 * width * height)

        return when (order) {
            OrderType.CHW -> {
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        val rgb = internalNativeImageGetRGBPixels(image, x, y)

                        floatArray[y * width + x] = rgb[0] / 255.0f
                        floatArray[width * height + y * width + x] = rgb[1] / 255.0f
                        floatArray[2 * width * height + y * width + x] = rgb[2] / 255.0f
                    }
                }
                create(intArrayOf(1, 3, height, width), floatArray)
            }

            OrderType.CWH -> {
                for (y in 0 until width) {
                    for (x in 0 until height) {
                        val rgb = internalNativeImageGetRGBPixels(image, x, y)

                        floatArray[y * height + x] = rgb[0] / 255.0f
                        floatArray[width * height + y * height + x] = rgb[1] / 255.0f
                        floatArray[2 * width * height + y * height + x] = rgb[2] / 255.0f
                    }
                }
                create(intArrayOf(1, 3, width, height), floatArray)
            }
        }
    }

    fun create(shape: Shape, data: FloatArray): Tensor {
        val dataBuffer = Buffer()
        data.forEach { dataBuffer.writeFloat(it) }
        return Tensor(shape, dataBuffer, TensorType.FLOAT)
    }
}