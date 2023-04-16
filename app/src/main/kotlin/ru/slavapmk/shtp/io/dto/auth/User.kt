package ru.slavapmk.shtp.io.dto.auth

data class User(
    val firstName: String,
    val groupId: Any,
    val id: Int,
    val lastName: String,
    val learningClass: Int,
    val middleName: String,
    val new_notifications: Boolean,
    val rating: Int,
    val systemAchievements: Any,
    val techStack: Any,
    val userAboutText: Any,
    val userAvatarPath: Any,
    val userGithub: Any,
    val userKaggle: Any,
    val userRole: Int,
    val userStepik: Any,
    val userTelegram: Any,
    val userWebsite: String
)