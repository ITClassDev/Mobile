package ru.slavapmk.shtp.io.dto.user

data class UserFull(
    val firstName: String,
    val groupId: Any,
    val id: Int,
    val lastName: String,
    val learningClass: Int,
    val middleName: String,
    val new_notifications: Boolean,
    val rating: Int,
    val systemAchievements: Any,
    val techStack: String?,
    val userAboutText: String,
    val userAvatarPath: String,
    val userGithub: String?,
    val userKaggle: String?,
    val userRole: Int,
    val userStepik: String?,
    val userTelegram: String?,
    val userWebsite: String?
)