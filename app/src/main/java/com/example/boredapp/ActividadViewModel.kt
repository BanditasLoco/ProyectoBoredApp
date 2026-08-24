package com.example.boredapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActividadViewModel(private val repository: ActividadRepository) : ViewModel() {

    val actividades: StateFlow<List<Actividad>> = repository.actividadesLocales
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertarActividad(actividad: Actividad){
        viewModelScope.launch {
            repository.insertarActividadLocal(actividad)
        }
    }

    suspend fun obtenerActividadPorId(id: Int): Actividad? {
        return repository.obtenerActividadLocalPorId(id)
    }
}

class ActividadViewModelFactory(private val repository: ActividadRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActividadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActividadViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}