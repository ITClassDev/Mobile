package ru.slavapmk.shtp.io.dto.user.get

data class User(
    val firstName: String,
    val groupId: Int,
    val groupName: String,
    val uuid: String,
    val lastName: String,
    val userAvatarPath: String
)