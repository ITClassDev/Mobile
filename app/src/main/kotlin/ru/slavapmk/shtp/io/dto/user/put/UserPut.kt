package ru.slavapmk.shtp.io.dto.user.put

data class UserPut(
    val email: String,
    val firstName: String,
    val groupId: Int,
    val lastName: String,
    val learningClass: Int,
    val password: String,
    val userRole: Int
)