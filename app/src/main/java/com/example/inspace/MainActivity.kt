package com.example.inspace

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.inspace.work.RefreshDataWorker
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delayedInit()
        setTheme(R.style.Theme_InSpace)
        setContentView(R.layout.activity_main)
        setActionBarColor()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.menu.getItem(2).isEnabled = false
        val navController = this.findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainPicture, R.id.marsEstates, R.id.earthCamera))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val workerConstraints = Constraints.Builder()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(workerConstraints)
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun setActionBarColor() {
        val actionBar: ActionBar? = supportActionBar
        val colorDrawable = ColorDrawable(
            Color.parseColor(getString(R.string.parse_color))
        )
        actionBar!!.setBackgroundDrawable(colorDrawable)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}