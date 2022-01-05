package com.example.actors.ui.main.actorDetails.state

import com.example.data.businessmodel.PersonTaggedImagesModel
import com.example.network.model.responses.ApiErrorResponse

sealed class ActorsTaggedImagesState : RenderState {
    data class Success(val personTaggedImages: PersonTaggedImagesModel?) : ActorsTaggedImagesState()
    data class Error(val code: Int? = null, val error: ApiErrorResponse? = null) : ActorsTaggedImagesState()
}