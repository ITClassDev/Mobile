package ru.slavapmk.shtp.io.dto.tasks

data class DailyChallenge(
    val author_id: Int,
    val func_name: Any,
    val id: Int,
    val input_types: Any,
    val is_day_challenge: Boolean,
    val memory_limit: Int,
    val tests: List<DailyChallengeTest>,
    val text: String,
    val time_limit: Int,
    val title: String
)