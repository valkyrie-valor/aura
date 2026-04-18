package com.aura.installer.ui.screens.detail

import android.content.Context
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aura.installer.data.download.ApkDownloader
import com.aura.installer.data.download.DownloadState
import com.aura.installer.domain.installer.InstallResult
import com.aura.installer.domain.installer.Installer
import com.aura.installer.ui.navigation.AuraDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val downloader: ApkDownloader,
    private val installer: Installer,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val route: AuraDestination.Detail = savedStateHandle.toRoute()

    val downloadUrl: String = route.downloadUrl
    val fileName: String = route.fileName

    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState = _downloadState.asStateFlow()

    private val _installResult = MutableStateFlow<InstallResult?>(null)
    val installResult = _installResult.asStateFlow()

    fun startDownload() {
        viewModelScope.launch {
            downloader.download(downloadUrl, fileName).collect { state ->
                _downloadState.value = state
                if (state is DownloadState.Done) {
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        state.file,
                    )
                    val result = installer.install(uri)
                    _installResult.value = result
                }
            }
        }
    }
}
