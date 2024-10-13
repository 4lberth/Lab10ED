package com.tecsup.lab10ed

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)