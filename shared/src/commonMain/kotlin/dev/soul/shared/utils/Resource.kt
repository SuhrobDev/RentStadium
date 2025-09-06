package dev.soul.shared.utils

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
sealed class Resource<T>(val data: T? = null, val message: NetworkError? = null) {

    @ObjCName(name = "ResourceSuccess")
    class Success<T>(data: T) : Resource<T>(data)

    @ObjCName(name = "ResourceError")
    class Error<T>(message: NetworkError, data: T? = null) : Resource<T>(data, message)

}


inline fun <T> Resource<T>.onSuccess(action: (T?) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        action(data)
    }
    return this
}

inline fun <T> Resource<T>.onError(action: (NetworkError?) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        action(message)
    }
    return this
}
