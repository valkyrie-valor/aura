package com.aura.installer.ui.screens.installed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.installer.data.local.InstalledApp
import com.aura.installer.data.local.InstalledAppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstalledViewModel @Inject constructor(
    private val repository: InstalledAppsRepository,
) : ViewModel() {

    private val _apps = MutableStateFlow<List<InstalledApp>>(emptyList())
    val apps = _apps.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true
            _apps.value = repository.getUserApps()
            _isLoading.value = false
        }
    }

    fun uninstall(packageName: String) {
        viewModelScope.launch {
            repository.uninstall(packageName)
        }
    }
}
