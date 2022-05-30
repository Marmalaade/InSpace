package com.example.inspace.developerinformation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.R
import com.example.inspace.databinding.FragmentDeveloperInformationBinding


class DeveloperInformationFragment : Fragment() {

    private var _binding: FragmentDeveloperInformationBinding? = null
    private val binding get() = _binding!!
    private var intent: Intent? = null

    private val viewModel: DeveloperInformationViewModel by lazy {
        ViewModelProvider(this)[DeveloperInformationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeveloperInformationBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.onInstagramCardViewClickEvent.observe(viewLifecycleOwner, Observer { event ->
            if (event == false) {
                startInstagramLink()
            }
        })
        viewModel.onTelegramCardViewClickEvent.observe(viewLifecycleOwner, Observer { event ->
            if (event == false) {
                startTelegramLink()
            }
        })
        viewModel.onDiscordCardViewClickEvent.observe(viewLifecycleOwner, Observer { event ->
            if (event == false) {
                startDiscordLink()
            }
        })
        return binding.root
    }

    private fun startDiscordLink() {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.discord_link)))
        startActivity(intent)
    }

    private fun startInstagramLink() {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_link)))
        startActivity(intent)
    }

    private fun startTelegramLink() {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.telegram_link)))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}