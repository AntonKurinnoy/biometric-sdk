package net.iriscan.sdk.ml.translator.deepixbis

import kotlinx.io.readFloat
import net.iriscan.sdk.core.image.NativeImage
import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.tensor.Tensor
import net.iriscan.sdk.ml.transform.Transform
import net.iriscan.sdk.ml.translator.ImageTranslator
import net.iriscan.sdk.ml.translator.ImageTranslatorBuilder

/**
 * @author Anton Kurinnoy
 */
abstract class ImageBiometricLivenessTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transforms: List<Transform>
) : ImageTranslator<Boolean>(inputWidth, inputHeight, order, meanList, stdList, transforms)

abstract class ImageBiometricLivenessTranslatorBuilder :
    ImageTranslatorBuilder<ImageBiometricLivenessTranslator, ImageBiometricLivenessTranslatorBuilder>() {
    abstract fun setThreshold(threshold: Float): ImageBiometricLivenessTranslatorBuilder
    abstract fun setSign(sign: String): ImageBiometricLivenessTranslatorBuilder
}

class LivenessTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transformList: List<Transform>,
    threshold: Float,
    sign: String
) : ImageBiometricLivenessTranslator(inputWidth, inputHeight, order, meanList, stdList, transformList) {

    private val threshold: Float
    private val sign: String

    init {
        this.threshold = threshold
        this.sign = sign
    }

    override fun postProcessOutput(input: NativeImage, output: Map<String, Tensor>): Boolean {
        val mask = output.entries.firstOrNull() ?: throw IllegalStateException("Mask data is null")
        val arrayData = floatArrayOf()
        (0 until mask.value.data.size.toInt()).forEach { arrayData[it] = mask.value.data.readFloat() }
        val mean = arrayData.average().toFloat()

        return compareNumbers(mean, threshold, sign)
    }

    private fun compareNumbers(number1: Float, number2: Float, sign: String): Boolean {
        return when (sign) {
            ">" -> number1 > number2
            "<" -> number1 < number2
            ">=" -> number1 >= number2
            "<=" -> number1 <= number2
            "==" -> number1 == number2
            "!=" -> number1 != number2
            else -> throw IllegalArgumentException("Invalid sign: $sign")
        }
    }
}

class LivenessTranslatorBuilder : ImageBiometricLivenessTranslatorBuilder() {
    private var threshold: Float = 0.5f
    private lateinit var sign: String

    companion object {
        fun newBuilder(): LivenessTranslatorBuilder {
            return LivenessTranslatorBuilder()
        }
    }

    override fun setThreshold(threshold: Float): LivenessTranslatorBuilder {
        this.threshold = threshold
        return this
    }

    override fun setSign(sign: String): LivenessTranslatorBuilder {
        this.sign = sign
        return this
    }

    override fun self(): LivenessTranslatorBuilder {
        return this
    }

    override fun build(): LivenessTranslator {
        return LivenessTranslator(inputWidth, inputHeight, order, meanList, stdList, transformList, threshold, sign)
    }
}
