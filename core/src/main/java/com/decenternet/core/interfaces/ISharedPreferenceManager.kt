package com.decenternet.core.interfaces

import java.lang.reflect.Type

interface ISharedPreferenceManager {

    fun <T> put(key:String, value:T) : Boolean
    fun <T> get(key:String, classToDeserialize:Class<T>) : T?
    fun <T> get(key:String, typeToDeserialize: Type) : T?
}