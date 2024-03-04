package net.iriscan.sdk.ml.translator

import net.iriscan.sdk.ml.model.OrderType
import net.iriscan.sdk.ml.transform.Transform

/**
 * @author Anton Kurinnoy
 */
abstract class ImageTranslatorBuilder<out OT : ImageTranslator<*>, out OB> {
    protected var inputWidth = 0
    protected var inputHeight = 0
    protected lateinit var order: OrderType
    protected lateinit var meanList: List<FloatArray>
    protected lateinit var stdList: List<FloatArray>
    protected var transformList: List<Transform> = emptyList()

    fun setSize(width: Int, height: Int): OB {
        this.inputWidth = width
        this.inputHeight = height
        return self()
    }

    fun setOrder(order: OrderType): OB {
        this.order = order
        return self()
    }

    fun setNormalizeParams(meanList: List<FloatArray>, stdList: List<FloatArray>): OB {
        this.meanList = meanList
        this.stdList = stdList
        return self()
    }

    fun addTransforms(items: Pipeline): OB {
        this.transformList = items
        return self()
    }

    protected abstract fun self(): OB

    abstract fun build(): OT
}