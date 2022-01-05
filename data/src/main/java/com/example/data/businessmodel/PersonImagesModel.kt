package com.example.data.businessmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonImagesModel(
    val id: Long = 0,
    val profiles: List<ProfileModel>? = null
): Parcelable