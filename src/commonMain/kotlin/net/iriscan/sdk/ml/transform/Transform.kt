package net.iriscan.sdk.ml.transform

import net.iriscan.sdk.ml.tensor.Tensor

/**
 * @author Anton Kurinnoy
 */
interface Transform {
    fun transform(tensor: Tensor): Tensor
}