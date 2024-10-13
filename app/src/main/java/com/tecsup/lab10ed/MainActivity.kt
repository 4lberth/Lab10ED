package com.tecsup.lab10ed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.lab10ed.ui.theme.Lab10EDTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab10EDTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    MyApp()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PostApiService::class.java)
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CRUD con JSONPlaceholder") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary) // Aquí se actualiza
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("editPost/0") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Post")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController, startDestination = "postList") {
                composable("postList") {
                    PostListScreen(navController = navController, service = service)
                }
                composable("editPost/{id}") { backStackEntry ->
                    val postId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (postId != null) {
                        EditPostScreen(navController = navController, service = service, postId = postId)
                    }
                }
                composable("deletePost/{id}") { backStackEntry ->
                    val postId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (postId != null) {
                        DeletePostScreen(navController = navController, service = service, postId = postId)
                    }
                }
            }
        }
    }
}



