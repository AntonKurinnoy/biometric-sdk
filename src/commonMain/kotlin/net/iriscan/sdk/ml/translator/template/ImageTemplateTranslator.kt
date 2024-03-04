package net.iriscan.sdk.ml.translator.template

import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.transform.Transform
import net.iriscan.sdk.ml.translator.ImageTranslator
import net.iriscan.sdk.ml.translator.ImageTranslatorBuilder

/**
 * @author Anton Kurinnoy
 */
abstract class ImageTemplateTranslator(
    inputWidth: Int,
    inputHeight: Int,
    order: OrderType,
    meanList: List<FloatArray>,
    stdList: List<FloatArray>,
    transforms: List<Transform>
) : ImageTranslator<Template>(inputWidth, inputHeight, order, meanList, stdList, transforms)

abstract class ImageTemplateTranslatorBuilder :
    ImageTranslatorBuilder<ImageTemplateTranslator, ImageTemplateTranslatorBuilder>()
