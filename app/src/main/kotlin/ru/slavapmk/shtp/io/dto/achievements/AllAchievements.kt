package ru.slavapmk.shtp.io.dto.achievements

data class AllAchievements(
    val achievements: Achievements,
    val base: Achievements,
    val status: Boolean
)