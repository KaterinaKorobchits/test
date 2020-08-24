package my.luckydog.presentation.views

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import my.luckydog.common.Mapper
import my.luckydog.presentation.R
import my.luckydog.presentation.core.BaseScaleType
import my.luckydog.presentation.core.extensions.*
import my.luckydog.presentation.core.mappers.ToScaleTypeFresco
import java.io.File

class BaseImageView : SimpleDraweeView, LoadImage {

    private val scaleTypeMapper: Mapper<BaseScaleType, ScalingUtils.ScaleType> = ToScaleTypeFresco()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) :
            super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        context.typedArray(attrs, R.styleable.BaseImageView).run {
            getDefinedResId(R.styleable.BaseImageView_imageRes)?.let { load(it) }
            getDefinedResId(R.styleable.BaseImageView_placeholderImageRes)?.let { setPlaceholder(it) }
            getDefinedResId(R.styleable.BaseImageView_failureImageRes)?.let { setFailure(it) }
            getDefinedBoolean(R.styleable.BaseImageView_asCircle)?.let { asCircle(it) }
            getDefinedColor(R.styleable.BaseImageView_borderColor)?.let { setBorderColor(it) }
            getDefinedInt(R.styleable.BaseImageView_baseScaleType)?.let {
                setScaleType(BaseScaleType.values()[it])
            }
            getDefinedDimensionPixel(R.styleable.BaseImageView_borderWidth)?.let {
                setBorderWidth(it.toFloat())
            }
            getDefinedDimensionPixel(R.styleable.BaseImageView_cornerRadius)?.let {
                setCorners(it.toFloat())
            }

            recycle()
        }
    }

    override fun load(url: String) = setImageURI(url)

    override fun load(file: File) = setImageURI(Uri.fromFile(file).toString())

    override fun load(drawable: Drawable) {
        post {
            // TODO process rounded corners and borders
            rounding().cornersRadii
            rounding().borderWidth
            rounding().borderColor

            val result = if (rounding().roundAsCircle) {
                drawable.toBitmap(width, height)?.let {
                    RoundedBitmapDrawableFactory.create(resources, it).apply { isCircular = true }
                }
            } else drawable

            (this as ImageView).setImageDrawable(result)
        }
    }

    override fun load(resourceId: Int) {
        when {
            resources.isDrawable(resourceId) -> getDrawable(context, resourceId)?.let { load(it) }
            resources.isColor(resourceId) -> load(ColorDrawable(getColor(context, resourceId)))
            else -> setImageRequest(ImageRequestBuilder.newBuilderWithResourceId(resourceId).build())
        }
    }

    override fun setFailure(file: File) = run {
        val drawable = Drawable.createFromPath(file.absolutePath)
        drawable?.let { setFailure(it) } ?: setFailure(android.R.color.transparent)
    }

    override fun setFailure(drawable: Drawable) = apply { hierarchy.setFailureImage(drawable) }

    override fun setFailure(resourceId: Int) = apply { hierarchy.setFailureImage(resourceId) }

    override fun setPlaceholder(file: File) = run {
        val drawable = Drawable.createFromPath(file.absolutePath)
        drawable?.let { setPlaceholder(it) } ?: setPlaceholder(android.R.color.transparent)
    }

    override fun setPlaceholder(drawable: Drawable) = apply {
        hierarchy.setPlaceholderImage(drawable)
    }

    override fun setPlaceholder(resourceId: Int) = apply {
        hierarchy.setPlaceholderImage(resourceId)
    }

    override fun setScaleType(scaleType: BaseScaleType) = apply {
        hierarchy.actualImageScaleType = scaleTypeMapper.transform(scaleType)
    }

    override fun asCircle(isCircle: Boolean) = apply { rounding().roundAsCircle = isCircle }

    override fun setCorners(radius: Float) = apply { rounding().setCornersRadius(radius) }

    override fun setCorners(radiusRes: Int) = setCorners(resources.getDimension(radiusRes))

    override fun setCorners(
        topLeft: Float,
        topRight: Float,
        bottomRight: Float,
        bottomLeft: Float
    ) = apply { rounding().setCornersRadii(topLeft, topRight, bottomRight, bottomLeft) }

    override fun setCorners(
        topLeft: Int,
        topRight: Int,
        bottomRight: Int,
        bottomLeft: Int
    ) = with(resources) {
        setCorners(
            getDimension(topLeft),
            getDimension(topRight),
            getDimension(bottomRight),
            getDimension(bottomLeft)
        )
    }

    override fun setBorderColor(color: Int) = apply { rounding().borderColor = color }

    override fun setBorderColorRes(colorRes: Int) = setBorderColor(getColor(context, colorRes))

    override fun setBorderWidth(width: Float) = apply { rounding().borderWidth = width }

    override fun setBorderWidthRes(widthRes: Int) = setBorderWidth(resources.getDimension(widthRes))

    private fun rounding() = hierarchy.roundingParams ?: RoundingParams()
        .apply { hierarchy.roundingParams = this }
}