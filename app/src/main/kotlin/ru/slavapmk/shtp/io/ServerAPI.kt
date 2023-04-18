package ru.slavapmk.shtp.io

import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.slavapmk.shtp.io.dto.PatchResult
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthLoginResponse
import ru.slavapmk.shtp.io.dto.auth.AuthMeResponse
import ru.slavapmk.shtp.io.dto.user.UserFull
import ru.slavapmk.shtp.io.dto.user.all.AllUsers
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest

interface ServerAPI {
    @POST("auth/login/")
    fun login(
        @Body authRequest: AuthLoginRequest
    ): Observable<AuthLoginResponse>

    @GET("auth/me/")
    fun getMe(
        @Header("Authorization") token: String
    ): Observable<AuthMeResponse>

    @GET("users/")
    fun allUsers(
        @Header("Authorization") token: String
    ): Observable<AllUsers>

    @PUT("users/")
    fun addUser(
        @Header("Authorization") token: String,
        @Body user: UserFull
    ): Observable<String>

    @PATCH("users/")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body user: PatchUserRequest
    ): Observable<PatchResult>

    @GET("users/{id}/")
    fun getUser(
        @Path("id") userId: Int
    ): Observable<UserFull>

    @DELETE("users/{id}/")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    )

    @PATCH("users/avatar/")
    suspend fun uploadAvatar(@Body image: RequestBody)

}