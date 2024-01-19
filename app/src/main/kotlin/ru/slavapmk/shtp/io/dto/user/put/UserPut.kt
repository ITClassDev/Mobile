package ru.slavapmk.shtp.io.dto.user.put

data class UserPut(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val learningClass: Int,
    val groupId: String
)