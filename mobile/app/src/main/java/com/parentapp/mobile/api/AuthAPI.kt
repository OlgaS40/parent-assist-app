package com.parentapp.mobile.api

import com.parentapp.mobile.models.UserRequest
import com.parentapp.mobile.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST(RestAPI.SIGNUP)
    suspend fun signup(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST(RestAPI.LOGIN)
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST(RestAPI.LOGOFF)
    suspend fun signout() : Response<UserResponse>
}