package com.aura.installer.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aura.installer.R
import com.aura.installer.data.settings.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settings by viewModel.settings.collectAsState()
    var serverUrlDraft by rememberSaveable(settings.nexusServerUrl) {
        mutableStateOf(settings.nexusServerUrl)
    }
    var repositoryDraft by rememberSaveable(settings.nexusRepository) {
        mutableStateOf(settings.nexusRepository)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.settings_title)) }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            ListItem(headlineContent = { Text("Nexus Repository") })
            OutlinedTextField(
                value = serverUrlDraft,
                onValueChange = {
                    serverUrlDraft = it
                    viewModel.setServerUrl(it)
                },
                label = { Text(stringResource(R.string.settings_server_url)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                singleLine = true,
            )
            OutlinedTextField(
                value = repositoryDraft,
                onValueChange = {
                    repositoryDraft = it
                    viewModel.setRepository(it)
                },
                label = { Text(stringResource(R.string.settings_repository)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                singleLine = true,
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_dynamic_color)) },
                trailingContent = {
                    Switch(
                        checked = settings.useDynamicColor,
                        onCheckedChange = viewModel::setDynamicColor,
                    )
                }
            )
            ThemeMode.entries.forEach { mode ->
                ListItem(
                    headlineContent = {
                        Text(mode.name.lowercase().replaceFirstChar { it.uppercase() })
                    },
                    trailingContent = {
                        RadioButton(
                            selected = settings.themeMode == mode,
                            onClick = { viewModel.setThemeMode(mode) },
                        )
                    }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            ListItem(
                headlineContent = { Text(stringResource(R.string.settings_about)) },
                supportingContent = { Text("Aura v1.0.0 · Apache 2.0") },
            )
        }
    }
}
