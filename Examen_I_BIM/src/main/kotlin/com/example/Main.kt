package com.example

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage

class SistemaSolarApp : Application() {
    private val sistemaSolar = SistemaSolar.cargarDesdeArchivo()

    override fun start(primaryStage: Stage) {
        // Botones del menú
        val agregarPlanetaButton = Button("Agregar Planeta")
        val editarPlanetaButton = Button("Editar Planeta")
        val eliminarPlanetaButton = Button("Eliminar Planeta")
        val listarPlanetasButton = Button("Listar Planetas")
        val infoSistemaSolarButton = Button("Ver Info Sistema Solar")

        // Área de texto para mostrar información
        val outputArea = TextArea()
        outputArea.isEditable = false

        // Configuración de eventos de los botones
        agregarPlanetaButton.setOnAction {
            val dialog = PlanetaDialog(planeta = null) // Formulario vacío para agregar
            val nuevoPlaneta = dialog.showAndWait()
            nuevoPlaneta.ifPresent { planeta ->
                sistemaSolar.agregarPlaneta(planeta)
                outputArea.text = "Planeta agregado: ${planeta.nombre}"
            }
        }

        editarPlanetaButton.setOnAction {
            val dialog = TextInputDialog()
            dialog.title = "Editar Planeta"
            dialog.headerText = "Ingrese el nombre del planeta a editar:"
            dialog.contentText = "Nombre del planeta:"
            val resultado = dialog.showAndWait()
            resultado.ifPresent { nombre ->
                val planetaActual = sistemaSolar.listarPlanetas().find { it.nombre == nombre }
                if (planetaActual != null) {
                    val editDialog = PlanetaDialog(planeta = planetaActual)
                    val planetaEditado = editDialog.showAndWait()
                    planetaEditado.ifPresent { nuevoPlaneta ->
                        sistemaSolar.editarPlaneta(nombre, nuevoPlaneta)
                        outputArea.text = "Planeta editado: ${nuevoPlaneta.nombre}"
                    }
                } else {
                    outputArea.text = "El planeta '$nombre' no existe."
                }
            }
        }

        eliminarPlanetaButton.setOnAction {
            val dialog = TextInputDialog()
            dialog.title = "Eliminar Planeta"
            dialog.headerText = "Ingrese el nombre del planeta a eliminar:"
            dialog.contentText = "Nombre del planeta:"
            val resultado = dialog.showAndWait()
            resultado.ifPresent { nombre ->
                if (sistemaSolar.eliminarPlaneta(nombre)) {
                    outputArea.text = "Planeta eliminado: $nombre"
                } else {
                    outputArea.text = "El planeta '$nombre' no existe."
                }
            }
        }

        listarPlanetasButton.setOnAction {
            val planetas = sistemaSolar.listarPlanetas()
            if (planetas.isEmpty()) {
                outputArea.text = "No hay planetas registrados en el sistema solar."
            } else {
                val listado = planetas.joinToString("\n") { planeta ->
                    "- ${planeta.nombre} (${planeta.tipo}), Distancia al Sol: ${planeta.distanciaAlSol} AU, " +
                            "Habitabilidad: ${if (planeta.habitabilidad) "Sí" else "No"}, Satélites: ${planeta.numeroSatelites}"
                }
                outputArea.text = "Lista de Planetas:\n$listado"
            }
        }

        infoSistemaSolarButton.setOnAction {
            outputArea.text = sistemaSolar.obtenerInformacionSistemaSolar()
        }

        // Diseño de la ventana
        val buttonBox = VBox(10.0, agregarPlanetaButton, editarPlanetaButton, eliminarPlanetaButton, listarPlanetasButton, infoSistemaSolarButton)
        val root = VBox(10.0, buttonBox, outputArea)

        // Configuración de la escena y la ventana principal
        val scene = Scene(root, 600.0, 400.0)
        primaryStage.title = "Sistema Solar"
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(SistemaSolarApp::class.java)
}
