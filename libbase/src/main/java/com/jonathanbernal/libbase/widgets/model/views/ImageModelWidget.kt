package com.jonathanbernal.libbase.widgets.model.views

import android.graphics.Bitmap
import androidx.annotation.DimenRes
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_IMAGE_VIEW
import com.rappi.components.widgets.actions.WidgetAction
import com.rappi.components.widgets.model.SpacingSimpleTextView

class ImageModelWidget(
    val backgroundUrl: String? = null,
    val backgroundRes: Int? = null,
    val backgroundColor: String? = null,
    var image: Bitmap? = null,
    val imageUrl: String = "",
    val imageRes: Int? = null,
    val imageTint: Int? = null,
    @DimenRes val cornerRadius: Int? = null,
    padding: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    width: Int = -1,
    height: Int = -2,
    extras: Map<String, Any>? = null,
    action: WidgetAction? = null,
) : BaseModelWidget(
    height = height,
    width = width,
    padding = padding,
    margin = margin,
    extras = extras,
    action = action
) {
    override fun getId(): Int = WIDGET_IMAGE_VIEW
}
