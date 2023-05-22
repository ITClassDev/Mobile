package ru.slavapmk.shtp.io.dto.events.mos

data class Event(
    val agent: Agent,
    val audiencesShort: List<String>,
    val emptySeats: Int,
    val finishedTime: String,
    val id: Int,
    val seats: Int,
    val startTime: String,
    val title: String
)