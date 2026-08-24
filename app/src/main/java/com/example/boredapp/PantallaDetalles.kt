package com.example.boredapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaDetalles(navController: NavController, viewModel: ActividadViewModel, actividadId: Int) {

    // Creamos un estado mutable para almacenar la actividad obtenida del ViewModel
    var actividadState by remember { mutableStateOf<Actividad?>(null) }

    LaunchedEffect(actividadId) {
        actividadState = viewModel.obtenerActividadPorId(actividadId)
    }

    val actividad = actividadState

    if (actividad != null) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Detalle de la actividad") })
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = actividad.actividad, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Categoría: ${actividad.tipo}",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(24.dp))

                DatoDetalle(etiqueta = "Participantes", valor = "${actividad.participantes}")
                DatoDetalle(etiqueta = "Duración", valor = actividad.duracion)
                DatoDetalle(etiqueta = "Precio", valor = if (actividad.precio <= 0.0) "Gratis" else "De pago")
                DatoDetalle(etiqueta = "Accesibilidad", valor = actividad.accesibilidad)
                DatoDetalle(etiqueta = "Apta para niños", valor = if (actividad.aptaParaNinos) "Sí" else "No")

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Volver al Catálogo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    } else {
        // Mostrar un indicador mientras se obtiene la actividad
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DatoDetalle(etiqueta: String, valor: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(text = etiqueta, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = valor, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
    }
}