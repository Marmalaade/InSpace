package com.example.inspace.earthcamera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.inspace.adapters.EarthCameraDatesAdapter
import com.example.inspace.databinding.FragmentEarthCameraBinding

class EarthCameraFragment : Fragment() {

    private var _binding: FragmentEarthCameraBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EarthCameraViewModel by lazy {
        ViewModelProvider(this)[EarthCameraViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthCameraBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.datesItem.adapter = EarthCameraDatesAdapter(EarthCameraDatesAdapter.OnClickListener {
            viewModel.displayPhotoListProperty(it)

        })
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                view?.findNavController()?.navigate(EarthCameraFragmentDirections.showPhotoList(it))
                viewModel.displayPhotoListCompleted()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}