package net.iriscan.sdk.ml.transform

import kotlinx.io.readFloat
import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.tensor.Tensor
import net.iriscan.sdk.ml.tensor.TensorFactory

/**
 * @author Anton Kurinnoy
 */
interface Order : Transform

class ChangeOrder(private val newOrder: OrderType) : Order {
    override fun transform(tensor: Tensor): Tensor {
        val size = tensor.data.size.toInt()
        val floatArray = FloatArray(size)
        (0 until size).forEach { floatArray[it] = tensor.data.readFloat() }
        val newArray = FloatArray(size)
        val (width, height) = when (newOrder) {
            OrderType.CHW -> tensor.shape[2] to tensor.shape[3]
            OrderType.CWH -> tensor.shape[3] to tensor.shape[2]
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val indexHWRed = x * height + y
                val indexWHRed = y * width + x
                val indexHWGreen = width * height + x * height + y
                val indexWHGreen = width * height + y * width + x
                val indexHWBlue = 2 * width * height + x * height + y
                val indexWHBlue = 2 * width * height + y * width + x
                when (newOrder) {
                    OrderType.CHW -> {
                        newArray[indexWHRed] = floatArray[indexHWRed]
                        newArray[indexHWGreen] = floatArray[indexWHGreen]
                        newArray[indexHWBlue] = floatArray[indexWHBlue]
                    }

                    OrderType.CWH -> {
                        newArray[indexHWRed] = floatArray[indexWHRed]
                        newArray[indexWHGreen] = floatArray[indexHWGreen]
                        newArray[indexWHBlue] = floatArray[indexHWBlue]
                    }
                }
            }
        }

        return when (newOrder) {
            OrderType.CHW -> TensorFactory.create(intArrayOf(tensor.shape[0], tensor.shape[1], height, width), newArray)
            OrderType.CWH -> TensorFactory.create(intArrayOf(tensor.shape[0], tensor.shape[1], width, height), newArray)
        }
    }
}