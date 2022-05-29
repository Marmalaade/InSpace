package com.example.inspace.mainastronomicalpicture

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.inspace.R
import com.example.inspace.databinding.FragmentMainPictureBinding
import io.github.muddz.styleabletoast.StyleableToast
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

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
        binding.mainPicture.setOnLongClickListener {
            confettiAnimation()
            showCustomToast()
            binding.mainPicture.isLongClickable = false
            return@setOnLongClickListener true
        }

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

    private fun showCustomToast() {
        activity?.let {
            StyleableToast.makeText(it.applicationContext, getText(R.string.toast_text).toString(), R.style.CustomToast)
                .show()
        }
    }


    private fun confettiAnimation() {
        val colorsList = let { resources.getIntArray(R.array.confettiColorList) }.toList()
        binding.konfettiView.build()
            .addColors(colorsList)
            .setDirection(0.0, 180.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(1000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(10))
            .setPosition(-50f, binding.konfettiView.width + 50f, -50f, -50f)
            .streamFor(550, 800L)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

