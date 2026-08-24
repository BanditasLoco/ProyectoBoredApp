package com.example.boredapp

import kotlinx.coroutines.flow.Flow

// El repositorio pide el DAO (Room) en su constructor
class ActividadRepository(
    private val actividadDao: ActividadDao
) {

    // --- OPERACIONES LOCALES (ROOM) ---

    // Obtenemos el Flow de la base de datos
    val actividadesLocales: Flow<List<Actividad>> = actividadDao.obtenerActividades()

    suspend fun insertarActividadLocal(actividad: Actividad) {
        actividadDao.insertarActividad(actividad)
    }

    suspend fun obtenerActividadLocalPorId(id: Int): Actividad? {
        return actividadDao.obtenerActividadPorId(id)
    }
}