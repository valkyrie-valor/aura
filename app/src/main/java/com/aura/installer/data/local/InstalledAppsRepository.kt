package com.aura.installer.data.local

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

data class InstalledApp(
    val packageName: String,
    val label: String,
    val versionName: String,
    val isSystemApp: Boolean,
)

@Singleton
class InstalledAppsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun getUserApps(): List<InstalledApp> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
            .map { info ->
                InstalledApp(
                    packageName = info.packageName,
                    label = pm.getApplicationLabel(info).toString(),
                    versionName = runCatching {
                        pm.getPackageInfo(info.packageName, 0).versionName ?: ""
                    }.getOrDefault(""),
                    isSystemApp = false,
                )
            }
            .sortedBy { it.label.lowercase() }
    }

    suspend fun uninstall(packageName: String) = withContext(Dispatchers.IO) {
        val intent = android.content.Intent(android.content.Intent.ACTION_DELETE).apply {
            data = android.net.Uri.parse("package:$packageName")
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
