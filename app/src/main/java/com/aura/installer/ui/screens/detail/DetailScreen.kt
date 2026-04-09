package com.aura.installer.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aura.installer.R
import com.aura.installer.data.download.DownloadState
import com.aura.installer.domain.installer.InstallResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val downloadState by viewModel.downloadState.collectAsState()
    val installResult by viewModel.installResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewModel.fileName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = viewModel.fileName,
                style = MaterialTheme.typography.headlineSmall,
            )

            when (val state = downloadState) {
                is DownloadState.Idle -> {
                    Button(
                        onClick = viewModel::startDownload,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(stringResource(R.string.download_button))
                    }
                }
                is DownloadState.Progress -> {
                    if (state.totalBytes > 0) {
                        LinearProgressIndicator(
                            progress = { state.fraction },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            "%.1f / %.1f MB".format(
                                state.bytesDownloaded / 1_048_576.0,
                                state.totalBytes / 1_048_576.0,
                            )
                        )
                    } else {
                        CircularProgressIndicator()
                        Text(stringResource(R.string.downloading))
                    }
                }
                is DownloadState.Done -> {
                    when (installResult) {
                        null -> {
                            CircularProgressIndicator()
                            Text(stringResource(R.string.install_launched))
                        }
                        is InstallResult.Success -> Text(stringResource(R.string.install_launched))
                        is InstallResult.Failure -> Text(
                            "Error: ${(installResult as InstallResult.Failure).message}",
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                is DownloadState.Error -> {
                    Text(
                        "Download failed: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                    )
                    Button(
                        onClick = viewModel::startDownload,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(stringResource(R.string.retry_button))
                    }
                }
            }
        }
    }
}
