package com.example.boredapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarActividad(actividad: Actividad)

    @Query("SELECT * FROM actividades")
    fun obtenerActividades(): Flow<List<Actividad>>

    @Query("SELECT * FROM actividades WHERE id = :idBuscado")
    suspend fun obtenerActividadPorId(idBuscado: Int): Actividad?

    @Query("DELETE FROM actividades")
    suspend fun borrarTodas()
}