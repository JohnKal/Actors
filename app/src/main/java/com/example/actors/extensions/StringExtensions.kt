package com.example.actors.extensions

import java.text.ParseException
import java.text.SimpleDateFormat

fun String?.convertToGreekDate(): String {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val newDateFormat = SimpleDateFormat("dd MMM yyyy")
        newDateFormat.format(dateFormat.parse(this))
    } catch (ex: ParseException) {
        this ?: ""
    }
}