package com.example.proyecto_ruleta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Roulettes.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_ROULETTES = "roulettes"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_OPTIONS = "options"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_ROULETTES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_OPTIONS TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ROULETTES")
        onCreate(db)
    }

    // Guardar una nueva ruleta en la base de datos
    fun saveRoulette(name: String, options: List<String>): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_OPTIONS, options.joinToString(",")) // Guardamos las opciones como una cadena separada por comas
        }
        return db.insert(TABLE_ROULETTES, null, values)
    }

    // Obtener todas las ruletas guardadas
    fun getAllRoulettes(): List<Pair<Int, String>> {
        val db = readableDatabase
        val roulettes = mutableListOf<Pair<Int, String>>()
        val cursor = db.rawQuery("SELECT $COLUMN_ID, $COLUMN_NAME FROM $TABLE_ROULETTES", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            roulettes.add(Pair(id, name))
        }

        cursor.close()
        return roulettes
    }

    // Obtener las opciones de una ruleta por su ID
    fun getRouletteOptions(id: Int): List<String>? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_OPTIONS FROM $TABLE_ROULETTES WHERE $COLUMN_ID = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            cursor.getString(0).split(",") // Convertimos la cadena de nuevo a una lista
        } else {
            null
        }.also { cursor.close() }
    }
}
