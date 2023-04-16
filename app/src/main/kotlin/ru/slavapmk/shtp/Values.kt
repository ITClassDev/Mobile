package ru.slavapmk.shtp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.slavapmk.shtp.io.ServerAPI
import ru.slavapmk.shtp.io.dto.auth.User

object Values {
    private const val ENDPOINT_URL = "http://192.168.0.105:8080"
    lateinit var token: String
    lateinit var user: User

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val api: ServerAPI = retrofit.create(ServerAPI::class.java)
}