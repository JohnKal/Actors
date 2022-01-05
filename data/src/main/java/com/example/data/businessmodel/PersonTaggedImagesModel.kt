package com.example.data.businessmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonTaggedImagesModel(
    val results: List<ResultModel>? = null
): Parcelable