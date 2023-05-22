package ru.slavapmk.shtp.io

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import ru.slavapmk.shtp.io.dto.PatchResult
import ru.slavapmk.shtp.io.dto.achievements.AchievementsResponse
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthLoginResponse
import ru.slavapmk.shtp.io.dto.auth.AuthMeResponse
import ru.slavapmk.shtp.io.dto.groups.GroupPut
import ru.slavapmk.shtp.io.dto.notifications.AllNotifications
import ru.slavapmk.shtp.io.dto.notifications.PostNotification
import ru.slavapmk.shtp.io.dto.user.LeaderBoard
import ru.slavapmk.shtp.io.dto.user.get.UsersAll
import ru.slavapmk.shtp.io.dto.user.patch.PatchAvatarResponse
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest
import ru.slavapmk.shtp.io.dto.user.put.UserPut

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

    @GET("users/")
    fun allUsers(
        @Header("Authorization") token: String
    ): Observable<UsersAll>

    @PUT("users/")
    fun registerUser(
        @Header("Authorization") token: String,
        @Body user: UserPut
    ): Completable

    @PUT("users/groups/")
    fun createGroup(
        @Header("Authorization") token: String,
        @Body group: GroupPut
    ): Completable

    @DELETE("users/groups/{group_id}/")
    fun deleteGroup(
        @Header("Authorization") token: String,
        @Path("group_id") groupId: Int
    ): Completable

    @PATCH("users/avatar/")
    @Multipart
    fun updateAvatar(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Observable<PatchAvatarResponse>

    @DELETE("users/{user_id}/")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("user_id") userId: Int
    ): Completable

    @GET("achievements/")
    fun getAchievements(@Header("Authorization") token: String): Observable<AchievementsResponse>

    @GET("users/my_notifications/")
    fun getNotifications(@Header("Authorization") token: String): Observable<AllNotifications>

    @PUT("notifications/")
    fun sendNotification(
        @Header("Authorization") token: String,
        @Body notification: PostNotification
    ): Completable

    @GET("users/get_leaderboard/")
    fun getLeaderBoard(): Observable<LeaderBoard>

//    @GET("programming/day_challenge/")
//    fun getDailyChallenge(@Header("Authorization") token: String): Observable<DailyChallenge>

}