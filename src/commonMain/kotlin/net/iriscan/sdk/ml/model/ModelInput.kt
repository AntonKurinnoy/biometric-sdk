package net.iriscan.sdk.ml.model

/**
 * @author Anton Kurinnoy
 */
sealed class ModelInput(val type: InputType)

class RGBInputData(
    val order: OrderType,
    val shape: IntArray,
    val normalization: Array<FloatArray>,
) : ModelInput(type = InputType.RGB)

enum class InputType { RGB }

enum class OrderType { CWH, CHW }