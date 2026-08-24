package com.example.boredapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 1. EL MENÚ: Definimos qué queremos pedirle al servidor
interface BoredApi {
    @GET("random")
    suspend fun obtenerActividadAleatoria(): ActividadRed

    @GET("filter")
    suspend fun filtrarActividades(
        @Query("type") tipo: String,
        @Query("participants") participantes: Int
    ): List<ActividadRed>
}

// 2. EL MOTOR: Configuramos Retrofit para toda la app (Patrón Singleton)
object RetrofitClient {
    private const val BASE_URL = "https://bored-api.appbrewery.com/"

    val api: BoredApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Le enseñamos a leer JSON
            .build()
            .create(BoredApi::class.java)
    }
}