package com.example.inspace.marsestatesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.databinding.FragmentMarsEstatesDetailBinding


class MarsEstatesDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentMarsEstatesDetailBinding.inflate(inflater)
        val marsProperty = MarsEstatesDetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = MarsEstatesDetailViewModelFactory(marsProperty, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)[MarsEstatesDetailViewMode::class.java]
        return binding.root
    }

}