package com.parentapp.mobile.api

sealed class RestAPI {
    companion object {
        const val LOGIN: String = "/api/auth/signin"
        const val SIGNUP: String = "/api/auth/signup"
        const val LOGOFF: String = "/api/auth/signout"
    }
}