package net.iriscan.sdk.ml.inference

/**
 * @author Anton Kurinnoy
 */
interface Predictor<in I, out O> {
    fun predict(input: I): O
}
