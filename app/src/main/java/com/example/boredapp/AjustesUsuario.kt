package com.example.boredapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "ajustes_boredapp")

class AjustesUsuario(private val context: Context) {

    companion object {
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    }

    suspend fun guardarNombre(nombre: String) {
        context.dataStore.edit { preferencias ->
            preferencias[NOMBRE_USUARIO] = nombre
        }
    }

    val nombreUsuarioFlow: Flow<String> = context.dataStore.data.map { preferencias ->
        preferencias[NOMBRE_USUARIO] ?: "Invitado"
    }
}