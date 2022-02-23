package com.example.inspace.mainastronomicalpicture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.inspace.databinding.FragmentMainPictureBinding


class MainPicture : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainPictureBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}
