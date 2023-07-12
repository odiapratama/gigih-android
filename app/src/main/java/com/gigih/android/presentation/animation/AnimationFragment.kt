package com.gigih.android.presentation.animation

import android.view.animation.AnimationUtils
import com.gigih.android.R
import com.gigih.android.utils.gone
import com.gigih.android.utils.visible

class AnimationFragment : AnimationUi() {

    override fun fadeInAnimation() {
        binding.tvTitle.visible()
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        )
    }

    override fun fadeOutAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        )
        binding.tvTitle.gone()
    }

    override fun zoomInAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in)
        )
    }

    override fun zoomOutAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out)
        )
    }

    override fun slideUpAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        )
    }

    override fun slideDownAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        )
    }

    override fun bounceAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        )
    }

    override fun rotateAnimation() {
        binding.tvTitle.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        )
    }
}