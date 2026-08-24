package com.example.boredapp

import kotlinx.coroutines.flow.Flow

// El repositorio pide el DAO (Room) y el API (Retrofit) en su constructor
class ActividadRepository(
    private val actividadDao: ActividadDao,
    private val boredApi: BoredApi
) {

    // --- 1. OPERACIONES LOCALES (ROOM) ---

    // Obtenemos el Flow de la base de datos
    val actividadesLocales: Flow<List<Actividad>> = actividadDao.obtenerActividades()

    suspend fun insertarActividadLocal(actividad: Actividad) {
        actividadDao.insertarActividad(actividad)
    }

    suspend fun obtenerActividadLocalPorId(id: Int): Actividad? {
        return actividadDao.obtenerActividadPorId(id)
    }

    // --- 2. OPERACIONES DE RED (RETROFIT) ---

    suspend fun obtenerActividadAleatoriaDelMundo(): ActividadRed? {
        return try {
            boredApi.obtenerActividadAleatoria()
        } catch (e: Exception) {
            android.util.Log.e("BoredApp", "Error en Repositorio: ${e.message}")
            null // Si hay error de internet, devolvemos null para que no explote la app
        }
    }
}