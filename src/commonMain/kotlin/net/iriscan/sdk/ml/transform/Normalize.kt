package net.iriscan.sdk.ml.transform

import kotlinx.io.Buffer
import kotlinx.io.writeFloat
import net.iriscan.sdk.ml.tensor.Tensor

/**
 * @author Anton Kurinnoy
 */
interface Normalize : Transform

class Normalization(private val mean: FloatArray, private val std: FloatArray) : Normalize {
    override fun transform(tensor: Tensor): Tensor {
        val size = tensor.data.size.toInt()
        val newData = Buffer()

        for (i in 0 until size step 3) {
            val red: Float = (tensor.data[i.toLong()] - mean[0]) / std[0]
            val green: Float = (tensor.data[(i + 1).toLong()] - mean[1]) / std[1]
            val blue: Float = (tensor.data[(i + 2).toLong()] - mean[2]) / std[2]

            newData.writeFloat(red)
            newData.writeFloat(green)
            newData.writeFloat(blue)
        }

        return Tensor(tensor.shape, newData, tensor.type)
    }
}