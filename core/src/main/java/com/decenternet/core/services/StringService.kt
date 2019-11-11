package com.decenternet.core.services

import android.content.Context
import com.decenternet.core.interfaces.IStringService

class StringService(private val _context: Context) : IStringService {
    override fun get(key: String): String {
        val parsed = Integer.parseInt(key)
        return _context.getString(parsed)
    }

    override fun get(key: Int): String {
        return _context.getString(key)
    }
}
