package ru.slavapmk.shtp.io.dto.achievements

data class Achievement(
    val uuid: String,
    val eventType: String,
    val title: String,
    val description: String,
    val attachmentName: String,
    val created_at: String,
    val points: Int,
    val accepted_at: String,
    val accepted_by: String
)