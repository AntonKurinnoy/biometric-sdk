package net.iriscan.sdk.ml.inference

/**
 * @author Anton Kurinnoy
 */
interface InferenceModel<I, O> {
    fun newPredictor(): Predictor<I, O>
}