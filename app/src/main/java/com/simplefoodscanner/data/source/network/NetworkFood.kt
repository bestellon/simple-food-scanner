package com.simplefoodscanner.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkFood(
    val code: String,
    val name: String,
    val brands: String,
    val nutriscore: String,
    val quantities: List<NetworkQuantity>
)