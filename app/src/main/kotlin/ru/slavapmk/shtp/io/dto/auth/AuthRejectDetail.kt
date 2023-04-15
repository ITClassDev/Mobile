package ru.slavapmk.shtp.io.dto.auth

data class AuthRejectDetail(
    val loc: List<Any>,
    val msg: String,
    val type: String
)