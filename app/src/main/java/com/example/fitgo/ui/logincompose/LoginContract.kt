package com.example.apolloproject.ui.screens.logincompose

interface LoginContract {
    sealed class Event(){
        object login: Event()
        object register: Event()
        class CambiarLoginSuccess(val flag:Boolean):Event()
        class CambiarRegisterModeSuccess(val flag:Boolean):Event()
        class CambiarEmailState(val email: String):Event()
        class CambiarUsernameState(val name: String):Event()
        class CambiarPasswState(val passw: String):Event()
        class CambiarCodeState(val coachCode:String):Event()
        class CambiarAgeState(val age: Int ):Event()
        class CambiarWeightState(val weight: Int ):Event()
        class CambiarSexState(val sex: String ):Event()
        class CambiarExpanded(val expanded: Boolean):Event()




    }
    data class State(

        val email:String?=null,
        val password:String?=null,
        val username:String?=null,
        val age:Int=0,
        val weight:Int=0,
        val sex: String="Hombre",

        val code:String?=null,
        val message:String?=null,
        val isLoading:Boolean=false,
        val loginsucces:Boolean=false,
        val registersuccess:Boolean=false,
        val registerMode:Boolean=false,
val expanded:Boolean=false
        )


}