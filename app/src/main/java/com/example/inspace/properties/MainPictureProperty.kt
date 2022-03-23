package com.example.inspace.properties

import com.example.inspace.database.DatabaseEntities
import com.squareup.moshi.Json

data class MainPictureProperty(
    @Json(name = "hdurl") val img_url: String,
    val title: String,
)


fun MainPictureProperty.asDatabaseModel(): DatabaseEntities {
    return DatabaseEntities(
        img_url = img_url,
        title = title,
    )
}
