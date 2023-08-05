package com.ubuntuyouiwe.nexus.util

sealed class Resource<T> {
    class Success<T>(val data: T? = null) : Resource<T>()

    class Error<T>(val errorCode: String? = null ,val message: String) : Resource<T>()

    class Loading<T> : Resource<T>()
}