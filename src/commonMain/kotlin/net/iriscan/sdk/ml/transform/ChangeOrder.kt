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
        return when (newOrder) {
            OrderType.CHW -> changeWHtoHW(tensor)
            OrderType.CWH -> changeHWtoWH(tensor)
        }
    }

    private fun changeWHtoHW(tensor: Tensor): Tensor {
        val size = tensor.data.size.toInt()
        val floatArray = FloatArray(size)
        (0 until size).forEach { floatArray[it] = tensor.data.readFloat() }
        val newArray = FloatArray(size)
        val width = tensor.shape[2]
        val height = tensor.shape[3]

        for (y in 0 until height) {
            for (x in 0 until width) {
                newArray[x * height + y] = floatArray[y * width + x]
                newArray[width * height + x * height + y] = floatArray[width * height + y * width + x]
                newArray[2 * width * height + x * height + y] = floatArray[2 * width * height + y * width + x]
            }
        }

        return TensorFactory.create(intArrayOf(tensor.shape[0], tensor.shape[1], height, width), newArray)
    }

    private fun changeHWtoWH(tensor: Tensor): Tensor {
        val size = tensor.data.size.toInt()
        val floatArray = FloatArray(size)
        (0 until size).forEach { floatArray[it] = tensor.data.readFloat() }
        val newArray = FloatArray(size)
        val height = tensor.shape[2]
        val width = tensor.shape[3]

        for (y in 0 until height) {
            for (x in 0 until width) {
                newArray[y * width + x] = floatArray[x * height + y]
                newArray[width * height + y * width + x] = floatArray[width * height + x * height + y]
                newArray[2 * width * height + y * width + x] = floatArray[2 * width * height + x * height + y]
            }
        }

        return TensorFactory.create(intArrayOf(tensor.shape[0], tensor.shape[1], width, height), newArray)
    }
}