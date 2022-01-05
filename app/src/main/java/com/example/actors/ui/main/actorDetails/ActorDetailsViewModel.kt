package com.example.actors.ui.main.actorDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actors.ui.main.actorDetails.state.ActorsDetailsState
import com.example.actors.ui.main.actorDetails.state.ActorsImagesState
import com.example.actors.ui.main.actorDetails.state.ActorsTaggedImagesState
import com.example.data.businessmodel.PersonImagesModel
import com.example.data.businessmodel.PersonModel
import com.example.data.businessmodel.PersonTaggedImagesModel
import com.example.data.repository.DataRepository
import com.example.network.helpers.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val isForTesting: Boolean = false,
    private val dataRepository: DataRepository
) : ViewModel() {

    val personDetailsState: MutableLiveData<ActorsDetailsState> = MutableLiveData()
    val personImagesState: MutableLiveData<ActorsImagesState> = MutableLiveData()
    val personTaggedImagesState: MutableLiveData<ActorsTaggedImagesState> = MutableLiveData()

    init {
        if (!isForTesting) {
            getPersonDetails("37625")
        }
    }

    fun getPersonDetailsState(): LiveData<ActorsDetailsState> = personDetailsState
    fun getPersonImagesState(): LiveData<ActorsImagesState> = personImagesState
    fun getPersonTaggedImagesState(): LiveData<ActorsTaggedImagesState> = personTaggedImagesState

    fun getPersonDetails(id: String) = viewModelScope.launch {

        supervisorScope {
            personDetailsState.value = ActorsDetailsState.Loading

            val personDetailsResponse = async { dataRepository.getPerson(id) }
            val personImagesResponse = async { dataRepository.getPersonImages(id) }
            val personTaggedImagesResponse = async { dataRepository.getPersonTaggedImages(id) }

            when (personDetailsResponse.await()) {
                is ResultWrapper.NetworkError -> personDetailsState.value = ActorsDetailsState.Error()
                is ResultWrapper.GenericError ->  personDetailsState.value = ActorsDetailsState.Error(
                    (personDetailsResponse.await() as ResultWrapper.GenericError).code,
                    (personDetailsResponse.await() as ResultWrapper.GenericError).error
                )
                is ResultWrapper.Success -> personDetailsState.value = ActorsDetailsState.Success(
                    (personDetailsResponse.await() as ResultWrapper.Success<PersonModel?>).value)
            }

            when (personImagesResponse.await()) {
                is ResultWrapper.NetworkError -> personImagesState.value = ActorsImagesState.Error()
                is ResultWrapper.GenericError -> personImagesState.value = ActorsImagesState.Error(
                    (personImagesResponse.await() as ResultWrapper.GenericError).code,
                    (personImagesResponse.await() as ResultWrapper.GenericError).error
                )
                is ResultWrapper.Success -> personImagesState.value = ActorsImagesState.Success(
                    (personImagesResponse.await() as ResultWrapper.Success<PersonImagesModel?>).value)
            }

            when (personTaggedImagesResponse.await()) {
                is ResultWrapper.NetworkError -> personTaggedImagesState.value = ActorsTaggedImagesState.Error()
                is ResultWrapper.GenericError ->  personTaggedImagesState.value = ActorsTaggedImagesState.Error(
                    (personTaggedImagesResponse.await() as ResultWrapper.GenericError).code,
                    (personTaggedImagesResponse.await() as ResultWrapper.GenericError).error
                )
                is ResultWrapper.Success -> personTaggedImagesState.value = ActorsTaggedImagesState.Success(
                    (personTaggedImagesResponse.await() as ResultWrapper.Success<PersonTaggedImagesModel?>).value)
            }
        }
    }
}