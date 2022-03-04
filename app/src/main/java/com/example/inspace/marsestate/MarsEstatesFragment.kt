package com.example.inspace.marsestate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.inspace.adapters.PhotosGridAdapter
import com.example.inspace.databinding.FragmentMarsEstateBinding

class MarsEstatesFragment : Fragment() {

    private var _binding: FragmentMarsEstateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MarsEstatesViewModel by lazy {
        ViewModelProvider(this)[MarsEstatesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsEstateBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.marsPhotos.adapter = PhotosGridAdapter(PhotosGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                view?.findNavController()?.navigate(MarsEstatesFragmentDirections.actionShowDetail(it))
                viewModel.displayDetailsCompleted()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}