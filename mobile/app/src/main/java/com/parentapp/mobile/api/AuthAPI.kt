package com.parentapp.mobile.api

import com.parentapp.mobile.models.UserRequest
import com.parentapp.mobile.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("/api/auth/signup")
    suspend fun signup(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("/api/auth/signin")
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("/api/auth/signout")
    suspend fun signout() : Response<UserResponse>
}