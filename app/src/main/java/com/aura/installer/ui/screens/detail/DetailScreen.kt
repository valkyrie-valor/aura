package com.aura.installer.ui.screens.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aura.installer.R
import com.aura.installer.data.api.AppItemDto
import com.aura.installer.data.api.ChrigaPagingSource
import com.aura.installer.data.download.DownloadState
import com.aura.installer.domain.installer.InstallResult
import com.aura.installer.ui.components.AuraPortalMark
import com.aura.installer.ui.components.PackageMonogramAvatar
import com.aura.installer.ui.components.VersionBadgePill

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val downloadState by viewModel.downloadState.collectAsState()
    val installResult by viewModel.installResult.collectAsState()

    val (displayName, version) = ChrigaPagingSource.resolveDisplayAndVersion(
        AppItemDto(name = viewModel.fileName.removeSuffix(".apk"))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail_title)) },
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        if (displayName.equals("Aura", ignoreCase = true)) {
                            AuraPortalMark(size = 56.dp)
                        } else {
                            PackageMonogramAvatar(displayName = displayName, size = 56.dp)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = displayName,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            if (!version.isNullOrBlank()) {
                                VersionBadgePill(version = version)
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    SpecRow(label = "BUNDLE", value = viewModel.fileName)
                    SpecRow(label = "FORMAT", value = "Android Package (APK)")
                }
            }

            when (val state = downloadState) {
                is DownloadState.Idle -> {
                    Button(
                        onClick = viewModel::startDownload,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.download_button),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
                is DownloadState.Progress -> {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            if (state.totalBytes > 0) {
                                LinearProgressIndicator(
                                    progress = { state.fraction },
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Text(
                                    text = "%.1f / %.1f MB".format(
                                        state.bytesDownloaded / 1_048_576.0,
                                        state.totalBytes / 1_048_576.0,
                                    ),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontFamily = FontFamily.Monospace,
                                )
                            } else {
                                CircularProgressIndicator()
                                Text(stringResource(R.string.downloading))
                            }
                        }
                    }
                }
                is DownloadState.Done -> {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            when (installResult) {
                                null -> {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                    Text(
                                        text = stringResource(R.string.install_launched),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }
                                is InstallResult.Success -> Text(
                                    text = stringResource(R.string.install_launched),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                                is InstallResult.Failure -> Text(
                                    text = "Error: ${(installResult as InstallResult.Failure).message}",
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                }
                is DownloadState.Error -> {
                    Text(
                        text = "Download failed: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                    )
                    Button(
                        onClick = viewModel::startDownload,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    ) {
                        Text(stringResource(R.string.retry_button))
                    }
                }
            }
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
