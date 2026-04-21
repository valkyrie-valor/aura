package com.aura.installer.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ChrigaApi {
    @GET
    suspend fun listApps(
        @Url url: String,
        @Query("q") query: String? = null,
    ): List<AppItemDto>
}
