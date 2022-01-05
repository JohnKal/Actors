package com.example.data.repository

import com.example.data.businessmodel.PersonImagesModel
import com.example.data.businessmodel.PersonModel
import com.example.data.businessmodel.PersonTaggedImagesModel
import com.example.data.extensions.mapToDomainModel
import com.example.network.api.ServiceEndpoints
import com.example.network.helpers.NetworkHelper
import com.example.network.helpers.ResultWrapper
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class DataRepositoryImpl @Inject constructor(
    val serviceEndpoints: ServiceEndpoints,
    val networkHelper: NetworkHelper
) : DataRepository {

    override suspend fun getPerson(id: String): ResultWrapper<PersonModel?> =
        withContext(coroutineContext) {
            networkHelper.safeApiCall {
                getPersonDetails(id)
            }
        }

    override suspend fun getPersonDetails(id: String): PersonModel? {
        val response = serviceEndpoints.getPerson(id)

        if (!response.isSuccessful)
            throw HttpException(response)

        return response.body().mapToDomainModel()
    }

    override suspend fun getPersonImages(id: String): ResultWrapper<PersonImagesModel?> =
        withContext(coroutineContext) {
            networkHelper.safeApiCall {
                getPersonImagesDetails(id)
            }
        }

    override suspend fun getPersonImagesDetails(id: String): PersonImagesModel? {
        val response = serviceEndpoints.getPersonImages(id)

        if (!response.isSuccessful)
            throw HttpException(response)

        return response.body().mapToDomainModel()
    }

    override suspend fun getPersonTaggedImages(id: String): ResultWrapper<PersonTaggedImagesModel?> =
        withContext(coroutineContext) {
            networkHelper.safeApiCall {
                getPersonTaggedImagesDetails(id)
            }
        }

    override suspend fun getPersonTaggedImagesDetails(id: String): PersonTaggedImagesModel? {
        val response = serviceEndpoints.getPersonTaggedImages(id)

        if (!response.isSuccessful)
            throw HttpException(response)

        return response.body().mapToDomainModel()
    }
}