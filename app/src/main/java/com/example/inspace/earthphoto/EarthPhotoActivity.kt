package com.example.inspace.earthphoto

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.inspace.R

class EarthPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_earth_photo)
    }

    companion object {
        fun startPhotoActivity(context: Context) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://api.nasa.gov/EPIC/archive/natural/2019/05/30/png/epic_1b_20190530011359.png?api_key=DEMO_KEY")
            )
            context.startActivity(intent)
        }
    }
}