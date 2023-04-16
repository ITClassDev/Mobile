package ru.slavapmk.shtp.io.dto.user.patch

data class PatchUserPassword(
    val confirmPassword: String,
    val currentPassword: String,
    val newPassword: String
)