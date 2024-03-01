package net.iriscan.sdk.ml.tensor

import kotlinx.io.Buffer

/**
 * @author Anton Kurinnoy
 */
class Tensor(val shape: Shape, val data: Buffer, val type: TensorType)

typealias Shape = IntArray

enum class TensorType { INT, FLOAT }


