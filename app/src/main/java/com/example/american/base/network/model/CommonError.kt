package com.example.american.base.network.model

sealed class CommonError {
    object NoNetwork : CommonError()
    object ServerError : CommonError()
    object ClientError : CommonError()
    object Forbidden : CommonError()
    object UnknownError : CommonError()
    object NotFound : CommonError()
    object Canceled : CommonError()
}
