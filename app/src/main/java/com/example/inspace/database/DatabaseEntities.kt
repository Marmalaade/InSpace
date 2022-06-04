package com.example.inspace.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.inspace.properties.MainPictureProperty

@Entity
data class DatabaseEntities constructor(
    @PrimaryKey
    val img_url: String,
    val title: String
)

fun DatabaseEntities.asDomainModel(): MainPictureProperty {
    return MainPictureProperty(
        img_url = img_url,
        title = title
    )
}

