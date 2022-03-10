package com.example.inspace.earthcameraphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.R
import com.example.inspace.databinding.FragmentEarthCameraBinding
import com.example.inspace.databinding.FragmentEarthCameraPhotoBinding
import com.example.inspace.earthcameraphotolist.EarthCameraPhotoListFragmentArgs
import com.example.inspace.earthcameraphotolist.EarthCameraPhotoListViewModel


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
        binding.viewModel = ViewModelProvider(this, viewModelFactory)[EarthCameraPhotoViewModel::class.java]
        return binding.root
    }


}