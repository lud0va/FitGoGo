package com.example.fitgo.ui.main

import com.example.fitgo.domain.model.Entrenamientos

interface MainContract {
    sealed class Event(){


        class getEntrenamiento(val dia:String):Event()
        class cargarUsername():Event()
    }
    data class State(

     val username:String?=null,
     val  message:String?=null,
     val isLoading:Boolean=false,

     val entrenamiento:Entrenamientos?=null

    )
}