package ru.slavapmk.shtp.io.dto.user.all

data class UserMin(
    val firstName: String,
    val groupId: Int,
    val groupName: String,
    val id: Int,
    val lastName: String,
    val userAvatarPath: String
)