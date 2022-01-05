package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("known_for_department") val knownFor: String?,
    @SerializedName("deathday") val dayOfDeath: String?,
    @SerializedName("name") val name: String?
)