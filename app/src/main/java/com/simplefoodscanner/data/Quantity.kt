package com.simplefoodscanner.data

data class Quantity(
    val foodCode: String,
    val name: String,
    val rank: Int,
    val level: Int,
    val value: Double,
    val unit: String
)