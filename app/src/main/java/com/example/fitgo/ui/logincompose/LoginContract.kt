package com.example.apolloproject.ui.screens.logincompose

interface LoginContract {
    sealed class Event(){
        object login: Event()
        object register: Event()
        class CambiarLoginSuccess(val flag:Boolean):Event()
        class CambiarRegisterModeSuccess(val flag:Boolean):Event()
        class CambiarUserState(val username: String):Event()
        class CambiarNameState(val name: String):Event()
        class CambiarLastNameState(val lastname: String):Event()
        class CambiarPasswState(val passw: String):Event()
        class CambiarCodeState(val coachCode:String):Event()



    }
    data class State(

        val email:String?=null,
        val password:String?=null,
        val name:String?=null,
        val code:String?=null,
        val lastname:String?=null,
        val message:String?=null,
        val isLoading:Boolean=false,
        val loginsucces:Boolean=false,
        val registersuccess:Boolean=false,
        val registerMode:Boolean=false

        )


}