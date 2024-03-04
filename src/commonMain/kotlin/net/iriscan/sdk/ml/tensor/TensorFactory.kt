package net.iriscan.sdk.ml.tensor

import kotlinx.io.Buffer
import kotlinx.io.writeFloat
import net.iriscan.sdk.core.image.*
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

        for (y in 0 until height) {
            for (x in 0 until width) {
                val rgb = internalNativeImageGetRGBPixels(image, x, y)
                when (order) {
                    OrderType.CHW -> {
                        floatArray[y * width + x] = rgb.red() / 255.0f
                        floatArray[width * height + y * width + x] = rgb.green() / 255.0f
                        floatArray[2 * width * height + y * width + x] = rgb.blue() / 255.0f
                    }

                    OrderType.CWH -> {
                        floatArray[x * height + y] = rgb.red() / 255.0f
                        floatArray[width * height + x * height + y] = rgb.green() / 255.0f
                        floatArray[2 * width * height + x * height + y] = rgb.blue() / 255.0f
                    }
                }
            }
        }

        return when (order) {
            OrderType.CHW -> create(intArrayOf(1, 3, height, width), floatArray)
            OrderType.CWH -> create(intArrayOf(1, 3, width, height), floatArray)
        }
    }

    fun create(shape: TensorShape, data: FloatArray): Tensor {
        val dataBuffer = Buffer()
        data.forEach { dataBuffer.writeFloat(it) }
        return Tensor(shape, dataBuffer, TensorType.FLOAT)
    }
}