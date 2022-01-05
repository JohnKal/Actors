package com.example.data.businessmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileModel(
    val filePath: String
): Parcelable