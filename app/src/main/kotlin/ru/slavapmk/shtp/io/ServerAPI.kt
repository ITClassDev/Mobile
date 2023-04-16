package ru.slavapmk.shtp.io

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthLoginResponse
import ru.slavapmk.shtp.io.dto.auth.AuthMeResponse
import ru.slavapmk.shtp.io.dto.user.UserFull
import ru.slavapmk.shtp.io.dto.user.all.AllUsers
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest

interface ServerAPI {
    @POST("auth/login")
    suspend fun login(
        @Body authRequest: AuthLoginRequest
    ): AuthLoginResponse

    @GET("auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): AuthMeResponse

    @GET("users")
    suspend fun allUsers(
        @Header("Authorization") token: String
    ): AllUsers

    @PUT("users")
    suspend fun addUser(
        @Header("Authorization") token: String,
        @Body user: UserFull
    ): String

    @PATCH("users")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body user: PatchUserRequest
    ): String

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: Int
    ): UserFull

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    )

    @PATCH("users/avatar")
    suspend fun uploadAvatar(@Body image: RequestBody)

}