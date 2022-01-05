package com.example.data.businessmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonModel(
    val birthday: String? = "",
    val placeOfBirth: String? = "",
    val knownFor: String? = "",
    val dayOfDeath: String? = "",
    val name: String? = ""
): Parcelable