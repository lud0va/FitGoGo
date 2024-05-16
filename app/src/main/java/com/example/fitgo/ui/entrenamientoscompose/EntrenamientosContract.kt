package com.example.fitgo.ui.entrenamientoscompose

import com.example.fitgo.domain.model.Entrenamientos

interface EntrenamientosContract {
    sealed class Event(){
        object getEntrenamientos:Event()

    }
    data class State(
        val entrenamientos:List<Entrenamientos> = emptyList(),
        val  message:String?=null,
        val isLoading:Boolean=false,
    )
}