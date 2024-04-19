package ru.slavapmk.shtp.io.dto.achievements

data class AchievementPut(
    val eventType: String,
    val title: String,
    val description: String
)