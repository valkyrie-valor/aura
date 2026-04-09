package com.aura.installer.data.nexus

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

@Serializable
data class NexusSearchResponse(
    val items: List<NexusAssetDto> = emptyList(),
    val continuationToken: String? = null,
)

@Serializable
data class NexusAssetDto(
    val id: String = "",
    val downloadUrl: String = "",
    val path: String = "",
    val repository: String = "",
    @SerialName("fileSize") val fileSize: Long = 0L,
    val checksum: NexusChecksum? = null,
)

@Serializable
data class NexusChecksum(
    val sha1: String = "",
    val sha256: String = "",
)

interface NexusApi {
    @GET
    suspend fun searchAssets(
        @Url url: String,
        @Query("repository") repository: String,
        @Query("q") query: String? = null,
        @Query("continuationToken") continuationToken: String? = null,
    ): NexusSearchResponse
}
