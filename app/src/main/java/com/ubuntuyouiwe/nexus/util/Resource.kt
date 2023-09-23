package com.ubuntuyouiwe.nexus.util

sealed interface Resource<out T> {
    data class Success<T>(val data: T? = null) : Resource<T>

    data class Error<T>(val errorCode: String? = null ,val message: String) : Resource<T>

    data object Loading : Resource<Nothing>
}