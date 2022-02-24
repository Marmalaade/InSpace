package com.example.inspace.marsestate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.R
import com.example.inspace.databinding.FragmentMarsEstateBinding

class MarsEstate : Fragment() {
    private val viewModel: MarsEstateViewModel by lazy {
        ViewModelProvider(this)[MarsEstateViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMarsEstateBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }
}