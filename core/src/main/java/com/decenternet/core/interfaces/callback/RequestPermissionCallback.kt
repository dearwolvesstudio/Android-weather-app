package com.decenternet.core.interfaces.callback

interface RequestPermissionCallback {

    /**
     * What happens when the permission is successful
     */
    fun onPermissionSuccess(permissionName:String)

    /**
     * What happens when the permission is denied
     */
    fun onPermissionDeclined(permissionName:String)
}