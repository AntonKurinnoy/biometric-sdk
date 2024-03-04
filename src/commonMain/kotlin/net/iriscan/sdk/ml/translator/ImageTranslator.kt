package net.iriscan.sdk.ml.translator

import net.iriscan.sdk.core.image.NativeImage
import net.iriscan.sdk.core.image.internalResizeNativeImage
import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.tensor.Tensor
import net.iriscan.sdk.ml.tensor.TensorFactory
import net.iriscan.sdk.ml.transform.ChangeOrder
import net.iriscan.sdk.ml.transform.Normalization
import net.iriscan.sdk.ml.transform.Transform

/**
 * @author Anton Kurinnoy
 */
abstract class ImageTranslator<O>(
    inputWidth: Int,
    inputHeight: Int,
    inputOrder: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    customTransformsList: List<Transform>
) : Translator<NativeImage, O> {

    private val pipeline: Pipeline = mutableListOf()
    private val newWidth: Int
    private val newHeight: Int
    private var order = OrderType.CHW

    init {
        this.newWidth = inputWidth
        this.newHeight = inputHeight
        if (this.order != inputOrder) {
            addTransform(ChangeOrder(inputOrder))
        }
        for (i in meanList.indices) {
            addTransform(Normalization(meanList[i], stdList[i]))
        }
        customTransformsList.forEach { addTransform(it) }
    }

    private fun addTransform(item: Transform) {
        pipeline.add(item)
    }

    override fun preProcessInput(input: NativeImage): Map<Int, Tensor> {
        val resizedImage = internalResizeNativeImage(input, newWidth, newHeight)
        val inputTensor = TensorFactory.fromImage(resizedImage, this.order)

        val transformedTensor = pipeline.fold(inputTensor) { acc, transform ->
            transform.transform(acc)
        }

        return mapOf(0 to transformedTensor)
    }
}
