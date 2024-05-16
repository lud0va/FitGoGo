package com.example.fitgo.ui.entrenamientoscompose

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgo.domain.usecases.entrenamientos.GetEntrenamientosUseCase
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EntrenamientosViewModel  @Inject constructor(
    private val getEntrenamientosUseCase: GetEntrenamientosUseCase,
    val sharedPreferences: SharedPreferences

): ViewModel(){
    private val _uistate= MutableStateFlow((EntrenamientosContract.State()))
    val state:StateFlow<EntrenamientosContract.State> = _uistate.asStateFlow()

    fun event(event: EntrenamientosContract.Event) {
        when (event) {
            EntrenamientosContract.Event.getEntrenamientos -> cargarLista()

        }
    }
    fun cargarLista(){

        viewModelScope.launch {

            sharedPreferences.getString("userEmail", null)?.let {
                getEntrenamientosUseCase.invoke(it)
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uistate.update {
                                    it.copy(
                                        message = result.message,

                                        )
                                }

                            }

                            is NetworkResult.Loading -> _uistate.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uistate.update {
                                it.copy(
                                    entrenamientos = result.data
                                        ?: emptyList(), isLoading = false
                                )
                            }

                        }


                    }
            }

        }
    }
}