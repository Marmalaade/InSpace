package com.example.inspace.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.databinding.FragmentEarthCameraPhotoListBinding

class EarthCameraPhotoListFragment : Fragment() {

    private var _binding: FragmentEarthCameraPhotoListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthCameraPhotoListBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val earthCameraDateProperty = EarthCameraPhotoListFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = EarthCameraPhotoListViewModelFactory(earthCameraDateProperty, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)[EarthCameraPhotoListViewModel::class.java]
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}