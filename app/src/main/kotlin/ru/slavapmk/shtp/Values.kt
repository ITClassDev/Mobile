package ru.slavapmk.shtp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.slavapmk.shtp.components.versions.VersionManager
import ru.slavapmk.shtp.components.versions.github.GithubVersionManager
import ru.slavapmk.shtp.io.EventsAPI
import ru.slavapmk.shtp.io.ServerAPI
import ru.slavapmk.shtp.io.dto.achievements.Achievements
import ru.slavapmk.shtp.io.dto.notifications.AllNotifications
import ru.slavapmk.shtp.io.dto.tasks.DailyChallenge
import ru.slavapmk.shtp.io.dto.user.LeaderBoard
import ru.slavapmk.shtp.io.dto.user.UserFull

object Values {
    const val ENDPOINT_URL = "https://shtp.1561.ru/api/"
    private const val EVENTS_ENDPOINT_URL = "https://regs.temocenter.ru/"
    private const val LOG_REQUESTS = true

    //System constants and variables

    const val APP_ID = "ru.slavapmk.shtp"
    const val AUTH_KEY_ID = "AUTH_TOKEN"

    lateinit var token: String
    lateinit var user: UserFull
    lateinit var leaderboard: LeaderBoard
    lateinit var achievements: Achievements
    lateinit var notifications: AllNotifications
    lateinit var dailyChallenge: DailyChallenge

    val httpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        httpLoggingInterceptor.level = when (LOG_REQUESTS) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    }

    val versionManager: VersionManager = GithubVersionManager("ITClassDev", "Mobile")

    val api: ServerAPI = Retrofit
        .Builder()
        .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
        .baseUrl(ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(ServerAPI::class.java)

    val eventsApi: EventsAPI = Retrofit
        .Builder()
        .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
        .baseUrl(EVENTS_ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(EventsAPI::class.java)
}
