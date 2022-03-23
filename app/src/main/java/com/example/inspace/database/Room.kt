package com.example.inspace.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MainPictureDao {
    @Query("SELECT * FROM DatabaseEntities")
    fun getMainScreenItems(): LiveData<DatabaseEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMainScreenItems(mainPictureItems: DatabaseEntities)
}

@Database(entities = [DatabaseEntities::class], version = 1)
abstract class MainPictureDatabase : RoomDatabase() {
    abstract val mainPictureDao: MainPictureDao
}

private lateinit var INSTANCE: MainPictureDatabase

fun getDatabase(context: Context): MainPictureDatabase {
    synchronized(MainPictureDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MainPictureDatabase::class.java,
                "main_items_database"
            ).build()
        }
    }
    return INSTANCE
}