package com.jonathanbernal.libbase.models

import android.os.Bundle

class StartActivityModel(val activity: Class<*>, bundle: Bundle? = null, code: Int = 0) {
    var bundle: Bundle? = bundle
    var code: Int = code
    var flags: Int = 0

    fun setFlags(flags: Int): StartActivityModel = apply { this.flags = flags }

    constructor(activity: Class<*>, code: Int = 0) : this(activity, null, code)
}