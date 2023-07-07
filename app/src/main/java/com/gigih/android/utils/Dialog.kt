package com.gigih.android.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.gigih.android.R

fun Activity.requestPermissionDialog(
    permission: String,
    requestCode: Int
) {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.rationale_title))
        .setMessage(getRationale(permission))
        .setPositiveButton("OK") { _, _ ->
            // Request the permission when the user clicks OK
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
        .setNegativeButton("Cancel", null)
        .show()
}

fun Context.getRationale(permission: String): String {
    return when(permission) {
        android.Manifest.permission.READ_CONTACTS -> getString(R.string.rationale_desc)
        else -> "Special Request Access Needed!"
    }
}

fun Context.showToast(title: String) {
    Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
}