package com.decenternet.core.services

import android.content.Context
import android.content.SharedPreferences
import com.decenternet.core.interfaces.ISharedPreferenceManager
import com.google.gson.Gson
import java.lang.reflect.Type

class SharedPreferenceService(context: Context) : ISharedPreferenceManager {
    private val _sharedPreferences: SharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context)

    override fun <T> put(key: String, value: T): Boolean {
        val gson:Gson = Gson()
        val serialised = gson.toJson(value)
        val editor:SharedPreferences.Editor = _sharedPreferences.edit()
        editor.putString(key, serialised)
        editor.apply()
        return true
    }

    override fun <T> get(key: String, classToDeserialize: Class<T>): T? {
        (_sharedPreferences.getString(key, ""))?.also { a ->
            return Gson().fromJson(a, classToDeserialize)
        }

        return  null
    }

    override fun <T> get(key: String, typeToDeserialize: Type): T? {
        (_sharedPreferences.getString(key, ""))?.also { a ->
            return Gson().fromJson(a, typeToDeserialize)
        }

        return  null
    }

}