package net.iriscan.sdk.ml.inference

import net.iriscan.sdk.ml.engine.Processor
import net.iriscan.sdk.ml.translator.Translator

/**
 * @author Anton Kurinnoy
 */
internal class DefaultPredictor<I, O>(processor: Processor, translator: Translator<I, O>) :
    Predictor<I, O> {
    private val translator: Translator<I, O>
    private val processor: Processor

    init {
        this.translator = translator
        this.processor = processor
    }

    override fun predict(input: I): O {
        val inputData = translator.preProcessInput(input)
        val outputData = processor.run(inputData)
        return translator.postProcessOutput(input, outputData)
    }
}