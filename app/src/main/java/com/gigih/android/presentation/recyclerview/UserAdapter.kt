package com.gigih.android.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gigih.android.data.model.User
import com.gigih.android.databinding.ItemUserBinding

class UserAdapter : ListAdapter<User, UserAdapter.ViewHolder>(DiffUtilCallback) {

    interface OnItemClickedListener {
        fun onClicked(item: User, position: Int)
        fun onLongClicked(item: User, position: Int)
    }

    var listener: OnItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User, position: Int) {
            with(binding) {
                tvName.text = item.name
                binding.root.setOnClickListener {
                    listener?.onClicked(item, position)
                }
                binding.root.setOnLongClickListener {
                    listener?.onLongClicked(item, position)
                    true
                }
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}