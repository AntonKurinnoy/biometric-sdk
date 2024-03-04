package net.iriscan.sdk.ml.model

/**
 * @author Anton Kurinnoy
 */
sealed class ModelOutput(val type: OutputType)

class MaskScoreOutputData(
    val outputOrder: Array<String>,
    val shape: IntArray,
    val method: String,
    val threshold: Double,
    val sign: String,
    val classes: Array<String>
) : ModelOutput(type = OutputType.MASK_SCORE)

class MaskOutputData(
    val outputOrder: Array<String>,
    val shape: IntArray,
    val method: String,
    val threshold: Double,
    val sign: String,
    val classes: Array<String>
) : ModelOutput(type = OutputType.MASK)

class TemplateOutputData(
    val length: Int,
    val scoreAlgorithm: ScoreAlgorithm,
) : ModelOutput(type = OutputType.TEMPLATE)

class ScoresBoxesOutputData(
    val outputOrder: Array<String>,
    val threshold: Double,
    val bboxType: BBoxType,
) : ModelOutput(type = OutputType.SCORES_BOXES)


enum class OutputType { MASK_SCORE, MASK, TEMPLATE, SCORES_BOXES }

enum class ScoreAlgorithm { L2NORM, COSNORM }

enum class BBoxType { BBOX_ULURWH, BBOX_ULURBLBR, BBOX_CXCYWH }