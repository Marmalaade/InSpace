package com.example.inspace.marsestate

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.inspace.R
import com.example.inspace.adapters.MarsPhotosGridAdapter
import com.example.inspace.databinding.FragmentMarsEstateBinding
import com.example.inspace.network.MarsApiFilter

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
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.marsPhotos.adapter = MarsPhotosGridAdapter(MarsPhotosGridAdapter.OnClickListener {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.estates_filter_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_buy -> MarsApiFilter.SHOW_BYE
                R.id.show_rent -> MarsApiFilter.SHOW_RENT
                else -> MarsApiFilter.SHOW_ALL
            }
        )
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}