package com.jonathanbernal.libbase.widgets.model

import android.graphics.Typeface
import android.text.TextUtils
import androidx.annotation.*
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_TEXT
import com.jonathanbernal.libbase.widgets.model.views.BaseModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WidgetGravity

class TextModelWidget(
    var text: CharSequence,
    var type: Int = Typeface.NORMAL
) : BaseModelWidget(
    padding = SpacingSimpleTextView()
) {

    @DimenRes
    var fontSize = R.dimen.font_body

    @ColorRes
    var textColor = android.R.color.black

    @StyleRes
    var style = -1

    var backgroundColor = -1

    var gravity: WidgetGravity? = null

    var drawable = DrawableTextModel()

    var tag: Int = -1

    var resourceId: Int = 0
        private set

    var textStyle: Int = type

    var isHtmlText: Boolean = false

    var maxLines: Int? = null

    var ellipsize: TextUtils.TruncateAt? = null

    constructor(resourceId: Int) : this("") {
        this.resourceId = resourceId
    }

    override fun getId(): Int = WIDGET_TEXT
}

class SpacingSimpleTextView() {

    @DimenRes
    var spacingTop = R.dimen.spacing_medium

    @DimenRes
    var spacingBottom = R.dimen.spacing_medium

    @DimenRes
    var spacingLeft = R.dimen.spacing_large

    @DimenRes
    var spacingRight = R.dimen.spacing_large

    @DimenRes
    var withDrawable = R.dimen.spacing_medium

    constructor(@DimenRes dimen: Int) : this() {

        spacingTop = dimen

        spacingBottom = dimen

        spacingLeft = dimen

        spacingRight = dimen
    }

    constructor(
        @DimenRes dimenLeft: Int,
        @DimenRes dimenTop: Int,
        @DimenRes dimenRight: Int,
        @DimenRes dimenBottom: Int,
    ) : this() {

        spacingTop = dimenTop

        spacingBottom = dimenBottom

        spacingLeft = dimenLeft

        spacingRight = dimenRight
    }
}

data class DrawableTextModel(
    var drawableLeft: Int? = null,
    var drawableRight: Int? = null,
    var drawableBottom: Int? = null,
    var drawableTop: Int? = null,
    @DimenRes var compoundDrawablePaddingRes: Int? = null,
    @ColorRes var tintColorRes: Int? = null,
    @DimenRes var widthRes: Int? = null,
    @DimenRes var heightRes: Int? = null,
    @DimenRes var squareSizeRes: Int? = null,
    @IntegerRes var rotationDegree: Int = 0,
)

/**
 * Extensions
 */

fun TextModelWidget.background(@DrawableRes drawable: Int) = this.apply {
    backgroundColor = drawable
}

/**
 * Margin
 */

fun TextModelWidget.margin(@DimenRes dimen: Int) = this.apply {
    margin = SpacingSimpleTextView(dimen)
}

fun TextModelWidget.marginLeft(@DimenRes dimen: Int) = this.apply {
    margin.spacingLeft = dimen
}

fun TextModelWidget.marginTop(@DimenRes dimen: Int) = this.apply {
    margin.spacingTop = dimen
}

fun TextModelWidget.marginRight(@DimenRes dimen: Int) = this.apply {
    margin.spacingRight = dimen
}

fun TextModelWidget.marginBottom(@DimenRes dimen: Int) = this.apply {
    margin.spacingBottom = dimen
}

fun TextModelWidget.standardSideMargin() = this.apply {
    margin.run {
        spacingLeft = R.dimen.spacing_large
        spacingRight = R.dimen.spacing_large
    }
}

fun TextModelWidget.verticalMargin(@DimenRes dimen: Int) = this.apply {
    margin.run {
        spacingTop = dimen
        spacingBottom = dimen
    }
}

fun TextModelWidget.sideMargin(@DimenRes dimen: Int) = this.apply {
    margin.run {
        spacingLeft = dimen
        spacingRight = dimen
    }
}

fun TextModelWidget.emptyMargin() = this.apply {
    margin = SpacingSimpleTextView(R.dimen.spacing_empty)
}

/**
 * Padding
 */

fun TextModelWidget.verticalPadding(@DimenRes dimen: Int) = this.apply {
    padding.run {
        spacingTop = dimen
        spacingBottom = dimen
    }
}

fun TextModelWidget.sidePadding(@DimenRes dimen: Int) = this.apply {
    padding.run {
        spacingLeft = dimen
        spacingRight = dimen
    }
}

/**
 * Drawable
 */

fun TextModelWidget.drawableWidth(@DimenRes dimenRes: Int) = this.apply {
    drawable.widthRes = dimenRes
}

fun TextModelWidget.drawableHeight(@DimenRes dimenRes: Int) = this.apply {
    drawable.heightRes = dimenRes
}

fun TextModelWidget.drawableSize(@DimenRes dimenRes: Int) = this.apply {
    drawable.squareSizeRes = dimenRes
}

fun TextModelWidget.drawablePadding(@DimenRes dimenRes: Int) = this.apply {
    drawable.compoundDrawablePaddingRes = dimenRes
}

fun TextModelWidget.drawableTop(@DrawableRes drawableRes: Int) = this.apply {
    drawable.drawableTop = drawableRes
}

fun TextModelWidget.drawableRight(@DrawableRes drawableRes: Int) = this.apply {
    drawable.drawableRight = drawableRes
}

fun TextModelWidget.drawableLeft(@DrawableRes drawableRes: Int) = this.apply {
    drawable.drawableLeft = drawableRes
}
