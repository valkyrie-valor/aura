package com.aura.installer.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.installer.data.settings.AppSettings
import com.aura.installer.data.settings.LanguageOverride
import com.aura.installer.data.settings.SettingsRepository
import com.aura.installer.data.settings.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {

    val settings = repository.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppSettings(),
    )

    fun setServerUrl(url: String) = viewModelScope.launch { repository.setServerUrl(url) }
    fun setRepository(repo: String) = viewModelScope.launch { repository.setRepository(repo) }
    fun setDynamicColor(enabled: Boolean) = viewModelScope.launch { repository.setDynamicColor(enabled) }
    fun setThemeMode(mode: ThemeMode) = viewModelScope.launch { repository.setThemeMode(mode) }
    fun setLanguageOverride(lang: LanguageOverride) = viewModelScope.launch { repository.setLanguageOverride(lang) }
}
