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

    //Obtener el contexto actual de la aplicación
    val context = LocalContext.current

    //Construir la base de datos y sacar el DAO
    val database = ActividadDatabase.getDatabase(context)
    val dao = database.actividadDao()

    val repositorio = remember { ActividadRepository(dao) }

    //Creamos el viewModel usando nuestro factory personalizado para pasarle el DAO
    val actividadViewModel: ActividadViewModel = viewModel(factory = ActividadViewModelFactory(repositorio))

    PantallaCatalogo(viewModel = actividadViewModel)
}