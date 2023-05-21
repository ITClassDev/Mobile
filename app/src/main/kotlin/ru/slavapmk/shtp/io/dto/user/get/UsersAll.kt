package ru.slavapmk.shtp.io.dto.user.get

data class UsersAll(
    val userGroups: List<UserGroup>,
    val users: List<User>
)