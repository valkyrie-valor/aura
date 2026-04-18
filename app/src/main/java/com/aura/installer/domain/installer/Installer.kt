package com.aura.installer.domain.installer

import android.net.Uri

interface Installer {
    suspend fun install(apkUri: Uri): InstallResult
}

sealed interface InstallResult {
    data object Success : InstallResult
    data class Failure(val message: String) : InstallResult
}
