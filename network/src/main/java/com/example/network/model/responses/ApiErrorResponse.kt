package com.example.network.model.responses

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(@SerializedName("status_message") val statusMessage: String,
                            @SerializedName("status_code") val statusCode: Int,
                            @SerializedName("success") val success: Boolean?
)