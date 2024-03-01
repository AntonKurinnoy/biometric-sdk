package net.iriscan.sdk.ml.engine

import kotlinx.io.core.Closeable
import net.iriscan.sdk.ml.tensor.Tensor

/**
 * @author Anton Kurinnoy
 */
interface Processor : Closeable {
    fun initialize()
    fun run(input: Map<Int, Tensor>): Map<String, Tensor>
}