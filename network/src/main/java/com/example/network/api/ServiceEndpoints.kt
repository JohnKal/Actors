package com.example.network.api

import com.example.network.api.ServiceEndpoints.SECRETS.API_KEY
import com.example.network.model.responses.PersonImagesResponse
import com.example.network.model.responses.PersonResponse
import com.example.network.model.responses.PersonTaggedImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceEndpoints {

    object SECRETS {
        const val API_KEY = "543e2a266493176aa56671b649f0db3d"
    }

    @GET("person/{person_id}")
    suspend fun getPerson(@Path("person_id") personId: String,
                          @Query("api_key") apiKey: String = API_KEY): Response<PersonResponse>

    @GET("person/{person_id}/images")
    suspend fun getPersonImages(@Path("person_id") personId: String,
                                @Query("api_key") apiKey: String = API_KEY): Response<PersonImagesResponse>

    @GET("person/{person_id}/tagged_images")
    suspend fun getPersonTaggedImages(@Path("person_id") personId: String,
                                      @Query("api_key") apiKey: String = API_KEY): Response<PersonTaggedImagesResponse>
}