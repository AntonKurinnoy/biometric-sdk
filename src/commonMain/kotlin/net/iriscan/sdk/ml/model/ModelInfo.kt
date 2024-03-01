package net.iriscan.sdk.ml.model

/**
 * @author Anton Kurinnoy
 */
class ModelInfo(
    val name: String,
    val type: ModelType,
    val processor: ProcessorType,
    val url: String,
    val checksum: String?,
    val checksumMethod: ChecksumMethod?,
    val input: ModelInput,
    val output: ModelOutput
)

enum class ModelType { FACE_LIVENESS_SCORE, FACE_RECOGNITION, FACE_DETECTION }

enum class ProcessorType { ONNX, TFLITE }

enum class ChecksumMethod { CRC, MD5, SHA256 }
