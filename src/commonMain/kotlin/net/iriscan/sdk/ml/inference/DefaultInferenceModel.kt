package net.iriscan.sdk.ml.inference

import net.iriscan.sdk.ml.engine.Processor
import net.iriscan.sdk.ml.translator.Translator

/**
 * @author Anton Kurinnoy
 */
class DefaultInferenceModel<I, O>(processor: Processor, translator: Translator<I, O>) :
    InferenceModel<I, O> {

    private var processor: Processor
    private var translator: Translator<I, O>

    init {
        this.processor = processor
        this.translator = translator
    }

    override fun newPredictor(): Predictor<I, O> = DefaultPredictor(this.processor, this.translator)
}