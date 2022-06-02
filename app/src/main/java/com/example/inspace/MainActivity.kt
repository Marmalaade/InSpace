package com.example.inspace

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.inspace.databinding.ActivityMainBinding
import com.example.inspace.work.RefreshDataWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityMainBinding

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }

    private var dialogBuilder: AlertDialog? = null
    private var clicked = false

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_InSpace)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        delayedInit()
        setActionBarColor()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.menu.getItem(2).isEnabled = false
        val navController = this.findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainPicture, R.id.marsEstates, R.id.earthCamera, R.id.creator))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        mainBinding.fab.setOnClickListener {
            if (dialogBuilder == null) {
                dialogBuilder = AlertDialog.Builder(this).create()
            }
            showInfoDialog()
            onFabButtonClicked()
        }
    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false
            )
            if (notConnected) {
                showNoInternetConnectionSnackBar()
            }
        }
    }

    private fun showNoInternetConnectionSnackBar() {
        val snackBar = Snackbar.make(mainBinding.mainLayout, "", Snackbar.LENGTH_LONG)
        val customSnackView = layoutInflater.inflate(R.layout.custom_snackbar, null)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)

        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        val params = snackBar.view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackBar.view.layoutParams = params

        snackBarLayout.apply {
            setPadding(0, 0, 0, 0)
            addView(customSnackView, 0)

        }
        snackBar.show()
    }


    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun showInfoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_info_dialog, null)
        val dialogBackButton = dialogView.findViewById<Button>(R.id.back_button)
        dialogBuilder?.apply {
            setView(dialogView)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.windowAnimations = R.style.InfoDialogAnimation
            setCanceledOnTouchOutside(false)
        }
        dialogBackButton.setOnClickListener {
            dialogBuilder?.dismiss()
        }
        dialogBuilder?.show()
    }

    private fun fabAnimation(clicked: Boolean) {
        if (!clicked) {
            mainBinding.fab.startAnimation(rotateOpen)
        } else {
            mainBinding.fab.startAnimation(rotateClose)
        }
    }

    private fun onFabButtonClicked() {
        fabAnimation(clicked)
        clicked = !clicked
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