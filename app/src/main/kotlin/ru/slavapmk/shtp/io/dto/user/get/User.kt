package ru.slavapmk.shtp.io.dto.user.get

data class User(
    val firstName: String,
    val groupId: String,
    val groupName: String,
    val uuid: String,
    val lastName: String,
    val userAvatarPath: String?
)