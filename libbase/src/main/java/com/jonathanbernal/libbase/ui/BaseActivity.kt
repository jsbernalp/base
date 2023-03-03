package com.jonathanbernal.libbase.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.models.StartActivityModel
import com.jonathanbernal.libbase.viewmodel.BaseViewModel

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    protected fun subscribeViewModel(viewModel: BaseViewModel, root: View) {
        viewModel.startActivity().observe(this) { startActivity(it) }
    }

    protected fun startActivity(startActivityModel: StartActivityModel) {
        val intent = Intent(baseContext, startActivityModel.activity)
        startActivityModel.bundle?.let { intent.putExtras(it) }
        intent.flags = startActivityModel.flags
        startActivityForResult(intent, startActivityModel.code)
    }
}