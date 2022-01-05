package com.example.network

import com.example.network.api.ServiceEndpoints
import com.example.network.model.responses.ApiErrorResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

abstract class BaseNetworkTest {

    val server = MockWebServer()
    lateinit var service: ServiceEndpoints
    lateinit var mockService: ServiceEndpoints

    @Before
    fun setUpBefore() {
        setUpWebServer()
        setUp()
    }

    private fun setUpWebServer() {
        server.start(8000)

        var BASE_URL = server.url(BuildConfig.BASE_URL).toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .build()

        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(ServiceEndpoints::class.java)

        mockService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(server.url(setMockServerUrl()))
            .client(okHttpClient)
            .build().create(ServiceEndpoints::class.java)
    }


    @After
    fun tearDownAfter() {
        server.shutdown()
        tearDown()
    }

    fun convertErrorResponse(responseBody: ResponseBody?): ApiErrorResponse? {
        return try {
            responseBody?.charStream().let {
                return Gson().fromJson(it, ApiErrorResponse::class.java)
            }
        } catch (ex: Exception) {
            null
        }
    }

    protected abstract fun setUp()
    protected abstract fun tearDown()
    protected abstract fun setMockServerUrl(): String
}