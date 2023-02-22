package com.jonathanbernal.libbase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jonathanbernal.libbase.R

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}