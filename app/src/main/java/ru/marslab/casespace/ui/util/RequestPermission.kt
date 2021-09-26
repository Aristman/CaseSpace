package ru.marslab.casespace.ui.util

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val OK_TEXT = "OK"
private const val CANCEL_TEXT = "Отмена"

class RequestPermission(
    private val fragment: Fragment,
    private val permissionDialogTitle: Int,
    private val permissionDialogMessage: Int,
    private val requestedPermission: String
) {
    private val _permission: MutableLiveData<PermissionStatus> = MutableLiveData()
    val permission: LiveData<PermissionStatus>
        get() = _permission

    private val permissionRequest =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                _permission.postValue(PermissionStatus.Granted)
            } else {
                _permission.postValue(PermissionStatus.Denied)
            }
        }

    fun getPermission() {
        try {
            when {
                fragment.shouldShowRequestPermissionRationale(requestedPermission) -> {
                    requestPermission()
                }
                isPermissionGranted() -> {
                    _permission.postValue(PermissionStatus.Granted)
                }
                else -> {
                    showRequestPermissionDialog()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun requestPermission() {
        permissionRequest.launch(requestedPermission)
    }

    private fun showRequestPermissionDialog() {
        AlertDialog.Builder(fragment.requireContext())
            .setTitle(permissionDialogTitle)
            .setMessage(permissionDialogMessage)
            .setPositiveButton(OK_TEXT) { _, _ ->
                requestPermission()
            }
            .setNegativeButton(CANCEL_TEXT) { dialog, _ ->
                _permission.postValue(PermissionStatus.Denied)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun isPermissionGranted() = ContextCompat.checkSelfPermission(
        fragment.requireContext(),
        requestedPermission
    ) == PackageManager.PERMISSION_GRANTED
}

sealed class PermissionStatus {
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object Error : PermissionStatus()
}