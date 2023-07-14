package com.gigih.android.presentation.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gigih.android.data.model.User
import com.gigih.android.data.model.listUser
import com.gigih.android.databinding.FragmentRecyclerviewBinding
import com.gigih.android.utils.showToast

class RecyclerViewFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val data = listUser

        val listAdapter = UserAdapter()
        listAdapter.listener = object : UserAdapter.OnItemClickedListener {
            override fun onClicked(item: User, position: Int) {
                requireContext().showToast("Clicked position $position")
                data.add(User())
                listAdapter.notifyItemInserted(data.size)
            }

            override fun onLongClicked(item: User, position: Int) {
                requireContext().showToast("Long Clicked position $position")
                data.remove(item)
                listAdapter.notifyItemRemoved(position)
                listAdapter.notifyItemRangeChanged(position, data.size - position)
            }
        }
        listAdapter.submitList(data)
        binding.rvUser.adapter = listAdapter
    }
}