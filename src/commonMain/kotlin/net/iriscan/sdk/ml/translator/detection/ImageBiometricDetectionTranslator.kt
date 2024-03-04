package net.iriscan.sdk.ml.translator.detection

import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.transform.Transform
import net.iriscan.sdk.ml.translator.ImageTranslator
import net.iriscan.sdk.ml.translator.ImageTranslatorBuilder

/**
 * @author Anton Kurinnoy
 */
abstract class ImageBiometricDetectionTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transforms: List<Transform>
) : ImageTranslator<List<DetectedBiometric>>(inputWidth, inputHeight, order, meanList, stdList, transforms)

abstract class ImageBiometricDetectionTranslatorBuilder :
    ImageTranslatorBuilder<ImageBiometricDetectionTranslator, ImageBiometricDetectionTranslatorBuilder>() {
    abstract fun setThreshold(threshold: Float): ImageBiometricDetectionTranslatorBuilder
}
