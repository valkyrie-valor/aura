package com.aura.installer.ui.screens.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aura.installer.data.api.ChrigaRepository
import com.aura.installer.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val chrigaRepository: ChrigaRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val pagingData = combine(
        settingsRepository.settings,
        _query.debounce(300),
    ) { settings, q -> Pair(settings.chrigaApiUrl, q) }
        .flatMapLatest { (baseUrl, q) ->
            chrigaRepository.searchApps(baseUrl = baseUrl, query = q)
        }
        .cachedIn(viewModelScope)

    fun setQuery(q: String) {
        _query.value = q
    }
}
