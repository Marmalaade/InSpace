package com.example.inspace.marsestate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inspace.R
import com.example.inspace.databinding.FragmentMarsEstateBinding


class MarsEstate : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMarsEstateBinding.inflate(inflater)
        return binding.root
    }
}