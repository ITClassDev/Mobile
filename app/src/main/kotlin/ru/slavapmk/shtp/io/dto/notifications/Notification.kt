package ru.slavapmk.shtp.io.dto.notifications

data class Notification(
    val id: Int,
    val to_user: Int,
    val type: Int,
    val viewed: Boolean,
    val `data`: Data
)