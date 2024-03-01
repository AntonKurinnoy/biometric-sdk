package net.iriscan.sdk.ml.translator

import net.iriscan.sdk.ml.transform.Transform

/**
 * @author Anton Kurinnoy
 */
interface Translator<in I, out O> : PreProcessor<I>, PostProcessor<I, O>

typealias Pipeline = MutableList<Transform>
