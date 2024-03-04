package net.iriscan.sdk.ml.inference

import net.iriscan.sdk.ml.engine.Processor
import net.iriscan.sdk.ml.translator.Translator

/**
 * @author Anton Kurinnoy
 */
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