package com.example.boredapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(navController: NavController, viewModel: ActividadViewModel) {

    // Observamos la lista de actividades desde el ViewModel
    val listaActividades by viewModel.actividades.collectAsState()

    // Obtenemos el contexto actual para poder usarlo en AjustesUsuario
    val context = LocalContext.current
    // Obtenemos el nombre de usuario desde AjustesUsuario
    val ajustesUsuario = remember { AjustesUsuario(context) }
    // Observamos el flujo de nombre de usuario y lo convertimos en un estado para que Compose lo observe
    val nombreUsuario by ajustesUsuario.nombreUsuarioFlow.collectAsState(initial = "Cargando...")

    // SCAFFOLD nos da una estructura profesional con Barra Superior
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hola $nombreUsuario", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("ajustes") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        },

        //Agregar un boton para insertar datos
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //Insertar una actividad de ejemplo
                    val nuevaActividad = Actividad(
                        actividad = "Aprender a hacer origami",
                        tipo = "recreational",
                        participantes = 1,
                        precio = 0.0,
                        duracion = "minutes",
                        accesibilidad = "Few to no challenges",
                        aptaParaNinos = true
                    )
                    viewModel.insertarActividad(nuevaActividad)
                }
            )
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Actividad")
            }
        }

    ) { paddingValues -> // Padding automático para no tapar la barra

        //Mensaje si la base de datos esta vacia
        if (listaActividades.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No hay actividades guardadas. Agrega una usando el botón +",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) } // Espacio inicial
                items(listaActividades) { actividad ->
                    ItemActividad(actividad = actividad, navController = navController)
                }
            }
        }
    }
}

@Composable
fun ItemActividad(actividad: Actividad, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detalles/${actividad.id}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = actividad.actividad, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${actividad.tipo} • ${actividad.participantes} participante(s) • ${actividad.duracion}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (actividad.precio <= 0.0) "💲 Gratis" else "💲 De pago",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (actividad.aptaParaNinos) {
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "👶 Apta para niños",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}