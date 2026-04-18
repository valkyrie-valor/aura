package com.aura.installer.ui.screens.local

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalScreen(
    viewModel: LocalViewModel = hiltViewModel(),
) {
    val recentPicks by viewModel.recentPicks.collectAsState()

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri: Uri? ->
        uri?.let { viewModel.installApk(it, it.lastPathSegment ?: "unknown.apk") }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.local_title)) }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { filePicker.launch(arrayOf("application/vnd.android.package-archive")) },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text(stringResource(R.string.pick_apk)) },
            )
        }
    ) { padding ->
        if (recentPicks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("No recent APKs — tap + to pick one")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 88.dp),
            ) {
                items(recentPicks, key = { it.uri }) { pick ->
                    ListItem(
                        headlineContent = { Text(pick.displayName) },
                        supportingContent = { Text("Tap to install") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.installApk(
                                    Uri.parse(pick.uri),
                                    pick.displayName,
                                )
                            }
                    )
                }
            }
        }
    }
}
