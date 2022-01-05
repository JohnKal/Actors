package com.example.actors.ui.main.actorDetails.state

import com.example.data.businessmodel.PersonImagesModel
import com.example.network.model.responses.ApiErrorResponse

sealed class ActorsImagesState : RenderState {
    data class Success(val personImages: PersonImagesModel?) : ActorsImagesState()
    data class Error(val code: Int? = null, val error: ApiErrorResponse? = null) : ActorsImagesState()
}