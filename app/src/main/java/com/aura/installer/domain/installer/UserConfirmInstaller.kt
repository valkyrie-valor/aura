package com.aura.installer.domain.installer

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserConfirmInstaller @Inject constructor(
    @ApplicationContext private val context: Context,
) : Installer {

    override suspend fun install(apkUri: Uri): InstallResult {
        return try {
            val intent = Intent(Intent.ACTION_INSTALL_PACKAGE).apply {
                data = apkUri
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
                // Suppress "install from unknown source" interstitial
                putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                // Claim Play Store as installer so upgrades show "Update app?" directly
                putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, "com.android.vending")
            }
            context.startActivity(intent)
            InstallResult.Success
        } catch (e: Exception) {
            InstallResult.Failure(e.message ?: "Unknown error")
        }
    }
}
