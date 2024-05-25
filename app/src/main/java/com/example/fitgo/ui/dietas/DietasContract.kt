package com.example.fitgo.ui.dietas

import com.example.fitgo.domain.model.Dieta
import com.example.fitgo.domain.model.Entrenamientos

interface DietasContract {
    sealed class Event(){
        object getDietas:Event()

    }
    data class State(
        val dietas:List<Dieta> = emptyList(),
        val  message:String?=null,
        val isLoading:Boolean=false,
    )
}