package com.rappi.components.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.jonathanbernal.libbase.R

fun Drawable.tintWithHexColor(hexColor: String?): Drawable =
    hexColor?.let { tintWithColor(Color.parseColor(it)) } ?: this

fun Drawable.tint(context: Context, @ColorRes tintColorRes: Int?): Drawable =
    tintColorRes?.let { tintWithColor(ContextCompat.getColor(context, it)) } ?: this

fun Drawable.tintWithColor(@ColorInt tintColor: Int): Drawable = apply {
    DrawableCompat.wrap(this)
        .apply { DrawableCompat.setTintList(this, ColorStateList.valueOf(tintColor)) }
}

fun Drawable.tintSolidWithHexColor(hexColor: String?): Drawable =
    hexColor?.let { tintSolid(Color.parseColor(it)) } ?: this

fun Drawable.tintSolid(@ColorInt tintColor: Int): Drawable = apply {
    if (this is GradientDrawable) {
        setColor(tintColor)
    }
}

fun Drawable.tintSolid(
    context: Context,
    @ColorRes color: Int
): Drawable = apply {
    if (this is GradientDrawable) {
        setColor(ContextCompat.getColor(context, color))
    }
}

fun Drawable.resize(
    context: Context,
    @DimenRes widthRes: Int?,
    @DimenRes heightRes: Int? = widthRes
): Drawable =
    if (widthRes != null && heightRes != null) {
        val width = context.resources.getDimensionPixelSize(widthRes)
        val height = context.resources.getDimensionPixelSize(heightRes)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        setBounds(0, 0, width, height)
        draw(canvas)
        BitmapDrawable(context.resources, bitmap)
    } else this

fun Drawable.tintBorder(
    context: Context,
    @ColorRes tintColorRes: Int,
    @DimenRes strokeWidthRes: Int = R.dimen.spacing_xmicro
) {
    if (this is GradientDrawable) {
        val strokeWidth = context.resources.getDimensionPixelSize(strokeWidthRes)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val strokeColor = ContextCompat.getColorStateList(context, tintColorRes)
            setStroke(strokeWidth, strokeColor)
        } else {
            val strokeColor = ContextCompat.getColor(context, tintColorRes)
            setStroke(strokeWidth, strokeColor)
        }
    }
}

fun Drawable.rotate(
    context: Context,
    rotateDegree: Int
): Drawable {
    Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
        val canvas = Canvas(this)
        setBounds(0, 0, width, height)
        draw(canvas)
        val matrix = Matrix().apply { postRotate(rotateDegree.toFloat()) }
        val rotatedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
        return BitmapDrawable(context.resources, rotatedBitmap)
    }
}
