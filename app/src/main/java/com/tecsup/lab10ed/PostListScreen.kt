package com.tecsup.lab10ed

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PostListScreen(
    navController: NavHostController,
    service: PostApiService
) {
    var postList by remember { mutableStateOf(listOf<PostModel>()) }

    LaunchedEffect(Unit) {
        val response = service.getPosts()
        postList = response
    }

    LazyColumn {
        items(postList) { post ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("editPost/${post.id}")
                    }
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = post.title, fontWeight = FontWeight.Bold)
                    Text(text = post.body)
                }
                IconButton(onClick = {
                    navController.navigate("deletePost/${post.id}")
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
