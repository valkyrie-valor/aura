package com.aura.installer.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChrigaRepository @Inject constructor(
    private val api: ChrigaApi,
) {
    fun searchApps(baseUrl: String, query: String): Flow<PagingData<AppItem>> = Pager(
        config = PagingConfig(pageSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { ChrigaPagingSource(api, baseUrl, query) },
    ).flow
}
