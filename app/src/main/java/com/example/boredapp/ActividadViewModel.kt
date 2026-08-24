package com.example.boredapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
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

    // Estado para almacenar la actividad aleatoria descargada de la API
    private val _actividadAleatoria = MutableStateFlow<ActividadRed?>(null)
    val actividadAleatoria: StateFlow<ActividadRed?> = _actividadAleatoria

    fun descargarActividadAleatoria(){
        viewModelScope.launch {
            _actividadAleatoria.value = null // Mostramos la ruedita de carga mientras baja la nueva actividad
            _actividadAleatoria.value = repository.obtenerActividadAleatoriaDelMundo()
        }
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