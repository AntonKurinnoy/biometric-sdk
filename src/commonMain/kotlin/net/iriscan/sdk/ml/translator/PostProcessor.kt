package net.iriscan.sdk.ml.translator

import net.iriscan.sdk.ml.tensor.Tensor

/**
 * @author Anton Kurinnoy
 */
interface PostProcessor<in I, out O> {
    fun postProcessOutput(input: I, output: Map<String, Tensor>): O
}