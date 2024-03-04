package net.iriscan.sdk.ml.translator.detection

import net.iriscan.sdk.ml.translator.score.Score

/**
 * @author Anton Kurinnoy
 */
class DetectedBiometric(val score: Score, val box: BoundingBox)