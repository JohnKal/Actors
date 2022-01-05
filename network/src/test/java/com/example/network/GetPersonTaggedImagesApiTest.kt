package com.example.network

import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Test

@SmallTest
class GetPersonTaggedImagesApiTest : BaseNetworkTest() {

    private lateinit var mockedResponse: String
    private lateinit var mockedFailedResponse401: String
    private lateinit var mockedFailedResponse404: String

    override fun setUp() {
        setMockServerUrl()
        mockedResponse = MockResponseFileReader("raw/get_person_tagged_images_response.json").content
        mockedFailedResponse401 = MockResponseFileReader("raw/get_person_failed_401_response.json").content
        mockedFailedResponse404 = MockResponseFileReader("raw/get_person_failed_404_response.json").content
    }

    @Test
    fun `given mock response with 200 code verify that getPerson response is not null or empty list`() {

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        runBlocking {
            val mockResponseList = mockService.getPersonTaggedImages("37625")

            Assert.assertNotNull(mockResponseList.body())
        }
    }

    @Test
    fun `given mock response with 200 code verify that getPerson response is the expected`() {

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        runBlocking {
            val mockResponse = mockService.getPersonTaggedImages("37625")

            Assert.assertTrue(mockResponse.isSuccessful)
            Assert.assertEquals(37625L, mockResponse.body()?.id)
            Assert.assertNotNull(mockResponse.body()?.results)
            Assert.assertEquals("/sVYgFC6z0RZJrgUpve4XlyrVrgr.jpg", mockResponse.body()?.results?.getOrNull(0)?.filePath)
        }
    }

    @Test
    fun `given mock response with 401 error code verify that getPerson response is not successful and we get the expected error response`() {

        server.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(mockedFailedResponse401)
        )

        runBlocking {
            val mockResponse = mockService.getPersonTaggedImages("37625")

            val apiResponseError = convertErrorResponse(mockResponse.errorBody())

            Assert.assertFalse(mockResponse.isSuccessful)
            Assert.assertEquals("Invalid API key: You must be granted a valid key.", apiResponseError?.statusMessage)
            Assert.assertFalse(apiResponseError?.success == true)
            Assert.assertEquals(7, apiResponseError?.statusCode)
        }
    }

    @Test
    fun `given mock response with 404 error code verify that getPerson response is not successful and we get the expected error response`() {

        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(mockedFailedResponse404)
        )

        runBlocking {
            val mockResponse = mockService.getPersonTaggedImages("37625")

            val apiResponseError = convertErrorResponse(mockResponse.errorBody())

            Assert.assertFalse(mockResponse.isSuccessful)
            Assert.assertEquals("The resource you requested could not be found.", apiResponseError?.statusMessage)
            Assert.assertEquals(34, apiResponseError?.statusCode)
        }
    }

    override fun tearDown() {}

    override fun setMockServerUrl(): String = "/api.themoviedb.org/3/"
}