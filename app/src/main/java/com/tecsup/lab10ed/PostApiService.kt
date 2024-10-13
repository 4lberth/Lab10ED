package com.tecsup.lab10ed

import retrofit2.Response
import retrofit2.http.*

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostModel>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<PostModel>

    @POST("posts")
    suspend fun createPost(@Body post: PostModel): Response<PostModel>

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostModel): Response<PostModel>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<PostModel>
}