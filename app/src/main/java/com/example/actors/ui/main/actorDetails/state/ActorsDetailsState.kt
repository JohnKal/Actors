package com.example.actors.ui.main.actorDetails.state

import com.example.data.businessmodel.PersonModel
import com.example.network.model.responses.ApiErrorResponse

sealed class ActorsDetailsState : RenderState {
    object Loading : ActorsDetailsState()
    data class Success(val personDetails: PersonModel?) : ActorsDetailsState()
    data class Error(val code: Int? = null, val error: ApiErrorResponse? = null) : ActorsDetailsState() }