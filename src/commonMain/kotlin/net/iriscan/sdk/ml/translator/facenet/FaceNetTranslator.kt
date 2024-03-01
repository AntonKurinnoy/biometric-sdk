package net.iriscan.sdk.ml.translator.facenet

import kotlinx.io.readFloat
import net.iriscan.sdk.core.image.NativeImage
import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.tensor.Tensor
import net.iriscan.sdk.ml.transform.Transform
import net.iriscan.sdk.ml.translator.ImageTranslator
import net.iriscan.sdk.ml.translator.ImageTranslatorBuilder
import net.iriscan.sdk.ml.translator.data.Template

/**
 * @author Anton Kurinnoy
 */
abstract class ImageBiometricRecognitionTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transforms: List<Transform>
) : ImageTranslator<Template>(inputWidth, inputHeight, order, meanList, stdList, transforms)

abstract class ImageBiometricRecognitionTranslatorBuilder :
    ImageTranslatorBuilder<ImageBiometricRecognitionTranslator, ImageBiometricRecognitionTranslatorBuilder>()


class FaceRecognitionTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transformList: List<Transform>
) : ImageBiometricRecognitionTranslator(inputWidth, inputHeight, order, meanList, stdList, transformList) {

    override fun postProcessOutput(input: NativeImage, output: Map<String, Tensor>): Template {
        val template = output.entries.firstOrNull() ?: throw IllegalStateException("Template data is null")
        val arrayData = floatArrayOf()
        (0 until template.value.data.size.toInt()).forEach { arrayData[it] = template.value.data.readFloat() }
        return arrayData
    }
}

class FaceRecognitionTranslatorBuilder : ImageBiometricRecognitionTranslatorBuilder() {

    companion object {
        fun newBuilder(): FaceRecognitionTranslatorBuilder {
            return FaceRecognitionTranslatorBuilder()
        }
    }

    override fun self(): FaceRecognitionTranslatorBuilder {
        return this
    }

    override fun build(): FaceRecognitionTranslator {
        return FaceRecognitionTranslator(inputWidth, inputHeight, order, meanList, stdList, transformList)
    }
}