package ru.slavapmk.shtp.io.dto.auth

import ru.slavapmk.shtp.io.dto.user.UserFull

data class AuthMeResponse(
    val status: Boolean,
    val user: UserFull
)