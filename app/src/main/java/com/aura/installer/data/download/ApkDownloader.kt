package com.aura.installer.data.download

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

sealed interface DownloadState {
    data object Idle : DownloadState
    data class Progress(val bytesDownloaded: Long, val totalBytes: Long) : DownloadState {
        val fraction: Float get() = if (totalBytes > 0) bytesDownloaded.toFloat() / totalBytes else 0f
    }
    data class Done(val file: File) : DownloadState
    data class Error(val message: String) : DownloadState
}

@Singleton
class ApkDownloader @Inject constructor(
    @ApplicationContext private val context: Context,
    private val okHttpClient: OkHttpClient,
) {
    fun download(url: String, fileName: String): Flow<DownloadState> = flow {
        emit(DownloadState.Progress(0L, 0L))
        val cacheDir = File(context.cacheDir, "apks").also { it.mkdirs() }
        val destFile = File(cacheDir, fileName)

        val request = Request.Builder().url(url).build()
        try {
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    emit(DownloadState.Error("HTTP ${response.code}"))
                    return@flow
                }
                val body = response.body
                if (body == null) {
                    emit(DownloadState.Error("Empty response"))
                    return@flow
                }
                val totalBytes = body.contentLength()
                var bytesRead = 0L
                destFile.outputStream().use { out ->
                    body.byteStream().use { input ->
                        val buffer = ByteArray(8192)
                        var n: Int
                        while (input.read(buffer).also { n = it } != -1) {
                            out.write(buffer, 0, n)
                            bytesRead += n
                            emit(DownloadState.Progress(bytesRead, totalBytes))
                        }
                    }
                }
                emit(DownloadState.Done(destFile))
            }
        } catch (e: Exception) {
            emit(DownloadState.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}
