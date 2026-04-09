package com.aura.installer.data.settings

data class AppSettings(
    val nexusServerUrl: String = "https://nexus.example.com",
    val nexusRepository: String = "apks-raw",
    val useDynamicColor: Boolean = true,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val languageOverride: LanguageOverride = LanguageOverride.SYSTEM,
)

enum class ThemeMode { SYSTEM, LIGHT, DARK }
enum class LanguageOverride { SYSTEM, EN, DE }
