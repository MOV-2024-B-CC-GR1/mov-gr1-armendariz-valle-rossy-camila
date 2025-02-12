package com.example

import javafx.scene.control.*
import javafx.scene.layout.GridPane

class PlanetaDialog(planeta: Planeta?) : Dialog<Planeta>() {
    init {
        title = if (planeta == null) "Agregar Planeta" else "Editar Planeta"

        val grid = GridPane().apply {
            hgap = 10.0
            vgap = 10.0
        }

        val nombreField = TextField(planeta?.nombre)
        val tipoCombo = ComboBox<String>().apply {
            items.addAll("Rocoso", "Gaseoso")
            value = planeta?.tipo ?: "Rocoso"
        }
        val distanciaField = TextField(planeta?.distanciaAlSol?.toString())
        val habitabilidadCheck = CheckBox().apply {
            isSelected = planeta?.habitabilidad ?: false
        }
        val satelitesField = TextField(planeta?.numeroSatelites?.toString())

        grid.add(Label("Nombre:"), 0, 0)
        grid.add(nombreField, 1, 0)
        grid.add(Label("Tipo:"), 0, 1)
        grid.add(tipoCombo, 1, 1)
        grid.add(Label("Distancia al Sol (millones de km):"), 0, 2)
        grid.add(distanciaField, 1, 2)
        grid.add(Label("Habitabilidad:"), 0, 3)
        grid.add(habitabilidadCheck, 1, 3)
        grid.add(Label("Número de Satélites:"), 0, 4)
        grid.add(satelitesField, 1, 4)

        dialogPane.content = grid
        dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)

        setResultConverter { button ->
            if (button == ButtonType.OK) {
                Planeta(
                    nombreField.text,
                    tipoCombo.value,
                    distanciaField.text.toDouble(),
                    habitabilidadCheck.isSelected,
                    satelitesField.text.toInt()
                )
            } else null
        }
    }
}
