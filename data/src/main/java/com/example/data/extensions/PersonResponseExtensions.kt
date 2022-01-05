package com.example.data.extensions

import com.example.data.businessmodel.PersonModel
import com.example.network.model.responses.PersonResponse

fun PersonResponse?.mapToDomainModel() : PersonModel? {
    return this?.let {
        PersonModel(
            it.birthday,
            it.placeOfBirth,
            it.knownFor,
            it.dayOfDeath,
            it.name
        )
    }
}