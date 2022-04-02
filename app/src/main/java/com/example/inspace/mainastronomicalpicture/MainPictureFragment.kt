package com.example.inspace.mainastronomicalpicture

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.R
import com.example.inspace.databinding.FragmentMainPictureBinding

class MainPictureFragment : Fragment() {

    private var _binding: FragmentMainPictureBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainPictureViewModel by lazy {
        ViewModelProvider(this)[MainPictureViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMainPictureBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_button -> refreshData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshData() {
        activity?.intent?.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_NO_ANIMATION
        )
        activity?.apply {
            overridePendingTransition(0, 0)
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

