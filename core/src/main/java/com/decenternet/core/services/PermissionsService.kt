package com.decenternet.core.services

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.decenternet.core.R
import com.decenternet.core.interfaces.IPermissionsService
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.interfaces.callback.RequestPermissionCallback

class PermissionsService(private val _context: Context, private val _localiser: IStringService) : IPermissionsService {

    override fun hasLocationAccess(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            _context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    override fun requestLocationAccess(requestingActivity: Activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (_context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requestingActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    val builder = AlertDialog.Builder(requestingActivity)
                    builder.setCancelable(false)
                    builder.setMessage(_localiser.get(R.string.access_location_description)).setTitle(_localiser.get(R.string.access_location_title))
                    builder.setPositiveButton(
                        _localiser.get(R.string.ok)) { _, _ ->
                        ActivityCompat.requestPermissions(requestingActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LocationPermission)
                    }
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    ActivityCompat.requestPermissions(requestingActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LocationPermission)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, callback: RequestPermissionCallback) {
        when (requestCode) {
            LocationPermission -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callback.onPermissionSuccess(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    callback.onPermissionDeclined(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    }

    companion object {
        private val LocationPermission = 1
    }
}
