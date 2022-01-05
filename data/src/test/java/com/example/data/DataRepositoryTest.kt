package com.example.data

import androidx.test.filters.SmallTest
import com.example.data.businessmodel.PersonImagesModel
import com.example.data.businessmodel.PersonModel
import com.example.data.businessmodel.PersonTaggedImagesModel
import com.example.data.extensions.mapToDomainModel
import com.example.data.repository.DataRepositoryImpl
import com.example.data.repository.DataRepository
import com.example.network.api.ServiceEndpoints
import com.example.network.helpers.NetworkHelper
import com.example.network.model.responses.PersonImagesResponse
import com.example.network.model.responses.PersonResponse
import com.example.network.model.responses.PersonTaggedImagesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type

@SmallTest
@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest {

    private lateinit var mockedResponse: String
    private lateinit var mockedResponseModel: String
    private lateinit var mockedImagesResponse: String
    private lateinit var mockedImagesResponseModel: String
    private lateinit var mockedTaggedImagesResponse: String
    private lateinit var mockedTaggedImagesResponseModel: String

    @Mock
    lateinit var serviceEndpoints: ServiceEndpoints

    @InjectMocks
    lateinit var networkHelper: NetworkHelper

    lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockedResponse = MockResponseFileReader("raw/person_details_response.json").content
        mockedResponseModel = MockResponseFileReader("raw/person_details_response_model.json").content
        mockedImagesResponse = MockResponseFileReader("raw/person_images_response.json").content
        mockedImagesResponseModel = MockResponseFileReader("raw/person_images_response_model.json").content
        mockedTaggedImagesResponse = MockResponseFileReader("raw/person_tagged_images_response.json").content
        mockedTaggedImagesResponseModel = MockResponseFileReader("raw/person_tagged_images_response_model.json").content

        dataRepository = DataRepositoryImpl(serviceEndpoints, networkHelper)
    }

    @Test
    fun `given a mock personResponse verify that mapToDomainModel should return a non null domain model` () {
        val personType: Type = object : TypeToken<PersonResponse?>() {}.type
        val mockedResponse: PersonResponse = Gson().fromJson(mockedResponse, personType)

        val personModel = mockedResponse.mapToDomainModel()

        Assert.assertNotNull(personModel)
    }

    @Test
    fun `given a null personResponse verify that mapToDomainModel should return a null domain model`() {

        val personDetailsResponse: PersonResponse? = null

        val personModel = personDetailsResponse.mapToDomainModel()

        Assert.assertNull(personModel)
    }

    @Test
    fun `given a mock response verify that getPersonDetails should map to domain model as expected`() = runBlocking {
        val personType: Type = object : TypeToken<PersonResponse?>() {}.type
        val mockedResponse: PersonResponse = Gson().fromJson(mockedResponse, personType)

        `when`(serviceEndpoints.getPerson("37625")).thenReturn(Response.success(mockedResponse))

        val response = dataRepository.getPersonDetails("37625")

        verify(serviceEndpoints, times(1)).getPerson("37625")

        Assert.assertNotNull(response)

        val personTypeModel: Type = object : TypeToken<PersonModel?>() {}.type
        val mockedResponseModel: PersonModel = Gson().fromJson(mockedResponseModel, personTypeModel)

        Assert.assertEquals(mockedResponseModel, response)
    }

    @Test(expected = HttpException::class)
    fun `given an error response verify that getPersonDetails should throw HttpException`() = runBlocking {

        `when`(serviceEndpoints.getPerson("37625")).thenReturn(Response.error(401, "{}".toResponseBody()))

        given(dataRepository.getPersonDetails("37625")).willThrow(HttpException(null))

        val response = dataRepository.getPersonDetails("37625")

        Assert.assertNull(response)
    }

    @Test
    fun `given a mock response verify that getPersonImagesDetails should map to domain model as expected`() = runBlocking {
        val personImagesType: Type = object : TypeToken<PersonImagesResponse?>() {}.type
        val mockedResponse: PersonImagesResponse = Gson().fromJson(mockedImagesResponse, personImagesType)

        `when`(serviceEndpoints.getPersonImages("37625")).thenReturn(Response.success(mockedResponse))

        val response = dataRepository.getPersonImagesDetails("37625")

        verify(serviceEndpoints, times(1)).getPersonImages("37625")

        Assert.assertNotNull(response)

        val personImageTypeModel: Type = object : TypeToken<PersonImagesModel?>() {}.type
        val mockedResponseModel: PersonImagesModel = Gson().fromJson(mockedImagesResponseModel, personImageTypeModel)

        Assert.assertEquals(mockedResponseModel, response)
    }

    @Test(expected = HttpException::class)
    fun `given an error response verify that getPersonImagesDetails should throw HttpException`() = runBlocking {

        `when`(serviceEndpoints.getPersonImages("37625")).thenReturn(Response.error(401, "{}".toResponseBody()))

        given(dataRepository.getPersonImagesDetails("37625")).willThrow(HttpException(null))

        val response = dataRepository.getPersonImagesDetails("37625")

        Assert.assertNull(response)
    }

    @Test
    fun `given a mock response verify that getPersonTaggedImagesDetails should map to domain model as expected`() = runBlocking {
        val personTaggedImagesType: Type = object : TypeToken<PersonTaggedImagesResponse?>() {}.type
        val mockedResponse: PersonTaggedImagesResponse = Gson().fromJson(mockedTaggedImagesResponse, personTaggedImagesType)

        `when`(serviceEndpoints.getPersonTaggedImages("37625")).thenReturn(Response.success(mockedResponse))

        val response = dataRepository.getPersonTaggedImagesDetails("37625")

        verify(serviceEndpoints, times(1)).getPersonTaggedImages("37625")

        Assert.assertNotNull(response)

        val personTaggedImageTypeModel: Type = object : TypeToken<PersonTaggedImagesModel?>() {}.type
        val mockedResponseModel: PersonTaggedImagesModel = Gson().fromJson(mockedTaggedImagesResponseModel, personTaggedImageTypeModel)

        Assert.assertEquals(mockedResponseModel, response)
    }

    @Test(expected = HttpException::class)
    fun `given an error response verify that getPersonTaggedImagesDetails should throw HttpException`() = runBlocking {

        `when`(serviceEndpoints.getPersonTaggedImages("37625")).thenReturn(Response.error(401, "{}".toResponseBody()))

        given(dataRepository.getPersonTaggedImagesDetails("37625")).willThrow(HttpException(null))

        val response = dataRepository.getPersonTaggedImagesDetails("37625")

        Assert.assertNull(response)
    }
}