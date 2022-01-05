package com.example.data.extensions

import com.example.data.businessmodel.PersonImagesModel
import com.example.data.businessmodel.ProfileModel
import com.example.network.model.responses.PersonImagesResponse

fun PersonImagesResponse?.mapToDomainModel(): PersonImagesModel? {
    return this?.let {
        PersonImagesModel(
            it.id,
            it.profiles?.map { profile ->
                ProfileModel(
                    filePath = profile.filePath
                )
            }
        )
    } ?: null
}