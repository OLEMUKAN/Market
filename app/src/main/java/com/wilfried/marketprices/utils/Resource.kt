package com.wilfried.marketprices.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T> Type of the resource data
 * */
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
} 