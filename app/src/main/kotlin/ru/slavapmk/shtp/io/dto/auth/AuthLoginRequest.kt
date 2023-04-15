package ru.slavapmk.shtp.io.dto.auth

data class AuthLoginRequest(
    val email: String,
    val password: String
)