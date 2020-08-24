package my.luckydog.presentation.core.mappers

import com.facebook.drawee.drawable.ScalingUtils
import my.luckydog.common.Mapper
import my.luckydog.presentation.core.BaseScaleType

class ToScaleTypeFresco : Mapper<BaseScaleType, ScalingUtils.ScaleType> {

    override fun transform(entity: BaseScaleType): ScalingUtils.ScaleType = when (entity) {
        BaseScaleType.FIT_XY -> ScalingUtils.ScaleType.FIT_XY
        BaseScaleType.FIT_START -> ScalingUtils.ScaleType.FIT_START
        BaseScaleType.FIT_CENTER -> ScalingUtils.ScaleType.FIT_CENTER
        BaseScaleType.FIT_END -> ScalingUtils.ScaleType.FIT_END
        BaseScaleType.CENTER -> ScalingUtils.ScaleType.CENTER
        BaseScaleType.CENTER_INSIDE -> ScalingUtils.ScaleType.CENTER_INSIDE
        BaseScaleType.CENTER_CROP -> ScalingUtils.ScaleType.CENTER_CROP
    }
}