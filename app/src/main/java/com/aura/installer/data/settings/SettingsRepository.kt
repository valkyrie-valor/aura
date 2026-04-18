package com.aura.installer.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "aura_settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private object Keys {
        val SERVER_URL = stringPreferencesKey("nexus_server_url")
        val REPOSITORY = stringPreferencesKey("nexus_repository")
        val DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LANGUAGE = stringPreferencesKey("language_override")
    }

    val settings: Flow<AppSettings> = context.dataStore.data.map { prefs ->
        AppSettings(
            nexusServerUrl = prefs[Keys.SERVER_URL] ?: "https://nexus.example.com",
            nexusRepository = prefs[Keys.REPOSITORY] ?: "apks-raw",
            useDynamicColor = prefs[Keys.DYNAMIC_COLOR] ?: true,
            themeMode = ThemeMode.entries.firstOrNull {
                it.name == prefs[Keys.THEME_MODE]
            } ?: ThemeMode.SYSTEM,
            languageOverride = LanguageOverride.entries.firstOrNull {
                it.name == prefs[Keys.LANGUAGE]
            } ?: LanguageOverride.SYSTEM,
        )
    }

    suspend fun setServerUrl(url: String) {
        context.dataStore.edit { it[Keys.SERVER_URL] = url }
    }

    suspend fun setRepository(repo: String) {
        context.dataStore.edit { it[Keys.REPOSITORY] = repo }
    }

    suspend fun setDynamicColor(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DYNAMIC_COLOR] = enabled }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    suspend fun setLanguageOverride(lang: LanguageOverride) {
        context.dataStore.edit { it[Keys.LANGUAGE] = lang.name }
    }
}
