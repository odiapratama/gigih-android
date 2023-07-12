package com.gigih.android.presentation.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gigih.android.R
import com.gigih.android.databinding.FragmentAnimationBinding

abstract class AnimationUi : Fragment(R.layout.fragment_animation), AnimationClick,
    OnClickListener {

    lateinit var binding: FragmentAnimationBinding
    private lateinit var clickListener: AnimationClick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener = this
        initListener()
    }

    private fun initListener() {
        with(binding) {
            btnFadeInInAnimation.setOnClickListener(this@AnimationUi)
            btnFadeOutAnimation.setOnClickListener(this@AnimationUi)
            btnZoomInAnimation.setOnClickListener(this@AnimationUi)
            btnZoomOutAnimation.setOnClickListener(this@AnimationUi)
            btnSlideUpAnimation.setOnClickListener(this@AnimationUi)
            btnSlideDownAnimation.setOnClickListener(this@AnimationUi)
            btnRotateAnimation.setOnClickListener(this@AnimationUi)
            btnBounceAnimation.setOnClickListener(this@AnimationUi)
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v?.id) {
                btnFadeInInAnimation.id -> clickListener.fadeInAnimation()
                btnFadeOutAnimation.id -> clickListener.fadeOutAnimation()
                btnZoomInAnimation.id -> clickListener.zoomInAnimation()
                btnZoomOutAnimation.id -> clickListener.zoomOutAnimation()
                btnSlideUpAnimation.id -> clickListener.zoomInAnimation()
                btnSlideDownAnimation.id -> clickListener.zoomOutAnimation()
                btnBounceAnimation.id -> clickListener.rotateAnimation()
                btnRotateAnimation.id -> clickListener.bounceAnimation()
            }
        }
    }
}