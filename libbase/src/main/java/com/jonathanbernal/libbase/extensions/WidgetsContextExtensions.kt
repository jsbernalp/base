package com.jonathanbernal.libbase.extensions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int) =
    drawableRes.takeIf { it != 0 }?.let { AppCompatResources.getDrawable(this, it) }
