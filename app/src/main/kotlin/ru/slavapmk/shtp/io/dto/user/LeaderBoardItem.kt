package ru.slavapmk.shtp.io.dto.user

data class LeaderBoardItem(
    val firstName: String,
    val id: Int,
    val lastName: String,
    val rating: Int,
    val userAvatarPath: String
)