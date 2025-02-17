package ru.sicampus.bootcamp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp.data.auth.AuthStorageDataSource
import ru.sicampus.bootcamp.data.auth.login
import ru.sicampus.bootcamp.data.centers.CentersNetworkDataSource
import ru.sicampus.bootcamp.data.list.CentersRepoImpl
import ru.sicampus.bootcamp.domain.Centers.CenterGetDataUseCase
import ru.sicampus.bootcamp.domain.list.CentersEntity

class VolunteerCenterViewModel(
    private val centerGetDataUseCase: CenterGetDataUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        updateState()
    }

    fun clickRefresh() {
        updateState()
    }

    private fun updateState() {
        viewModelScope.launch {
            _state.emit(State.Loading)
            _state.emit(
                centerGetDataUseCase.invoke().fold(
                    onSuccess = { data ->
                        State.Show(data)
                    },
                    onFailure = { error ->
                        State.Error(error.message.toString())
                    }
                )
            )
        }
    }

    sealed interface State {
        data object Loading : State
        data class Show(
            val items: CentersEntity
        ) : State

        data class Error(
            val text: String
        ) : State
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VolunteerCenterViewModel(
                    centerGetDataUseCase = CenterGetDataUseCase(
                        centersRepoImpl = CentersRepoImpl(
                            centersNetworkDataSource = CentersNetworkDataSource(),
                            authStorageDataSource = AuthStorageDataSource
                        ),
                        name = login.centerAdress
                    )
                ) as T
            }
        }
    }
}
