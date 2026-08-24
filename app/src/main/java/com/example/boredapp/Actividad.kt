package com.example.boredapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actividades")
data class Actividad(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val actividad: String,
    val tipo: String,
    val participantes: Int,
    val precio: Double,
    val duracion: String,
    val accesibilidad: String,
    val aptaParaNinos: Boolean
)