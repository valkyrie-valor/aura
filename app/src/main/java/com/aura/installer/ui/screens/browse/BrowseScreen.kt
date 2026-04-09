package com.aura.installer.ui.screens.browse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.aura.installer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(
    onAssetClick: (downloadUrl: String, fileName: String) -> Unit = { _, _ -> },
    viewModel: BrowseViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsState()
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text(stringResource(R.string.browse_title)) })
                OutlinedTextField(
                    value = query,
                    onValueChange = viewModel::setQuery,
                    placeholder = { Text(stringResource(R.string.search_hint)) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
        }
    ) { padding ->
        when {
            pagingItems.loadState.refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) { CircularProgressIndicator() }
            }
            pagingItems.loadState.refresh is LoadState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Error loading APKs. Check server settings.")
                }
            }
            pagingItems.itemCount == 0 && pagingItems.loadState.refresh !is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) { Text("No APKs found") }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = pagingItems.itemKey { it.id },
                    ) { index ->
                        val item = pagingItems[index]
                        if (item != null) {
                            ListItem(
                                headlineContent = { Text(item.path.substringAfterLast('/')) },
                                supportingContent = {
                                    Text(
                                        if (item.fileSize > 0)
                                            "%.1f MB".format(item.fileSize / 1_048_576.0)
                                        else "Size unknown"
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onAssetClick(
                                            item.downloadUrl,
                                            item.path.substringAfterLast('/'),
                                        )
                                    }
                            )
                        }
                    }
                    if (pagingItems.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) { CircularProgressIndicator() }
                        }
                    }
                }
            }
        }
    }
}
