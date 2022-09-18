package com.example.peak.data.network

import retrofit2.http.GET

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

interface PeakApi {

    @GET("rectangles")
    suspend fun getRectangles(): Rectangles

}