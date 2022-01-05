package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class PersonTaggedImagesResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Result>?
)