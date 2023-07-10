package com.gigih.android.presentation

import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.database.Cursor
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gigih.android.databinding.FragmentSecondBinding
import com.gigih.android.utils.BoundService
import com.gigih.android.utils.DatabaseHelper
import com.gigih.android.utils.DatabaseProvider
import com.gigih.android.utils.MusicService
import com.gigih.android.utils.showToast
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var boundService: BoundService
    private var cursorDatabase: Cursor? = null
    private var cursorContent: Cursor? = null
    private var isBound = false
    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BoundService.BinderDownload
            boundService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), BoundService::class.java).also {
            requireActivity().bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initDatabase()
        initListener()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unbindService(serviceConnection)
    }

    override fun onDestroy() {
        super.onDestroy()
        cursorDatabase?.close()
    }

    private fun initView() {
        val data = arguments?.getString("dataString")
        binding.etName.setText(data)
    }

    private fun initListener() {
        with(binding) {
            btnPlay.setOnClickListener {
                requireActivity().startService(Intent(requireContext(), MusicService::class.java))
            }

            btnStop.setOnClickListener {
                requireActivity().stopService(Intent(requireContext(), MusicService::class.java))
            }

            btnDownload.setOnClickListener {
                lifecycleScope.launch {
                    boundService.getProgress().collect {
                        progressIndicator.progress = it
                    }
                }
            }

            btnGetData.setOnClickListener {
                if (cursorDatabase?.moveToFirst() == true) {
                    requireContext().showToast(cursorDatabase?.getString(1) ?: "empty")
                }
            }

            btnNext.setOnClickListener {
                if (cursorContent?.moveToFirst() == true) {
                    etName.setText(cursorContent?.getString(1) ?: "empty")
                }
            }

            btnPrevious.setOnClickListener {
                if (cursorContent?.moveToPrevious() == true) {
                    etName.setText(cursorContent?.getString(1) ?: "empty")
                }
            }

            btnInsert.setOnClickListener {
                val contentValue = ContentValues()
                contentValue.put(DatabaseProvider.NAME, etName.text.toString())
                requireActivity().contentResolver.insert(DatabaseProvider.CONTENT_URI, contentValue)
                @Suppress("DEPRECATION")
                cursorContent?.requery()
            }

            btnUpdate.setOnClickListener {
                val contentValue = ContentValues()
                contentValue.put(DatabaseProvider.NAME, etName.text.toString())
                requireActivity().contentResolver.update(
                    DatabaseProvider.CONTENT_URI,
                    contentValue,
                    "NAME = ?",
                    arrayOf(etName.text.toString())
                )
                @Suppress("DEPRECATION")
                cursorContent?.requery()
            }

            btnDelete.setOnClickListener {
                requireActivity().contentResolver.delete(
                    DatabaseProvider.CONTENT_URI,
                    "NAME = ?",
                    arrayOf(etName.text.toString())
                )
                @Suppress("DEPRECATION")
                cursorContent?.requery()
            }

            btnClear.setOnClickListener {
                etName.setText("")
                etName.requestFocus()
            }
        }
    }

    private fun initDatabase() {
        val helper = DatabaseHelper(requireContext())
        val db = helper.readableDatabase
        cursorDatabase = db.rawQuery("SELECT * FROM ANDROID_TABLE", null)
        cursorContent = requireActivity().contentResolver.query(
            DatabaseProvider.CONTENT_URI,
            arrayOf(
                DatabaseProvider.id,
                DatabaseProvider.NAME
            ),
            null,
            null,
            DatabaseProvider.NAME
        )
    }
}