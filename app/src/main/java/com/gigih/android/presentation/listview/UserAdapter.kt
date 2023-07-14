package com.gigih.android.presentation.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gigih.android.data.model.User
import com.gigih.android.databinding.ItemUserBinding

class UserAdapter(
    context: Context,
    layoutId: Int,
    val data: List<User>
) : ArrayAdapter<User>(context, layoutId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemUserBinding = if (convertView == null) {
            ItemUserBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        } else {
            ItemUserBinding.bind(convertView)
        }
        binding.tvName.text = data[position].name
        return binding.root
    }
}