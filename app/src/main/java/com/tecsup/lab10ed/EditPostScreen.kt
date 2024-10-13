package com.tecsup.lab10ed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun EditPostScreen(
    navController: NavHostController,
    service: PostApiService,
    postId: Int? = null
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(postId) {
        if (postId != null) {
            val post = service.getPost(postId).body()
            title = post?.title ?: ""
            body = post?.body ?: ""
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
        TextField(value = body, onValueChange = { body = it }, label = { Text("Descripción") })

        Button(onClick = {
            val newPost = PostModel(id = postId ?: 0, title = title, body = body)

            coroutineScope.launch {
                if (postId == null) {
                    // Crear un nuevo post
                    service.createPost(newPost)
                } else {
                    // Actualizar el post existente
                    service.updatePost(postId, newPost)
                }
                navController.popBackStack() // Volver a la lista después de guardar
            }
        }) {
            Text("Guardar")
        }
    }
}
