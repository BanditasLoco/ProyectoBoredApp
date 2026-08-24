package com.example.boredapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boredapp.ui.theme.BoredAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoredAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BoredAppApp()
                }
            }
        }
    }
}

@Composable
fun BoredAppApp() {

    // 1. Creamos el motor de navegación
    val navController = rememberNavController()

    //Obtener el contexto actual de la aplicación
    val context = LocalContext.current

    //Construir la base de datos y sacar el DAO
    val database = ActividadDatabase.getDatabase(context)
    val dao = database.actividadDao()

    val api = RetrofitClient.api

    val repositorio = remember { ActividadRepository(dao, api) }

    //Creamos el viewModel usando nuestro factory personalizado para pasarle el DAO
    val actividadViewModel: ActividadViewModel = viewModel(factory = ActividadViewModelFactory(repositorio))

    // 2. Definimos el mapa y decimos que inicie en "catalogo"
    NavHost(navController = navController, startDestination = "catalogo") {

        // --- RUTA 1: El Catálogo ---
        composable("catalogo") {
            PantallaCatalogo(navController = navController, viewModel = actividadViewModel)
        }

        //RUTA 2: ACTIVIDAD ALEATORIA
        composable("aleatoria") {
            PantallaAleatoria(navController = navController, viewModel = actividadViewModel)
        }

        // --- RUTA 3: Ajustes de Usuario ---
        // --- RUTA 2: Ajustes de Usuario ---
        composable("ajustes") {
            PantallaAjustes(navController = navController)
        }

        // --- RUTA 4: Los Detalles (Espera un parámetro llamado {id}) ---
        // --- RUTA 3: Los Detalles (Espera un parámetro llamado {id}) ---
        // --- RUTA 2: Los Detalles (Espera un parámetro llamado {id}) ---
        composable("detalles/{id}") { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")
            val idInt = idString?.toIntOrNull() ?: 0

            PantallaDetalles(
                navController = navController,
                viewModel = actividadViewModel,
                actividadId = idInt
            )
        }
    }
}