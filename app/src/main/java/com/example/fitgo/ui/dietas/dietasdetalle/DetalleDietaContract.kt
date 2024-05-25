package com.example.fitgo.ui.dietas.dietasdetalle

import com.example.fitgo.domain.model.AlimentosPermitidos
import com.example.fitgo.domain.model.Dieta
import com.example.fitgo.domain.model.Ejercicios
import com.example.fitgo.domain.model.Entrenamientos
import com.example.fitgo.domain.model.Plato

interface DetalleDietaContract {
    sealed class Event(){
        class GetDieta(val dietaId:Int):Event()
        class GetPlatos(val dietaId:Int):Event()
        class GetAlimentosPermitidos(val dietaId:Int):Event()


    }
    data class State(
        val dieta: Dieta?=null,
        val platos: List<Plato> = emptyList(),
        val alimentosPermitidos: List<AlimentosPermitidos> = emptyList(),

        val  message:String?=null,

        val isLoading:Boolean=false,
    )
}