package ru.slavapmk.shtp.io.dto.notifications

data class PostNotification(
    val groupId: Int,
    val text: String,
    val type: Int
)