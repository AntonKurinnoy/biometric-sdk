package net.iriscan.sdk.ml.inference

import net.iriscan.sdk.ml.engine.Processor
import net.iriscan.sdk.ml.translator.Translator

/**
 * @author Anton Kurinnoy
 */
interface InferenceModel<I, O> {
    fun newPredictor(): Predictor<I, O>
}

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

class InferenceModelBuilder<I, O> {
    private lateinit var processor: Processor
    private lateinit var translator: Translator<I, O>

    fun setTranslator(translator: Translator<I, O>): InferenceModelBuilder<I, O> {
        this.translator = translator
        return this
    }

    fun setProcessor(processor: Processor): InferenceModelBuilder<I, O> {
        this.processor = processor
        return this
    }

    fun build(): DefaultInferenceModel<I, O> {
        return DefaultInferenceModel(this.processor, this.translator)
    }

    companion object {
        fun <I, O> newBuilder(): InferenceModelBuilder<I, O> {
            return InferenceModelBuilder()
        }
    }
}