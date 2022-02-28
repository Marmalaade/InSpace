package com.example.inspace.properties

import com.squareup.moshi.Json

data class MarsPropertyItem(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val price: Int,
    val type: String
)