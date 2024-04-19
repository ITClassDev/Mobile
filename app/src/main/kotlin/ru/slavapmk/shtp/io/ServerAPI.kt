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
import ru.slavapmk.shtp.io.dto.achievements.Achievement
import ru.slavapmk.shtp.io.dto.achievements.AchievementPut
import ru.slavapmk.shtp.io.dto.achievements.Achievements
import ru.slavapmk.shtp.io.dto.auth.AuthLoginRequest
import ru.slavapmk.shtp.io.dto.auth.AuthLoginResponse
import ru.slavapmk.shtp.io.dto.groups.GroupPut
import ru.slavapmk.shtp.io.dto.notifications.AllNotifications
import ru.slavapmk.shtp.io.dto.notifications.PutNotification
import ru.slavapmk.shtp.io.dto.user.LeaderBoard
import ru.slavapmk.shtp.io.dto.user.UserFull
import ru.slavapmk.shtp.io.dto.user.get.User
import ru.slavapmk.shtp.io.dto.user.get.UserGroup
import ru.slavapmk.shtp.io.dto.user.patch.PatchAvatarResponse
import ru.slavapmk.shtp.io.dto.user.patch.PatchUserRequest
import ru.slavapmk.shtp.io.dto.user.put.UserPut

interface ServerAPI {
    @POST("api/v1/auth/login")
    fun login(
        @Body authRequest: AuthLoginRequest
    ): Observable<AuthLoginResponse>

    @GET("api/v1/auth/me")
    fun getMe(
        @Header("Authorization") token: String
    ): Observable<UserFull>

    @PATCH("api/v1/users")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body user: PatchUserRequest
    ): Observable<PatchResult>

    @GET("api/v1/users")
    fun allUsers(
        @Header("Authorization") token: String
    ): Observable<List<User>>

    @PUT("api/v1/users")
    fun createUser(
        @Header("Authorization") token: String,
        @Body user: UserPut
    ): Completable

    @GET("api/v1/groups")
    fun groupList(
        @Header("Authorization") token: String
    ): Observable<List<UserGroup>>

    @PUT("api/v1/groups")
    fun createGroup(
        @Header("Authorization") token: String,
        @Body group: GroupPut
    ): Completable

    @DELETE("api/v1/groups/{group_id}")
    fun deleteGroup(
        @Header("Authorization") token: String,
        @Path("group_id") groupId: String
    ): Completable

    @PATCH("api/v1/users/avatar")
    @Multipart
    fun updateAvatar(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part
    ): Observable<PatchAvatarResponse>

    @PUT("api/v1/achievements")
    @Multipart
    fun putAchievement(
        @Header("Authorization") token: String,
        @Part("achievement") achievement: AchievementPut,
        @Part confirmFile: MultipartBody.Part
    ): Observable<Achievement>

    @DELETE("api/v1/users/{user_id}")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Completable

    @GET("api/v1/achievements")
    fun getAchievements(@Header("Authorization") token: String): Observable<Achievements>

    @GET("api/v1/notifications/all")
    fun getNotifications(@Header("Authorization") token: String): Observable<AllNotifications>

    @PUT("api/v1/notifications")
    fun sendNotification(
        @Header("Authorization") token: String,
        @Body notification: PutNotification
    ): Completable

    @GET("api/v1/users/leaderboard")
    fun getLeaderBoard(): Observable<LeaderBoard>

//    @GET("programming/day_challenge")
//    fun getDailyChallenge(@Header("Authorization") token: String): Observable<DailyChallenge>

}
