package com.rappi.components.widgets.model

import androidx.annotation.DimenRes

data class SpacingBoxCornerRadius(
    @DimenRes val radiusTopStart: Int,
    @DimenRes val radiusTopEnd: Int,
    @DimenRes val radiusBottomEnd: Int,
    @DimenRes val radiusBottomStart: Int,
) {
    constructor(@DimenRes dimen: Int) : this(
        dimen, dimen, dimen, dimen
    )
}
