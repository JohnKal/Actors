package com.example.data.extensions

import com.example.data.businessmodel.PersonTaggedImagesModel
import com.example.data.businessmodel.ResultModel
import com.example.network.model.responses.PersonTaggedImagesResponse

fun PersonTaggedImagesResponse?.mapToDomainModel(): PersonTaggedImagesModel? {
    return this?.let {
        PersonTaggedImagesModel(
            it.results?.map { result ->
                ResultModel(
                    filePath = result.filePath
                )
            }
        )
    } ?: null
}