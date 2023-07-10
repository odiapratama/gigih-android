package com.gigih.android.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigih.android.databinding.ItemCarouselBinding

class CarouselAdapter(
    private val carouselList: List<String>
): RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            ItemCarouselBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(carouselList[position])
    }

    override fun getItemCount() = carouselList.size

    inner class CarouselViewHolder(
        private val binding: ItemCarouselBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvDescription.text = item
        }
    }
}