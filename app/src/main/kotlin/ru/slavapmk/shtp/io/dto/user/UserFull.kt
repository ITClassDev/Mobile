package ru.slavapmk.shtp.io.dto.user

import ru.slavapmk.shtp.io.dto.user.get.UserGroup

data class UserFull(
    val firstName: String,
    val uuid: String,
    val lastName: String,
    val learningClass: Int,
    val patronymicName: String?,
    val newNotifications: Boolean,
    val rating: Int,
    val systemAchievements: Any,
    val techStack: String?,
    val aboutText: String?,
    var avatarPath: String,
    val telegram: String?,
    val website: String?,
    val github: String?,
    val kaggle: String?,
    val stepik: String?,
    val role: String,
    val nickName: String?,
    val shtpMaintainer: Boolean?,
    val groupId: String,
    val group: UserGroup
)