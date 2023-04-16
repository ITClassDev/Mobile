package ru.slavapmk.shtp.io.dto.user.patch

import ru.slavapmk.shtp.io.dto.user.SocialLinks

data class PatchUserRequest(
    val aboutText: String?,
    val admin: PatchUserAdmin?,
    val password: PatchUserPassword?,
    val socialLinks: SocialLinks?,
    val techStack: List<String>?
)