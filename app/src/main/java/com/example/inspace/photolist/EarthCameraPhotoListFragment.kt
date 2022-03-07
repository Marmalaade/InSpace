package com.example.inspace.photolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.R
import com.example.inspace.databinding.FragmentEarthCameraPhotoListBinding
import com.example.inspace.databinding.FragmentMarsEstateBinding
import com.example.inspace.databinding.FragmentMarsEstatesDetailBinding
import com.example.inspace.marsestate.MarsEstatesViewModel

class EarthCameraPhotoListFragment : Fragment() {

    private var _binding: FragmentEarthCameraPhotoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EarthCameraPhotoListViewModel by lazy {
        ViewModelProvider(this)[EarthCameraPhotoListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthCameraPhotoListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}