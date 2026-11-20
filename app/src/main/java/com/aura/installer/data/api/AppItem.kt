package com.aura.installer.data.api

data class AppItem(
    val name: String,
    val displayName: String,
    val version: String?,
    val repositoryPath: String,
    val fileSize: Long,
    val downloadUrl: String,
    val fileName: String,
)
