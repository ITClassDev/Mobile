package ru.slavapmk.shtp.io

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthLoginResponse
import ru.slavapmk.shtp.io.dto.auth.AuthMeResponse

interface ServerAPI {
    @POST("auth/login")
    suspend fun authLogin(@Body authRequest: AuthLoginRequest): AuthLoginResponse

    @GET("auth/me")
    suspend fun authMe(@Header("Authorization") token: String): AuthMeResponse
}