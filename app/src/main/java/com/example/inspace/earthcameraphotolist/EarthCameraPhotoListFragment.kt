package com.example.inspace.earthcameraphotolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.inspace.adapters.EarthCameraPhotosAdapter
import com.example.inspace.databinding.FragmentEarthCameraPhotoListBinding
import com.example.inspace.earthcamera.EarthCameraFragmentDirections

class EarthCameraPhotoListFragment : Fragment() {

    private var _binding: FragmentEarthCameraPhotoListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthCameraPhotoListBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = viewLifecycleOwner
        val earthCameraDateProperty = EarthCameraPhotoListFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = EarthCameraPhotoListViewModelFactory(earthCameraDateProperty, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)[EarthCameraPhotoListViewModel::class.java]
        binding.viewModel = viewModel
        binding.photoItems.adapter = EarthCameraPhotosAdapter(EarthCameraPhotosAdapter.OnClickListener {
            viewModel.displaySelectedImage(it)
        })
        viewModel.navigateToSelectedImage.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                view?.findNavController()?.navigate(EarthCameraPhotoListFragmentDirections.showImage(it))
                viewModel.displaySelectedImageCompleted()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}