package com.example.inspace.earthcameraphoto

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.inspace.databinding.FragmentEarthCameraPhotoBinding
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import java.io.IOException


class EarthCameraPhotoFragment : Fragment() {
    private var _binding: FragmentEarthCameraPhotoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        _binding = FragmentEarthCameraPhotoBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val imageProperties = EarthCameraPhotoFragmentArgs.fromBundle(requireArguments()).selectedImageProperties
        val viewModelFactory = EarthCameraPhotoViewModelFactory(imageProperties, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[EarthCameraPhotoViewModel::class.java]
        binding.viewModel = viewModel
        loadEarthCameraPhoto()
        return binding.root
    }

    private fun loadEarthCameraPhoto() {
        val imageLoader = ImageLoader.getInstance()
        imageLoader.init(ImageLoaderConfiguration.createDefault(context))
        val imgUrl = binding.viewModel?.selectedImageProperties?.value?.getImageUrl()
        try {
            imageLoader.loadImage(imgUrl, object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    super.onLoadingComplete(imageUri, view, loadedImage)
                    binding.statusImage.visibility = View.GONE
                    binding.earthPhoto.setImage(ImageSource.cachedBitmap(loadedImage))
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}