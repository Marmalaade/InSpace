package com.example.inspace.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.inspace.database.getDatabase
import com.example.inspace.repository.MainPictureRepository
import retrofit2.HttpException

class RefreshDataWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "UpdateDataWorker"
    }

    override suspend fun doWork(): Result {

        val database = getDatabase(applicationContext)
        val repository = MainPictureRepository(database)

        return try {
            repository.refreshMainScreenItems()
            Result.success()
        } catch (ex: HttpException) {
            ex.printStackTrace()
            Result.retry()
        }
    }


}