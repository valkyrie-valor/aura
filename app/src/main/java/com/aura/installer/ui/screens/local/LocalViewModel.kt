package com.aura.installer.ui.screens.local

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.installer.data.local.RecentPickDao
import com.aura.installer.data.local.RecentPickEntity
import com.aura.installer.domain.installer.Installer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val installer: Installer,
    private val recentPickDao: RecentPickDao,
) : ViewModel() {

    val recentPicks = recentPickDao.getRecentPicks().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    fun installApk(uri: Uri, displayName: String) {
        viewModelScope.launch {
            recentPickDao.insert(RecentPickEntity(uri = uri.toString(), displayName = displayName))
            installer.install(uri)
        }
    }
}
