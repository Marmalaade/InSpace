package com.example.inspace.properties

import com.squareup.moshi.Json

data class MainPictureProperty(
    val date: String,
    val explanation: String,
    @Json(name = "hdurl") val img_url: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)