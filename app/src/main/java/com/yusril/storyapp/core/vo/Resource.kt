package com.yusril.storyapp.core.vo

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object{
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(msg: String?): Resource<T> = Resource(Status.ERROR, null, msg)

        fun <T> loading(): Resource<T> = Resource(Status.LOADING, null, null)
    }
}
