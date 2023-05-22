package ru.slavapmk.shtp.io

import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import ru.slavapmk.shtp.io.dto.events.mos.MosEvents

interface EventsAPI {
    @POST("graphql")
    fun getEvents(@Body body: RequestBody): Observable<MosEvents>
}