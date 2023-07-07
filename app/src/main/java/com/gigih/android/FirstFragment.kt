package com.gigih.android

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gigih.android.databinding.FragmentFirstBinding
import com.gigih.android.utils.Const
import com.gigih.android.utils.requestPermissionDialog
import com.gigih.android.utils.showToast

class FirstFragment : Fragment() {

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFirstBinding.inflate(inflater, container, false)
        initRequestPermission()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        with(binding) {
            btnNext.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }

            btnRequestContact.setOnClickListener {
                checkPermission(android.Manifest.permission.READ_CONTACTS)
            }

            btnRequestLocation.setOnClickListener {
                checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
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
                permission.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    requireContext().showToast("Precise location access granted.")
                }
                permission.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    requireContext().showToast("Only approximate location access granted.")
                }
                else -> {
                    requireContext().showToast("No location access granted.")
                }
            }
        }
    }

    private fun checkPermission(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                requireContext().showToast("Action!")
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
            permission == android.Manifest.permission.ACCESS_FINE_LOCATION ||
            permission == android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
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
}