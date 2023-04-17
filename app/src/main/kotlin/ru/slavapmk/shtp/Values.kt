package ru.slavapmk.shtp

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.slavapmk.shtp.io.ServerAPI
import ru.slavapmk.shtp.io.dto.user.UserFull

object Values {
    const val ENDPOINT_URL = "http://91.203.192.42:8080"
    const val APP_ID = "ru.slavapmk.shtp"
    const val AUTH_ID = "AUTH_TOKEN"

    lateinit var token: String
    lateinit var user: UserFull

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val api: ServerAPI = retrofit.create(ServerAPI::class.java)
}