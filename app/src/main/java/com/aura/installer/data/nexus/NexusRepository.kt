package com.aura.installer.data.nexus

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NexusRepository @Inject constructor(
    private val api: NexusApi,
) {
    fun searchApks(
        baseUrl: String,
        repository: String,
        query: String,
    ): Flow<PagingData<NexusAssetDto>> = Pager(
        config = PagingConfig(pageSize = 25, enablePlaceholders = false),
        pagingSourceFactory = {
            NexusPagingSource(api, baseUrl, repository, query)
        }
    ).flow
}
