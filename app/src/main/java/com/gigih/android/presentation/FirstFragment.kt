package com.gigih.android.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.gigih.android.R
import com.gigih.android.data.database.AppDataStore
import com.gigih.android.data.database.AppPreferences
import com.gigih.android.databinding.FragmentFirstBinding
import com.gigih.android.utils.Const
import com.gigih.android.utils.LanguageHelper
import com.gigih.android.utils.NotificationWorker
import com.gigih.android.utils.requestPermissionDialog
import com.gigih.android.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {

    @Inject
    lateinit var dataStore: AppDataStore

    @Inject
    lateinit var preferences: AppPreferences

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentFirstBinding
    private var dataIntent: String? = null

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateUi()
    }

    private fun updateUi() {
        with(binding) {
            btnRequestContact.text = getString(R.string.request_contact)
            btnRequestLocation.text = getString(R.string.request_location)
            tvShowActivityLifecycle.text = getString(R.string.show_activity_lifecycle)
            tvShowFragmentLifecycle.text = getString(R.string.show_fragment_lifecycle)
            tvTheme.text = getString(R.string.theme)
            tvLanguage.text = getString(R.string.language)
            btnAddScheduledNotification.text = getString(R.string.add_scheduled_notification)
            ivFlag.setImageResource(R.mipmap.flag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLifecycleToast("onCreate Fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        showLifecycleToast("onCreateView Fragment")
        initRequestPermission()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLifecycleToast("onViewCreated Fragment")
        initView()
        initListener()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        showLifecycleToast("onViewStateRestored Fragment")
    }

    override fun onStart() {
        super.onStart()
        showLifecycleToast("onStart Fragment")
    }

    override fun onResume() {
        super.onResume()
        showLifecycleToast("onResume Fragment")
    }

    override fun onPause() {
        super.onPause()
        showLifecycleToast("onPause Fragment")
    }

    override fun onStop() {
        super.onStop()
        showLifecycleToast("onStop Fragment")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        showLifecycleToast("onSaveInstanceState Fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showLifecycleToast("onDestroyView Fragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        showLifecycleToast("onDestroy Fragment")
    }

    private fun initView() {
        with(binding) {
            dataIntent = requireActivity().intent.getStringExtra("data")
            tvDataIntent.text = dataIntent
            lifecycleScope.launch {
                dataStore.getShowActivityLifecycle().collect {
                    switchActivity.isChecked = it
                }
            }
            lifecycleScope.launch {
                dataStore.getShowFragmentLifecycle().collect {
                    switchFragment.isChecked = it
                }
            }
        }
    }

    private fun initListener() {
        with(binding) {
            btnNext.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("dataString", "Data from FirstFragment")
                }
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }

            btnRequestContact.setOnClickListener {
                checkPermission(Manifest.permission.READ_CONTACTS) {
                    requireContext().showToast("Action!")
                }
            }

            btnRequestLocation.setOnClickListener {
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) {
                    requireContext().showToast("Action!")
                }
            }

            switchActivity.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    dataStore.setShowActivityLifecycle(isChecked)
                }
            }

            switchFragment.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    dataStore.setShowFragmentLifecycle(isChecked)
                }
            }

            btnImplicitIntent.setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, dataIntent)
                    type = "text/plain"
                }
                startActivity(sendIntent)
            }

            btnAddScheduledNotification.setOnClickListener {
                if (Build.VERSION.SDK_INT >= 33) {
                    checkPermission(Manifest.permission.POST_NOTIFICATIONS) {
                        addScheduledNotification()
                    }
                } else {
                    addScheduledNotification()
                }
            }

            btnGoToCarousel.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_CarouselFragment)
            }

            btnGoToAnimation.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_AnimationFragment)
            }

            btnGoToListView.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_ListViewFragment)
            }

            btnGoToRecyclerView.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_RecyclerViewFragment)
            }

            spTheme.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.item_themes,
                android.R.layout.simple_spinner_dropdown_item
            )

            spTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> {
                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM.let {
                                AppCompatDelegate.setDefaultNightMode(it)
                                preferences.themeMode = it
                            }
                        }

                        2 -> {
                            AppCompatDelegate.MODE_NIGHT_NO.let {
                                AppCompatDelegate.setDefaultNightMode(it)
                                preferences.themeMode = it
                            }
                        }

                        3 -> {
                            AppCompatDelegate.MODE_NIGHT_YES.let {
                                AppCompatDelegate.setDefaultNightMode(it)
                                preferences.themeMode = it
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            spLanguage.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.item_language,
                android.R.layout.simple_spinner_dropdown_item
            )

            spLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> {
                            preferences.language = "en"
                            // Option restart activity
                            startActivity(
                                Intent(requireContext(), MenuActivity::class.java)
                            )
                            requireActivity().finish()
                        }

                        2 -> {
                            preferences.language = "in"
                            // Option onConfigurationChanged
                            LanguageHelper(requireContext()).applyLocalizedContext(
                                requireContext(),
                                "in"
                            ) {
                                onConfigurationChanged(it)
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun initRequestPermission() {
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    requireContext().showToast(getString(R.string.permission_granted))
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    requireContext().showToast(getString(R.string.permission_denied))
                }
            }

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            when {
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    requireContext().showToast("Precise location access granted.")
                }

                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    requireContext().showToast("Only approximate location access granted.")
                }

                else -> {
                    requireContext().showToast("No location access granted.")
                }
            }
        }
    }

    private fun checkPermission(permission: String, action: () -> Unit) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                action()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected, and what
                // features are disabled if it's declined. In this UI, include a
                // "cancel" or "no thanks" button that lets the user continue
                // using your app without granting the permission.
                requireActivity().requestPermissionDialog(
                    permission,
                    Const.RequestCode.READ_CONTACT_REQUEST_CODE
                )
            }

            permission == Manifest.permission.ACCESS_FINE_LOCATION ||
                    permission == Manifest.permission.ACCESS_COARSE_LOCATION -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun showLifecycleToast(title: String) {
        lifecycleScope.launch {
            dataStore.getShowFragmentLifecycle().collect {
                if (it) requireContext().showToast(title)
            }
        }
    }

    private fun addScheduledNotification() {
        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(5L, TimeUnit.SECONDS)
            .addTag("notification")
            .build()

        WorkManager.getInstance(requireContext()).cancelAllWorkByTag("work")
        WorkManager.getInstance(requireContext()).enqueue(work)
    }
}