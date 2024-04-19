package ru.slavapmk.shtp.io.dto.notifications

data class PutNotification(
    val toGroup: String,
    val type: Int,
    val `data`: Data,
)