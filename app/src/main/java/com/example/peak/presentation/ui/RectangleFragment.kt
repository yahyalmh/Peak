package com.example.peak.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.peak.R
import com.example.peak.data.storage.RectangleEntity
import com.example.peak.databinding.FragmentRectangleBinding
import com.example.peak.presentation.UiState
import com.example.peak.presentation.ui.component.RectangleView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@AndroidEntryPoint
class RectangleFragment : Fragment(R.layout.fragment_rectangle) {

    private lateinit var binding: FragmentRectangleBinding
    private val viewModel: RectanglesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRectangleBinding.bind(view)

        observeRectangles()
    }

    private fun observeRectangles() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel
                .rectangles
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            binding.loading.isVisible = false
                            handelData(uiState.data)
                        }
                        is UiState.Error -> {
                            binding.loading.isVisible = false
                            handelError(uiState.message)
                        }
                        is UiState.Loading -> binding.loading.isVisible = true
                    }
                }
        }
    }

    private fun handelData(data: List<RectangleEntity>) =
        data.forEach {
            addChildView(it)
        }


    private fun addChildView(rectangleEntity: RectangleEntity) {
        val rectangleView = RectangleView(requireActivity(), rectangleEntity) { rectangle ->
            viewModel.updateRectangle(rectangle)
        }.apply {
            id = View.generateViewId()
        }

        binding.root.addView(rectangleView)
        binding.root.run {
            val constraintSet = ConstraintSet()
            constraintSet.apply {
                clone(binding.root)
                connect(
                    rectangleView.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START
                )
                connect(
                    rectangleView.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )
                connect(
                    rectangleView.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP
                )
                connect(
                    rectangleView.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM
                )
                setHorizontalBias(rectangleView.id, rectangleEntity.x)
                setVerticalBias(rectangleView.id, rectangleEntity.y)
            }.applyTo(this)
        }
    }

    private fun handelError(message: String) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Warning")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}