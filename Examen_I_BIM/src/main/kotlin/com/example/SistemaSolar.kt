package com.example

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.time.LocalDate

data class SistemaSolar(
    val nombre: String = "Sistema Solar",
    val galaxia: String = "Vía Láctea",
    val edad: Double = 4.6,
    val habitabilidad: Boolean = true,
    val fechaDescubrimiento: LocalDate = LocalDate.of(1543, 5, 24),
    val planetas: MutableList<Planeta> = mutableListOf()
) {
    companion object {
        private const val FILE_PATH = "data/sistema_solar.json"

        fun cargarDesdeArchivo(): SistemaSolar {
            val archivo = File(FILE_PATH)
            return if (archivo.exists()) {
                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
                    .create()
                val tipo = object : TypeToken<SistemaSolar>() {}.type
                gson.fromJson(archivo.readText(), tipo)
            } else {
                SistemaSolar()
            }
        }
    }

    fun guardarEnArchivo() {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
        val archivo = File(FILE_PATH)
        archivo.parentFile.mkdirs()
        archivo.writeText(gson.toJson(this))
    }

    fun agregarPlaneta(planeta: Planeta) {
        planetas.add(planeta)
        guardarEnArchivo()
    }

    fun editarPlaneta(nombre: String, nuevoPlaneta: Planeta): Boolean {
        val indice = planetas.indexOfFirst { it.nombre == nombre }
        return if (indice != -1) {
            planetas[indice] = nuevoPlaneta
            guardarEnArchivo()
            true
        } else {
            false
        }
    }

    fun eliminarPlaneta(nombre: String): Boolean {
        val eliminado = planetas.removeIf { it.nombre == nombre }
        if (eliminado) guardarEnArchivo()
        return eliminado
    }

    fun listarPlanetas(): List<Planeta> {
        return planetas
    }

    fun obtenerInformacionSistemaSolar(): String {
        val info = StringBuilder()
        info.append("Información del Sistema Solar:\n")
        info.append("Nombre: $nombre\n")
        info.append("Galaxia: $galaxia\n")
        info.append("Edad: $edad miles de millones de años\n")
        info.append("Habitabilidad: ${if (habitabilidad) "Sí" else "No"}\n")
        info.append("Fecha de Descubrimiento: $fechaDescubrimiento\n")
        info.append("Planetas:\n")
        planetas.forEach { planeta ->
            info.append("- ${planeta.nombre} (${planeta.tipo}), Distancia al Sol: ${planeta.distanciaAlSol} AU, ")
            info.append("Habitabilidad: ${if (planeta.habitabilidad) "Sí" else "No"}, Satélites: ${planeta.numeroSatelites}\n")
        }
        return info.toString()
    }
}
