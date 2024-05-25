package com.example.fitgo.ui.entrenamientoscompose.entrenamientosdetalles


import com.example.fitgo.domain.model.Ejercicios
import com.example.fitgo.domain.model.Entrenamientos

interface EntrDetalleContract {
    sealed class Event(){
        class GetEjercicios(val entrenamientoId:Int):Event()
        class GetEntrenamiento(val entrenamientoId:Int):Event()


    }
    data class State(
        val entrenamiento: Entrenamientos?=null,
        val ejercicios: List<Ejercicios> = emptyList(),
        val  message:String?=null,
        val dia:String?=null,
        val isLoading:Boolean=false,
    )


}
