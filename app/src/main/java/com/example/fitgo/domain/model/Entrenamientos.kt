package com.example.fitgo.domain.model

data class Entrenamientos(
    val id:Int,
    val diaSemana:String,
    val coachee:Coachee,
    val ejercicios:List<Ejercicios> = emptyList(),
)