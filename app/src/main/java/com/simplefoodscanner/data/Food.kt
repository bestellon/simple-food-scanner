package com.simplefoodscanner.data

import java.time.LocalDateTime

data class Food(
    val code: String,
    val name: String,
    val brands: String,
    val nutriscore: String,
    val date: LocalDateTime
)