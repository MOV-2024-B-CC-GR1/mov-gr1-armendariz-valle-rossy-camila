package com.example

data class Planeta(
    val nombre: String,
    val tipo: String, // "rocoso" o "gaseoso"
    val distanciaAlSol: Double, // Distancia en millones de km
    val habitabilidad: Boolean, // Si es habitable o no
    val numeroSatelites: Int // Número de satélites
)
