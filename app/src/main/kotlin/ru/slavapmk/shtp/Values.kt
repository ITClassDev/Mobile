package ru.slavapmk.shtp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.slavapmk.shtp.io.ServerAPI
import ru.slavapmk.shtp.io.dto.user.UserFull

object Values {
    const val ENDPOINT_URL = "http://91.203.192.42:8080"
    lateinit var token: String
    lateinit var user: UserFull

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val api: ServerAPI = retrofit.create(ServerAPI::class.java)
}