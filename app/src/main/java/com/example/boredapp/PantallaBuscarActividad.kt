package com.example.boredapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

// Las categorías y cantidades de participantes que acepta la Bored API
val tiposDisponibles = listOf("education", "recreational", "social", "charity", "cooking", "relaxation", "busywork")
val participantesDisponibles = listOf(1, 2, 3, 4, 5, 6, 8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBuscarActividad(navController: NavController, viewModel: ActividadViewModel) {

    var tipoSeleccionado by remember { mutableStateOf(tiposDisponibles.first()) }
    var participantesSeleccionados by remember { mutableStateOf(participantesDisponibles.first()) }

    val resultados by viewModel.resultadosBusqueda.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Actividad") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp)) {

            Spacer(modifier = Modifier.height(8.dp))
            Text("Categoría", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tiposDisponibles.forEach { tipo ->
                    FilterChip(
                        selected = tipo == tipoSeleccionado,
                        onClick = { tipoSeleccionado = tipo },
                        label = { Text(tipo) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Participantes", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                participantesDisponibles.forEach { numero ->
                    FilterChip(
                        selected = numero == participantesSeleccionados,
                        onClick = { participantesSeleccionados = numero },
                        label = { Text("$numero") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.buscarActividades(tipoSeleccionado, participantesSeleccionados) },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Buscar", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (resultados.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Elige categoría y participantes, y presiona Buscar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Text("Toca un resultado para guardarlo en tu catálogo", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(resultados) { resultado ->
                        ItemResultadoBusqueda(
                            actividadRed = resultado,
                            onGuardar = {
                                viewModel.guardarActividadDesdeApi(resultado)
                                navController.popBackStack()
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }
        }
    }
}

@Composable
fun ItemResultadoBusqueda(actividadRed: ActividadRed, onGuardar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onGuardar),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = actividadRed.actividad, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${actividadRed.tipo} • ${actividadRed.participantes} participante(s) • ${actividadRed.duracion}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}