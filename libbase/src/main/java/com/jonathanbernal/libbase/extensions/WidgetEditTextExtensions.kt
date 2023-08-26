package com.jonathanbernal.libbase.extensions

import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import com.jonathanbernal.libbase.widgets.model.DrawableTextModel

fun EditText.addLettersOnlyFilter() {
    val allowedCharTypes = arrayOf(
        Character.UPPERCASE_LETTER.toInt(),
        Character.LOWERCASE_LETTER.toInt(),
        Character.SPACE_SEPARATOR.toInt()
    )
    val filter = InputFilter { source, _, _, _, _, _ ->
        source.map { Character.getType(it) }.filter { it !in allowedCharTypes }.map { "" }
            .firstOrNull()
    }
    filters += arrayOf(filter)
}

fun EditText.addLettersAndDigitsOnlyFilter() {
    val filter = InputFilter { source, _, _, _, _, _ ->
        source.filter { !it.isLetterOrDigit() }.map { "" }.firstOrNull()
    }
    filters += arrayOf(filter)
}

fun EditText.addFilterMaxLength(maxLength: Int) {
    if (maxLength > 0) filters += InputFilter.LengthFilter(maxLength)
}

fun EditText.applyDrawable(drawableTextModel: DrawableTextModel) {
    val widthRes = drawableTextModel.widthRes ?: drawableTextModel.squareSizeRes
    val heightRes = drawableTextModel.heightRes ?: drawableTextModel.squareSizeRes

    val drawableLeft: Drawable? = drawableTextModel.drawableLeft?.let {
        AppCompatResources.getDrawable(context, it)
            ?.tint(context, drawableTextModel.tintColorRes)
            ?.resize(context, widthRes, heightRes)
    }
    val drawableRight: Drawable? = drawableTextModel.drawableRight?.let {
        AppCompatResources.getDrawable(context, it)
            ?.tint(context, drawableTextModel.tintColorRes)
            ?.resize(context, widthRes, heightRes)
            ?.rotate(context, drawableTextModel.rotationDegree)
    }
    val drawableBottom: Drawable? = drawableTextModel.drawableBottom?.let {
        AppCompatResources.getDrawable(context, it)
            ?.tint(context, drawableTextModel.tintColorRes)
            ?.resize(context, widthRes, heightRes)
    }
    val drawableTop: Drawable? = drawableTextModel.drawableTop?.let {
        AppCompatResources.getDrawable(context, it)
            ?.tint(context, drawableTextModel.tintColorRes)
            ?.resize(context, widthRes, heightRes)
    }

    setCompoundDrawablesWithIntrinsicBounds(
        drawableLeft,
        drawableTop,
        drawableRight,
        drawableBottom
    )

    drawableTextModel.compoundDrawablePaddingRes?.let {
        compoundDrawablePadding = context.resources.getDimensionPixelSize(it)
    }
}
