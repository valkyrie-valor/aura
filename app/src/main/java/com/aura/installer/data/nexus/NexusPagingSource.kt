package com.aura.installer.data.nexus

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NexusPagingSource(
    private val api: NexusApi,
    private val baseUrl: String,
    private val repository: String,
    private val query: String,
) : PagingSource<String, NexusAssetDto>() {

    private val searchUrl: String
        get() = baseUrl.trimEnd('/') + "/service/rest/v1/search/assets"

    override fun getRefreshKey(state: PagingState<String, NexusAssetDto>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, NexusAssetDto> {
        return try {
            val response = api.searchAssets(
                url = searchUrl,
                repository = repository,
                query = query.ifBlank { null },
                continuationToken = params.key,
            )
            val apkItems = response.items.filter { it.path.endsWith(".apk") }
            LoadResult.Page(
                data = apkItems,
                prevKey = null,
                nextKey = response.continuationToken,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
