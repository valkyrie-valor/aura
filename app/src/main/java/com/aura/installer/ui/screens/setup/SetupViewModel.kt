package com.aura.installer.ui.screens.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.installer.data.security.ApiKeyStore
import com.aura.installer.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val apiKeyStore: ApiKeyStore,
) : ViewModel() {

    val isSetupComplete: StateFlow<Boolean?> = combine(
        settingsRepository.settings,
        apiKeyStore.hasApiKey,
    ) { settings, hasKey ->
        settings.chrigaApiUrl.isNotBlank() && hasKey
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    fun saveSetup(apiUrl: String, apiKey: String) {
        viewModelScope.launch {
            settingsRepository.setChrigaApiUrl(apiUrl.trimEnd('/'))
            apiKeyStore.saveApiKey(apiKey)
        }
    }
}
