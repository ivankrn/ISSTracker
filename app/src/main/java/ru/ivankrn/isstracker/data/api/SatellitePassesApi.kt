package ru.ivankrn.isstracker.data.api

import java.math.BigDecimal
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

import ru.ivankrn.isstracker.data.response.SatellitePassResponse

interface SatellitePassesApi {

    @GET("/passes/{noradId}")
    suspend fun getSatellitePassesForNoradId(
        @Path("noradId") noradId: Int,
        @Query("lat") latitude: BigDecimal,
        @Query("lon") longitude: BigDecimal,
        @Query("limit") limit: Int?,
        @Query("days") days: Int?,
        @Query("visible_only") visibleOnly: Boolean?
    ): List<SatellitePassResponse>

}