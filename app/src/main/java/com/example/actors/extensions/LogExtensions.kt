package com.example.actors.extensions

import android.util.Log
import com.example.actors.BuildConfig

fun Any.log(message: () -> String) {
    if (BuildConfig.DEBUG) Log.i(this::class.java.simpleName, message())
}