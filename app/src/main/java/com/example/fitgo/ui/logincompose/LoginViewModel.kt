package com.example.apolloproject.ui.screens.logincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitgo.R


import com.example.fitgo.domain.usecases.user.LoginUseCase
import com.example.fitgo.domain.usecases.user.RegisterUseCase
import com.example.utils.NetworkResult
import com.example.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,



    ) : ViewModel() {
    private val _uiState= MutableStateFlow(LoginContract.State())
    val uiState: StateFlow<LoginContract.State> = _uiState.asStateFlow()



    fun event(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.CambiarCodeState->cambiarCodeCoach(event.coachCode)
            is LoginContract.Event.CambiarPasswState -> cambiarPasswState(event.passw)
            is LoginContract.Event.CambiarUserState -> cambiarUserState(event.username)
            is LoginContract.Event.CambiarNameState->cambiarName(event.name)
            is LoginContract.Event.CambiarLastNameState-> cambiarLastName(event.lastname)
            is LoginContract.Event.CambiarRegisterModeSuccess->cambiarRegisterModeSuccess(event.flag)
            LoginContract.Event.login -> doLogin()
            LoginContract.Event.register -> doRegister()
            is LoginContract.Event.CambiarLoginSuccess -> cambiarLoginSuccess(event.flag)
            else -> {}
        }
    }
    fun cambiarCodeCoach(codeCoach:String){
        _uiState.update {
            it.copy(code=codeCoach)
        }
    }

    fun cambiarRegisterModeSuccess(boolean: Boolean){
        _uiState.update {
            it.copy(registerMode = boolean)
        }
    }
    fun cambiarLoginSuccess(boolean: Boolean){
        _uiState.update {
            it.copy(loginsucces = boolean)
        }
    }

    fun cambiarName(name:String){
        _uiState.update {
            it.copy(email = name)
        }
    }
    fun cambiarLastName(lastName:String){
  _uiState.update {
     it.copy(email = lastName)
                     }
    }
    fun cambiarUserState(user: String) {
        _uiState.update {
            it.copy(email = user)
        }
    }

    fun cambiarPasswState(passw: String) {
        _uiState.update {
            it.copy(password = passw)
        }

    }

    fun doLogin() {
        viewModelScope.launch {
            if (!uiState.value.email.isNullOrBlank()|| !uiState.value.password.isNullOrBlank()){
                uiState.value.email?.let {
                    uiState.value.password?.let { it1 ->
                        loginUseCase.invoke(
                            it,
                            it1
                        ).catch (action = {
                            cause ->
                            _uiState.update {
                                it.copy(
                                    message=cause.message

                                )
                            }
                        })

                        .collect{
                                result->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _uiState.update {
                                        it.copy(
                                            message = result.message,

                                            )
                                    }
                                }

                                is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true ) }

                                is NetworkResult.Success ->_uiState.update { it.copy(loginsucces = true, message = stringProvider.getString(R.string.loginCompl)) }

                            }
                        }
                    }
                }
            } else {
                _uiState.update {
                    it.copy(message = stringProvider.getString(R.string.introducirNamePasw))
                }
            }
        }


    }

   fun doRegister() {
 viewModelScope.launch {
            if (!uiState.value.email.isNullOrBlank() || !uiState.value.password.isNullOrBlank()) {



                uiState.value.email?.let {

                    uiState.value.password?.let { it1->
                        uiState.value.name?.let {it2->
                            uiState.value.code?.let {it3->
                                registerUseCase.userRepository.doRegister(
                                    it,
                                    it1 ,
                                    it2,
                                    it3
                                ).collect { result ->
                                    when (result) {
                                        is NetworkResult.Error -> {
                                            _uiState.update {
                                                it.copy(
                                                    message = result.message,

                                                    )
                                            }
                                        }

                                        is NetworkResult.Loading -> _uiState.update { it.copy() }
                                        is NetworkResult.Success -> _uiState.update { it.copy(message = stringProvider.getString(R.string.registroCompl), registersuccess = true) }

                                    }
                                }


                            }
                            }

                }
                }
            }else{
                _uiState.update { it.copy(message = stringProvider.getString(R.string.usuarInv), registersuccess = true) }

            }

        }

    }

}