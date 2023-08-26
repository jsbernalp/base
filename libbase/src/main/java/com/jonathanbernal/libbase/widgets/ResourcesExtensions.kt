package com.jonathanbernal.libbase.widgets

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.appcompat.widget.AppCompatTextView

fun Context.getResourceId(resourceType: String, resName: String): Int? {
    val id = resources.getIdentifier(
        resName,
        resourceType,
        packageName,
    )
    return if (id == 0) null else id
}

fun AppCompatTextView.fromHtml(html: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(html)
    }
}
