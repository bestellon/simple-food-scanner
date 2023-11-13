package com.simplefoodscanner.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkQuantity(
    val name: String,
    val rank: Int,
    val level: Int,
    val quantity: Double,
    val unit: String
)