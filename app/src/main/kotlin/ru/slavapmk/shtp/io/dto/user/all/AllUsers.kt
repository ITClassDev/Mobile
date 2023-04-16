package ru.slavapmk.shtp.io.dto.user.all

import ru.slavapmk.shtp.io.dto.user.all.UserGroup
import ru.slavapmk.shtp.io.dto.user.all.UserMin

data class AllUsers(
    val userGroups: List<UserGroup>,
    val users: List<UserMin>
)