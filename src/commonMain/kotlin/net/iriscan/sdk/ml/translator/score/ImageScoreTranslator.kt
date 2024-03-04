package net.iriscan.sdk.ml.translator.score

import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.transform.Transform
import net.iriscan.sdk.ml.translator.ImageTranslator
import net.iriscan.sdk.ml.translator.ImageTranslatorBuilder

/**
 * @author Anton Kurinnoy
 */
abstract class ImageScoreTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transforms: List<Transform>
) : ImageTranslator<Boolean>(inputWidth, inputHeight, order, meanList, stdList, transforms)

abstract class ImageScoreTranslatorBuilder :
    ImageTranslatorBuilder<ImageScoreTranslator, ImageScoreTranslatorBuilder>() {
    abstract fun setThreshold(threshold: Float): ImageScoreTranslatorBuilder
    abstract fun setSign(sign: String): ImageScoreTranslatorBuilder
}