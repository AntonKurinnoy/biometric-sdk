package net.iriscan.sdk.ml.tensor

import kotlinx.io.Buffer

/**
 * @author Anton Kurinnoy
 */
class Tensor(val shape: TensorShape, val data: Buffer, val type: TensorType)

typealias TensorShape = IntArray

enum class TensorType { INT, FLOAT }
