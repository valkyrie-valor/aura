package com.aura.installer.data.api

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState

class ChrigaPagingSource(
    private val api: ChrigaApi,
    private val baseUrl: String,
    private val query: String,
) : PagingSource<Unit, AppItem>() {

    override fun getRefreshKey(state: PagingState<Unit, AppItem>): Unit? = null

    override suspend fun load(params: LoadParams<Unit>): LoadResult<Unit, AppItem> {
        return try {
            val cleanBase = baseUrl.trimEnd('/')
            val url = "$cleanBase/api/apps"
            val dtos = api.listApps(url = url, query = query.ifBlank { null })
            val items = dtos.map { dto ->
                val (resolvedTitle, resolvedVersion) = resolveDisplayAndVersion(dto)
                val fullLabel = if (!resolvedVersion.isNullOrBlank()) {
                    "$resolvedTitle $resolvedVersion"
                } else {
                    resolvedTitle
                }
                val safeFileName = fullLabel.replace(' ', '-') + ".apk"
                val encodedDownloadKey = Uri.encode(dto.name.ifBlank { resolvedTitle })
                AppItem(
                    name = fullLabel,
                    displayName = resolvedTitle,
                    version = resolvedVersion,
                    repositoryPath = dto.path.ifBlank { "/apks/${resolvedTitle.lowercase()}" },
                    fileSize = dto.fileSize,
                    downloadUrl = "$cleanBase/api/apps/$encodedDownloadKey/download",
                    fileName = safeFileName,
                )
            }
            LoadResult.Page(data = items, prevKey = null, nextKey = null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private val VERSION_REGEX = Regex("""^(?<app>.+?)[-\s.]v?(?<ver>\d+(?:\.\d+)*)$""", RegexOption.IGNORE_CASE)

        private val CANONICAL_NAMES = mapOf(
            "aura" to "Aura",
            "flux" to "Flux",
            "net-cal" to "NetCal",
            "netcal" to "NetCal",
            "beanfreak" to "Beanfreak",
            "fuel-spotter" to "FuelSpotter",
            "fuelspotter" to "FuelSpotter",
            "kinginstaller" to "KingInstaller",
            "com.google.android.gms" to "microG GmsCore",
            "microg" to "microG GmsCore",
        )

        fun resolveDisplayAndVersion(dto: AppItemDto): Pair<String, String?> {
            if (dto.displayName.isNotBlank()) {
                return dto.displayName to dto.version?.takeIf { it.isNotBlank() }
            }
            val raw = dto.name.ifBlank {
                dto.path.substringAfterLast('/').removeSuffix(".apk")
            }
            val match = VERSION_REGEX.matchEntire(raw.trim())
            val rawApp = match?.groups?.get("app")?.value ?: raw
            val rawVer = match?.groups?.get("ver")?.value?.let { "v$it" } ?: dto.version
            val canonical = CANONICAL_NAMES[rawApp.lowercase()]
                ?: rawApp.split('-', '_').filter { it.isNotBlank() }.joinToString("") { part ->
                    part.replaceFirstChar { it.uppercase() }
                }.ifBlank { rawApp }
            return canonical to rawVer
        }
    }
}
