package com.jonathanbernal.libbase.viewmodel

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jonathanbernal.libbase.models.StartActivityModel
import com.rappi.nitro.base.viewModels.lifecycle.ConsumerLiveData

open class BaseViewModel : ViewModel() {

    protected val startActivity = ConsumerLiveData<StartActivityModel>()

    @CheckResult
    fun startActivity(): LiveData<StartActivityModel> = startActivity

}