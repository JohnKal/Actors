package com.example.actors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.actors.ui.main.actorDetails.ActorDetailsViewModel
import com.example.actors.ui.main.actorDetails.state.ActorsDetailsState
import com.example.actors.ui.main.actorDetails.state.ActorsImagesState
import com.example.actors.ui.main.actorDetails.state.ActorsTaggedImagesState
import com.example.data.businessmodel.*
import com.example.data.repository.DataRepositoryImpl
import com.example.network.helpers.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule

@ExtendWith(MockKExtension::class)
@SmallTest
class ViewModelsTest {

    private lateinit var viewModel: ActorDetailsViewModel
    val dispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var dataRepository: DataRepositoryImpl

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)

        viewModel = ActorDetailsViewModel(true, dataRepository)

        coEvery {
            runBlocking {
                dataRepository.getPerson("37625")
            }
        } returns ResultWrapper.GenericError()

        coEvery {
            runBlocking {
                dataRepository.getPersonImages("37625")
            }
        } returns ResultWrapper.GenericError()

        coEvery {
            runBlocking {
                dataRepository.getPersonTaggedImages("37625")
            }
        } returns ResultWrapper.GenericError()
    }

    @Test
    fun `given Success from data repository verify that getPersonDetails should change ActorsDetailsState to Success`()  {

        val mockedPersonModel = PersonModel(
            "1983-08-20",
            "Los Angeles, California, USA",
            "Acting",
            null,
            "Andrew Garfield"
        )

        coEvery {
            runBlocking {
                dataRepository.getPerson("37625")
            }
        } returns ResultWrapper.Success(mockedPersonModel)

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPerson("37625") } }

        Assert.assertEquals(viewModel.getPersonDetailsState().value, ActorsDetailsState.Success(mockedPersonModel))
    }

    @Test
    fun `given Success from data repository verify that getPersonDetails should change ActorsImagesState to Success`()  {

        val mockedPersonImagesModel = PersonImagesModel(
            37625L,
            arrayListOf(ProfileModel("/h0A3pzHaNTVRD7xNfabuoyadJba.jpg"))
        )

        coEvery {
            runBlocking {
                dataRepository.getPersonImages("37625")
            }
        } returns ResultWrapper.Success(mockedPersonImagesModel)

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPersonImages("37625") } }

        Assert.assertEquals(viewModel.getPersonImagesState().value, ActorsImagesState.Success(mockedPersonImagesModel))
    }

    @Test
    fun `given Success from data repository verify that getPersonDetails should change ActorsTaggedImagesState to Success`()  {

        val mockedPersonTaggedImagesModel = PersonTaggedImagesModel(
            arrayListOf(ResultModel("/sVYgFC6z0RZJrgUpve4XlyrVrgr.jpg"))
        )

        coEvery {
            runBlocking {
                dataRepository.getPersonTaggedImages("37625")
            }
        } returns ResultWrapper.Success(mockedPersonTaggedImagesModel)

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPersonTaggedImages("37625") } }

        Assert.assertEquals(viewModel.getPersonTaggedImagesState().value, ActorsTaggedImagesState.Success(mockedPersonTaggedImagesModel))
    }

    @Test
    fun `given GenericError from data repository verify that getPersonDetails should change ActorsDetailsState to Error`()  {

        val genericError = ResultWrapper.GenericError(401, null)

        coEvery {
            runBlocking {
                dataRepository.getPerson("37625")
            }
        } returns genericError

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPerson("37625") } }

        Assert.assertEquals(viewModel.getPersonDetailsState().value, ActorsDetailsState.Error(genericError.code, genericError.error))
    }

    @Test
    fun `given NetworkError from data repository verify that getPersonDetails should change ActorsDetailsState to Error`()  {

        val networkError = ResultWrapper.NetworkError

        coEvery {
            runBlocking {
                dataRepository.getPerson("37625")
            }
        } returns networkError

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPerson("37625") } }

        Assert.assertEquals(viewModel.getPersonDetailsState().value, ActorsDetailsState.Error())
    }

    @Test
    fun `given GenericError from data repository verify that getPersonDetails should change ActorsImagesState to Error`()  {

        val genericError = ResultWrapper.GenericError(401, null)

        coEvery {
            runBlocking {
                dataRepository.getPersonImages("37625")
            }
        } returns genericError

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPersonImages("37625") } }

        Assert.assertEquals(viewModel.getPersonImagesState().value, ActorsImagesState.Error(genericError.code, genericError.error))
    }

    @Test
    fun `given NetworkError from data repository verify that getPersonDetails should change ActorsImagesState to Error`()  {

        val networkError = ResultWrapper.NetworkError

        coEvery {
            runBlocking {
                dataRepository.getPersonImages("37625")
            }
        } returns networkError

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPersonImages("37625") } }

        Assert.assertEquals(viewModel.getPersonImagesState().value, ActorsImagesState.Error())
    }

    @Test
    fun `given GenericError from data repository verify that getPersonDetails should change ActorsTaggedImagesState to Error`()  {

        val genericError = ResultWrapper.GenericError(401, null)

        coEvery {
            runBlocking {
                dataRepository.getPersonTaggedImages("37625")
            }
        } returns genericError

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPersonTaggedImages("37625") } }

        Assert.assertEquals(viewModel.getPersonTaggedImagesState().value, ActorsTaggedImagesState.Error(genericError.code, genericError.error))
    }

    @Test
    fun `given NetworkError from data repository verify that getPersonDetails should change ActorsTaggedImagesState to Error`()  {

        val networkError = ResultWrapper.NetworkError

        coEvery {
            runBlocking {
                dataRepository.getPersonTaggedImages("37625")
            }
        } returns networkError

        viewModel.getPersonDetails("37625")

        verify(exactly = 1) { runBlocking { dataRepository.getPersonTaggedImages("37625") } }

        Assert.assertEquals(viewModel.getPersonTaggedImagesState().value, ActorsTaggedImagesState.Error())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}