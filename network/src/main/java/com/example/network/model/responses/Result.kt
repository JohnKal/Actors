package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("file_path") val filePath: String?
)