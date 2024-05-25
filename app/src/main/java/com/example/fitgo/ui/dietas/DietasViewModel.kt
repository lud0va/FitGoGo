package com.example.fitgo.ui.dietas

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgo.domain.usecases.dieta.GetDietaByEmailUseCase
import com.example.fitgo.ui.entrenamientoscompose.EntrenamientosContract
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietasViewModel @Inject constructor(
    private val getDietaByEmailUseCase: GetDietaByEmailUseCase,
    val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _uistate = MutableStateFlow((DietasContract.State()))
    val state: StateFlow<DietasContract.State> = _uistate.asStateFlow()

    fun event(event: DietasContract.Event) {
        when (event) {
            DietasContract.Event.getDietas -> cargarLista()

        }
    }

    fun cargarLista() {
        viewModelScope.launch {
            sharedPreferences.getString("userEmail", null)?.let {
                getDietaByEmailUseCase.invoke(it).collect { result ->
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
                                dietas = result.data
                                    ?: emptyList(), isLoading = false
                            )
                        }

                    }


                }
            }

        }
    }

}