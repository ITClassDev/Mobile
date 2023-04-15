package ru.slavapmk.shtp

import ru.slavapmk.shtp.io.dto.auth.User

object Values {
    const val ENDPOINT_URL = "http://192.168.0.105:8080"
    lateinit var token: String
    lateinit var user: User
}