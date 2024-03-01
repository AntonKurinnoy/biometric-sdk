package net.iriscan.sdk.ml.translator

import net.iriscan.sdk.ml.tensor.Tensor

/**
 * @author Anton Kurinnoy
 */
interface PreProcessor<in I> {
    fun preProcessInput(input: I): Map<Int, Tensor>
}