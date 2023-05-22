package ru.slavapmk.shtp.io.dto.achievements

data class AchievementPut(
    val description: String,
    val title: String,
    val type: Int
)