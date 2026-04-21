package com.aura.installer.data.api

import kotlinx.serialization.Serializable

@Serializable
data class AppItemDto(
    val name: String = "",
    val path: String = "",
    val fileSize: Long = 0L,
)
