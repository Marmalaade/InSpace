package com.example.inspace.bottomsheet

import android.app.WallpaperManager
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.inspace.R
import com.example.inspace.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.util.*


class BottomSheetFragment(private val earthPhoto: Bitmap) : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var mediaPlayer: MediaPlayer? = null
    private var imageUri: Uri? = null
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    companion object {
        const val TAG = "BottomSheetFragment.Tag"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomSheetBinding.bind(inflater.inflate(R.layout.fragment_bottom_sheet, container))
        mediaPlayer = MediaPlayer.create(activity?.applicationContext, R.raw.done_soundeffect)
        binding.setWallpaperButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                setWallpaper()
            }
            hideBottomSheet()
        }
        binding.shareButton.setOnClickListener {
            sharePhoto()
            hideBottomSheet()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Log.e(TAG, "STATE_COLLAPSED")
                    BottomSheetBehavior.STATE_EXPANDED -> Log.e(TAG, "STATE_EXPANDED")
                    else -> Log.d(TAG, "$newState")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (isAdded) {
                    animateBottomArrow(slideOffset)
                }
            }
        })
    }

    private fun sharePhoto() {
        saveImageToGallery(earthPhoto, context!!.contentResolver)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            Log.e("masd", imageUri.toString())
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_photo)))

    }


    private fun saveImageToGallery(bitmap: Bitmap, contentResolver: ContentResolver) {
        val fos: OutputStream
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "PictureFolder")
                imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = contentResolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Objects.requireNonNull<OutputStream?>(fos)
                Log.e("save", "Image saved")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setWallpaper() {
        val wallpaper = WallpaperManager.getInstance(this.activity?.applicationContext)
        try {
            wallpaper.setBitmap(earthPhoto)
            mediaPlayer?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun hideBottomSheet() {
        this.apply {
            if (isVisible) {
                dismiss()
            } else showsDialog
        }
    }


    private fun animateBottomArrow(offset: Float) {
        binding.bottomArrow.rotation = (offset * -180)
        binding.bottomArrow.rotation = (offset * 180)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}