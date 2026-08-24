package com.example.boredapp

import com.google.gson.annotations.SerializedName

// La Bored API manda cada actividad como un objeto plano (sin envoltorio como TMDB o Dog API)
data class ActividadRed(
    @SerializedName("activity") val actividad: String,
    @SerializedName("type") val tipo: String,
    @SerializedName("participants") val participantes: Int,
    @SerializedName("price") val precio: Double,
    @SerializedName("accessibility") val accesibilidad: String,
    @SerializedName("duration") val duracion: String,
    @SerializedName("kidFriendly") val aptaParaNinos: Boolean,
    @SerializedName("link") val link: String,
    @SerializedName("key") val key: String
)