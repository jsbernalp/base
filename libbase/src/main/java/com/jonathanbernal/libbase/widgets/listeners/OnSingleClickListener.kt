package com.jonathanbernal.libbase.widgets.listeners

import android.os.SystemClock
import android.view.View

private const val MIN_CLICK_INTERVAL: Long = 500

class OnSingleClickListener(private val onSingleClick: (View) -> Unit) : View.OnClickListener {

    private var mLastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return
        mLastClickTime = currentClickTime
        onSingleClick(v)
    }
}
