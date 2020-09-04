package com.gorothschild.myplantdiary.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationLiveDate = LocationLiveData(application)
    fun getLocationLiveData(application: Application) = locationLiveDate

}