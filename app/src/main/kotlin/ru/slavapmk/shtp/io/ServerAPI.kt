package ru.slavapmk.shtp.io

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import ru.slavapmk.shtp.io.dto.PatchResult
import ru.slavapmk.shtp.io.dto.achievements.AllAchievements
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthLoginResponse
import ru.slavapmk.shtp.io.dto.auth.AuthMeResponse
import ru.slavapmk.shtp.io.dto.notifications.AllNotifications
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

    @PATCH("users/")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body user: PatchUserRequest
    ): Observable<PatchResult>

    @GET("achievements/")
    fun getAchievements(@Header("Authorization") token: String): Observable<AllAchievements>

    @GET("users/my_notifications/")
    fun getNotifications(@Header("Authorization") token: String): Observable<AllNotifications>

}