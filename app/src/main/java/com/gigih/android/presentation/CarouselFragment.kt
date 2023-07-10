package com.gigih.android.presentation

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.gigih.android.R
import com.gigih.android.databinding.FragmentCarouselBinding
import kotlin.math.abs

class CarouselFragment : Fragment() {

    private lateinit var binding: FragmentCarouselBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarouselBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            vpCarousel.apply {
                clipChildren = false
                clipToPadding = false
                offscreenPageLimit = 3
                (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }

            val carouselList = listOf(
                getString(R.string.app_name),
                getString(R.string.download),
                getString(R.string.permission_denied),
                getString(R.string.permission_granted),
                getString(R.string.clear)
            )

            vpCarousel.adapter = CarouselAdapter(carouselList)

            val pageTransformer = CompositePageTransformer()
            pageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
            pageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = (0.80f + r * 0.20f)
            }
            vpCarousel.setPageTransformer(pageTransformer)
        }
    }
}