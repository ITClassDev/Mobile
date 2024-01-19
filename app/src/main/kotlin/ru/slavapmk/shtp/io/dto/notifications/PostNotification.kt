package ru.slavapmk.shtp.io.dto.notifications

data class PostNotification(
    val toGroup: String,
    val text: String,
    val type: Int
)