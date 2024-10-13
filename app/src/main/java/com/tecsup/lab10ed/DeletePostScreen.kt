package com.tecsup.lab10ed

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DeletePostScreen(
    navController: NavHostController,
    service: PostApiService,
    postId: Int
) {
    var showDialog by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de eliminar este post?") },
            confirmButton = {
                Button(onClick = {
                    // Lanzar una coroutine para hacer la eliminación en segundo plano
                    coroutineScope.launch(Dispatchers.IO) {
                        service.deletePost(postId)

                        // Cambiar al hilo principal para hacer popBackStack (UI)
                        withContext(Dispatchers.Main) {
                            showDialog = false
                            navController.popBackStack() // Volver a la lista después de eliminar
                        }
                    }
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

