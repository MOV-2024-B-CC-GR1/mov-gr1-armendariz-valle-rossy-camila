package org.example

fun main() {
    println("Hello World!")

    // INMUTABLE
    val inmutable: String = "Adrian";
    inmutable = "Vicente"

    // MUTABLE
    var mutable: String = "Vicente"
    mutable = "Adrian"

    // Duck Typing
    val ejemploVarible = "Rossy Armendariz"
    ejemploVarible.trim()

    val edadEjemplo: Int = 12

    // Clases en Java
    val fechaNacimiento: Date = Date()

    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
    }

    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito

    fun imprimirNombre(nombre:String): Unit{
        fun otraFuncionAdentro(){
            println("Otra funcion adentro")
        }
        println("Nombre: ${nombre}") // Template Strings
    }
}