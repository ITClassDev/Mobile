package ru.slavapmk.shtp.io.dto.auth

data class AuthLoginResponse(
    val accessToken: String,
    val detail: List<AuthRejectDetail>,
    val tokenType: String
)