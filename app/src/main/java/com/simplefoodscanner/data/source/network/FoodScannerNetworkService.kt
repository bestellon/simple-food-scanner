package com.simplefoodscanner.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path

interface FoodScannerNetworkService {
    @GET("food/{code}")
    suspend fun getFood(@Path("code") code: String): NetworkFood

}