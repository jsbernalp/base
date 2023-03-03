package com.jonathanbernal.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jonathanbernal.libbase.ui.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}