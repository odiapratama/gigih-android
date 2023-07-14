package com.gigih.android.presentation.listview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gigih.android.R
import com.gigih.android.data.model.listUser
import com.gigih.android.databinding.FragmentListViewBinding
import com.gigih.android.utils.showToast

class ListViewFragment : Fragment() {

    private lateinit var binding: FragmentListViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapter = UserAdapter(requireContext(), R.layout.item_user, listUser)
        binding.listView.adapter = adapter
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            requireContext().showToast(listUser[position].name)
        }
    }
}