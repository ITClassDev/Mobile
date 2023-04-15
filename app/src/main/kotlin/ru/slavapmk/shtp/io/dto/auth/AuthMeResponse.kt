package ru.slavapmk.shtp.io.dto.auth

data class AuthMeResponse(
    val status: Boolean,
    val user: User
)