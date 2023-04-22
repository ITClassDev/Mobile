package ru.slavapmk.shtp.io.dto.achievements

data class Achievement(
    val accepted_by: Int,
    val attachment_file_name: String,
    val description: String,
    val id: Int,
    val points: Int,
    val received_at: String,
    val title: String,
    val to_user: Int,
    val type: Int
)