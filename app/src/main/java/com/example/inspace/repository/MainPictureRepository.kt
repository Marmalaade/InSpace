package com.example.inspace.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.inspace.database.MainPictureDatabase
import com.example.inspace.database.asDomainModel
import com.example.inspace.network.MainPictureApi
import com.example.inspace.properties.MainPictureProperty
import com.example.inspace.properties.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainPictureRepository(private val database: MainPictureDatabase) {

    val mainItems: LiveData<MainPictureProperty> = Transformations.map(database.mainPictureDao.getMainScreenItems()) {
        it?.asDomainModel()
    }

    suspend fun refreshMainScreenItems() {
        withContext(Dispatchers.IO) {
            val mainItems = MainPictureApi.retrofitService.getPropertiesAsync()
            database.mainPictureDao.insertMainScreenItems(mainItems.asDatabaseModel())
        }
    }
}