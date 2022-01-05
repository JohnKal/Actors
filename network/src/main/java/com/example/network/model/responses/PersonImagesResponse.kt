package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class PersonImagesResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("profiles") val profiles: List<Profile>?
)