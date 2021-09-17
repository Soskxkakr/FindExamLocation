package com.example.findexamlocation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    val inputLocationName = MutableLiveData<String>()

    private val _locationName = MutableLiveData<String>()
    val locationName : LiveData<String>
        get() = _locationName
}