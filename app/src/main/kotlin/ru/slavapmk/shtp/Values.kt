package ru.slavapmk.shtp

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient;
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.slavapmk.shtp.io.ServerAPI
import ru.slavapmk.shtp.io.dto.user.UserFull


object Values {
    const val ENDPOINT_URL = "https://shtp.1561.ru/api"
    const val APP_ID = "ru.slavapmk.shtp"
    const val AUTH_ID = "AUTH_TOKEN"

    lateinit var token: String
    lateinit var user: UserFull

    private var httpLoggingInterceptor = HttpLoggingInterceptor()
    init {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
        .baseUrl(ENDPOINT_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val api: ServerAPI = retrofit.create(ServerAPI::class.java)
}
