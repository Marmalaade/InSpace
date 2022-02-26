package com.example.inspace.marsestate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.inspace.R
import com.example.inspace.databinding.FragmentMarsEstateBinding
import com.example.inspace.mainastronomicalpicture.MainPictureFragment

class MarsEstateFragment : Fragment() {
    private val viewModel: MarsEstateViewModel by lazy {
        ViewModelProvider(this)[MarsEstateViewModel::class.java]
    }

    @SuppressLint("ResourceType")
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