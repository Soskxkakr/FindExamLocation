package com.example.findexamlocation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Area(
    val name : String = "",
    val lat : Double = 0.0,
    val long : Double = 0.0,
) : Parcelable
