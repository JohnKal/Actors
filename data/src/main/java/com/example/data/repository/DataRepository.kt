package com.example.data.repository

import com.example.data.businessmodel.PersonImagesModel
import com.example.data.businessmodel.PersonModel
import com.example.data.businessmodel.PersonTaggedImagesModel
import com.example.network.helpers.ResultWrapper

interface DataRepository {

    /**
     * Get person
     */
    suspend fun getPerson(id: String): ResultWrapper<PersonModel?>
    suspend fun getPersonDetails(id: String): PersonModel?

    /**
     * Get images for person
     */
    suspend fun getPersonImages(id: String): ResultWrapper<PersonImagesModel?>
    suspend fun getPersonImagesDetails(id: String): PersonImagesModel?

    /**
     * Get tagged images for person
     */
    suspend fun getPersonTaggedImages(id: String): ResultWrapper<PersonTaggedImagesModel?>
    suspend fun getPersonTaggedImagesDetails(id: String): PersonTaggedImagesModel?
}