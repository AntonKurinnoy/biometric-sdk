package net.iriscan.sdk.core.tf

import net.iriscan.sdk.core.io.HashMethod
import net.iriscan.sdk.io.ResourceIOFactory
import java.nio.ByteBuffer
import java.nio.ByteOrder

actual class InterpreterImpl actual constructor(modelName: String,
                                                modelPath: String,
                                                modelChecksum: String?,
                                                modelChecksumMethod: HashMethod?,
                                                overrideCacheOnWrongChecksum: Boolean?) : Interpreter {
    private val interpreter: org.tensorflow.lite.Interpreter

    init {
        // TODO
        val modelBuffer = ByteBuffer.allocateDirect(model.size)
        modelBuffer.order(ByteOrder.nativeOrder())
        modelBuffer.put(model)
        val options = org.tensorflow.lite.Interpreter.Options().apply {
            this.numThreads = Runtime.getRuntime().availableProcessors()
        }
        interpreter = org.tensorflow.lite.Interpreter(modelBuffer, options)
    }

    override fun invoke(inputs: Map<Int, Any>, outputs: MutableMap<Int, Any>) {
        interpreter.runForMultipleInputsOutputs(Array(inputs.size) { i -> inputs[i] }, outputs)
    }
}