package com.decenternet.core.interfaces

import android.app.Activity
import androidx.annotation.NonNull
import com.decenternet.core.interfaces.callback.RequestPermissionCallback

interface IPermissionsService {

    /**
     * @return Whether the app has permission to access location
     */
    fun hasLocationAccess(): Boolean

    /**
     * @param requestingActivity - the activity that is requesting the permission
     */
    fun requestLocationAccess(requestingActivity: Activity)

    /**
     * A forwarding method for the activities on result callback
     * This should be used to forward all permission results too
     * @param requestCode - as defined by {@link androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback}
     * @param permissions - as defined by {@link androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback}
     * @param grantResults - as defined by {@link androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback}
     * @param callback - the code you wish to execute in a success or fail case
     */
    fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:Array<Int>, callback: RequestPermissionCallback)
}