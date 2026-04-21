package com.aura.installer.data.api

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
            val url = baseUrl.trimEnd('/') + "/api/apps"
            val dtos = api.listApps(url = url, query = query.ifBlank { null })
            val items = dtos.map { dto ->
                AppItem(
                    name = dto.name,
                    fileSize = dto.fileSize,
                    downloadUrl = "${baseUrl.trimEnd('/')}/api/apps/${dto.name}/download",
                    fileName = "${dto.name}.apk",
                )
            }
            LoadResult.Page(data = items, prevKey = null, nextKey = null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
