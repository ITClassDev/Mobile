package ru.slavapmk.shtp.io.dto.user.patch

data class PatchUserAdmin(
    val firstName: String,
    val lastName: String,
    val learningClass: Int,
    val middleName: String,
    val rating: Int
)