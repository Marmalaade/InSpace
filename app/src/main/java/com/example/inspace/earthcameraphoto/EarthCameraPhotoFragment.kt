package com.example.inspace.earthcameraphoto

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.inspace.bottomsheet.BottomSheetFragment
import com.example.inspace.databinding.FragmentEarthCameraPhotoBinding


class EarthCameraPhotoFragment : Fragment() {
    private var _binding: FragmentEarthCameraPhotoBinding? = null
    private val binding get() = _binding!!
    private var bottomSheet: BottomSheetFragment? = null
    private lateinit var earthPhoto: Bitmap

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
        loadEarthCameraPhoto(binding)

        binding.earthPhoto.setOnClickListener {
            if (bottomSheet == null) {
                bottomSheet = BottomSheetFragment(earthPhoto)
            } else if (bottomSheet!!.isAdded) {
                return@setOnClickListener
            }
            bottomSheet?.show(requireActivity().supportFragmentManager, BottomSheetFragment.TAG)

        }
        return binding.root
    }

    private fun loadEarthCameraPhoto(binding: FragmentEarthCameraPhotoBinding) {

        val url = binding.viewModel?.selectedImageProperties?.value?.getImageUrl()
        url?.let {
            val imgUri = url.toUri().buildUpon().scheme("https")?.build()
            Glide.with(requireContext())
                .asBitmap()
                .load(imgUri)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        if (!isRemoving) {
                            earthPhoto = resource
                            binding.earthPhoto.setImage(ImageSource.cachedBitmap(earthPhoto))
                        }
                        binding.statusImage.visibility = View.GONE
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        super.onLoadFailed(placeholder)
                    }
                })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}