# Aura — Implementation Plan

Aura is a modern, native Android installer app inspired by [king-installer](../fork/king-installer/).
It installs APKs from the local file system **and** from a configurable Nexus Repository
Manager 3 (raw format) instance, using Jetpack Compose with Material 3 Expressive.

> Status: planning complete, implementation not yet started.
> Last updated: 2026-04-09.

---

## 1. Goals

- Native Android app, Kotlin + Jetpack Compose, Material 3 Expressive UI.
- Install APKs from:
  1. The local file system (via the system file picker).
  2. A configurable Nexus Repository Manager 3 raw repository (flat list + text search).
- Architecture that cleanly extends to: authenticated Nexus, multiple servers, additional
  APK sources (HTTP directories, F-Droid, …), and alternative installer backends
  (Shizuku, privileged/system) — without refactoring UI code.
- German + English localization, system-locale driven.
- Distributed via a static landing page on the user's own nginx server.

## 2. Non-goals (v1)

- No authentication to Nexus (architected for, not implemented).
- No signature / checksum verification.
- No download caching — always re-download.
- No silent / privileged install — stock Android does not allow it for sideloaded apps.
- No root or Shizuku support.
- No Play Store distribution.

## 3. Tech stack (locked)

| Area              | Choice |
|-------------------|--------|
| Language          | Kotlin 2.0.x, K2 compiler |
| Build             | AGP 8.7+, Gradle 8.10+, version catalog (`libs.versions.toml`) |
| SDK               | `min=30`, `target=36`, `compile=36` |
| UI                | Jetpack Compose BOM + Material3 **Expressive** (alpha) |
| Theming           | Dynamic color (Material You) + light/dark + Aura seed color fallback |
| DI                | Hilt |
| Async             | Coroutines + Flow |
| Networking        | Retrofit + OkHttp + kotlinx.serialization |
| Persistence       | Room (installed-app cache, recent picks), DataStore Proto (settings) |
| Navigation        | Compose Navigation, type-safe routes (kotlinx.serialization) |
| Paging            | Paging 3 (Nexus browse list) |
| i18n              | `values/strings.xml` + `values-de/strings.xml`, system-locale driven |
| Testing           | JUnit5, Turbine, MockWebServer, Compose UI tests, Roborazzi |
| Static analysis   | ktlint + detekt + Android Lint, wired to `./gradlew check` |
| CI                | GitHub Actions: lint + test + assemble on PR |
| License           | Apache-2.0 (clean-room rewrite, no king-installer code reused) |

## 4. App identity

- App name: **Aura**
- Package id: `com.aura.installer`
- Brand seed color: deep teal (placeholder, easily changed in `ui/theme/`)

## 5. Package layout (single module)

```
com.aura.installer/
├── AuraApplication.kt
├── di/                       # Hilt modules
│   ├── NetworkModule.kt
│   ├── DatabaseModule.kt
│   ├── InstallerModule.kt
│   └── RepositoryModule.kt
├── data/
│   ├── settings/             # DataStore + defaults loader (res/raw/aura_defaults.json)
│   ├── nexus/                # Retrofit service, DTOs, NexusRepository
│   ├── local/                # Room entities, DAOs, InstalledAppsRepository
│   └── download/             # OkHttp streaming download → cache dir
├── domain/
│   ├── model/                # ApkAsset, InstalledApp, DownloadProgress, InstallResult
│   ├── installer/            # Installer interface + implementations
│   │   ├── Installer.kt
│   │   ├── UserConfirmInstaller.kt   # PackageInstaller session, system UI
│   │   └── InstallerFactory.kt
│   └── source/               # ApkSource interface (Local | Nexus | future…)
├── ui/
│   ├── theme/                # Aura M3 Expressive theme, dynamic color
│   ├── components/           # Reusable expressive components
│   ├── navigation/
│   └── screens/
│       ├── home/             # NavigationSuiteScaffold host
│       ├── browse/           # Nexus list + search
│       ├── local/            # System file picker entry, recent picks
│       ├── installed/        # List, uninstall, batch
│       ├── detail/           # Asset detail → download progress → install
│       └── settings/         # Server URL, theme, language override, about
└── util/
```

## 6. Key architectural decisions

- **`ApkSource` interface.** `LocalApkSource`, `NexusApkSource` today; future
  `HttpDirectoryApkSource`, `FDroidApkSource`, … without touching UI. Browse/search
  screens depend on the abstraction, not on Nexus.
- **`Installer` interface.** `UserConfirmInstaller` is the only impl in v1. Adding
  Shizuku or a privileged installer later is one new class + one factory branch.
- **`AuthProvider` interface** baked into `NetworkModule` from day 1. Today:
  `NoAuthProvider`. Adding Basic / Bearer later is additive.
- **Multiple Nexus servers** — `ServerConfig` is a *list* in DataStore even though v1
  UI only edits a single entry. A future "manage servers" screen is purely additive.
- **No caching now**, but downloads stream through a `DownloadManager`-style class that
  exposes progress as `Flow<DownloadProgress>`. Switching to cached mode later means
  changing the sink, not the API.
- **Dynamic Nexus base URL** — base URL is per-request via OkHttp `HttpUrl`, not
  Retrofit's static `baseUrl`, so the user can change the server URL at runtime
  without rebuilding the Retrofit instance.

## 7. Defaults file

`app/src/main/res/raw/aura_defaults.json` — the Android equivalent of `appsettings.json`.
Loaded once on first launch, written into DataStore, only re-read if the user taps
"Reset to defaults" in Settings. To ship a different default, replace the file and rebuild.

```json
{
  "nexus": {
    "defaultServerUrl": "https://nexus.example.com",
    "defaultRepository": "apks-raw"
  },
  "ui": {
    "useDynamicColor": true
  }
}
```

> v1 ships with a **mocked** server URL and repository name; the user changes them in Settings.

## 8. Nexus integration

Nexus 3 raw repository, no auth, REST API:

- `GET {base}/service/rest/v1/search/assets?repository={repo}&q={query}`
- Response: `{ items: [ { downloadUrl, path, fileSize, checksum: { sha1, sha256 } } ], continuationToken }`
- Filter client-side to `path.endsWith(".apk")`.
- Pagination via `continuationToken`, exposed as a Compose `PagingSource`.
- Search field debounced 300 ms; empty query lists everything.

Flow: **tap asset → detail screen → Download (streamed to app cache dir) → hand `content://`
URI via `FileProvider` to `Installer` → user confirms in system dialog → result reported back.**

## 9. Permissions (`AndroidManifest.xml`)

- `INTERNET`
- `REQUEST_INSTALL_PACKAGES`
- `DELETE_PACKAGES` (uninstall flow; user confirms)
- `QUERY_ALL_PACKAGES` (Installed tab)
- `POST_NOTIFICATIONS` (Android 13+, download progress)
- `FOREGROUND_SERVICE` + `FOREGROUND_SERVICE_DATA_SYNC` (download service)

## 10. Screens (v1)

1. **Home** — `NavigationSuiteScaffold` with 4 destinations: Browse, Local, Installed, Settings.
2. **Browse (Nexus)** — top search field, paged list of APK assets, pull-to-refresh.
3. **Local** — "Pick APK" via `ACTION_OPEN_DOCUMENT`, recent picks list, multi-select for batch install.
4. **Installed** — installed user packages, tap → details / uninstall, multi-select for batch uninstall.
5. **Detail** — name, version, size, source, install button, download progress, post-install status.
6. **Settings** — server URL, repository name, theme (system/light/dark), dynamic color toggle,
   language override (system/de/en), about + license.

## 11. Distribution

- Static landing page under `web/` in this repo, M3 Expressive look matching the app
  (same seed color, large rounded shapes, fluid type).
- Single "Download Aura" button → `aura-vX.Y.Z.apk` next to the page.
- Optional "What's new" pulled from `CHANGELOG.md`.
- Hosted on the user's nginx server behind a custom domain (reverse proxy).
- Release flow: GitHub Actions on tag push → `assembleRelease` → publish APK + page artifact;
  user `scp`s / rsyncs to nginx (or actions deploys directly later).

## 12. Build sequence

Each step ends with a green build and a runnable APK.

1. **Bootstrap** — new Gradle project, version catalog, Hilt, Compose, M3 Expressive alpha,
   ktlint/detekt, GitHub Actions skeleton. Verify `./gradlew assembleDebug` on a blank `MainActivity`.
2. **Theme & nav shell** — Aura theme (dynamic color + seed fallback), `NavigationSuiteScaffold`
   with the 4 stub screens, German + English strings.
3. **Settings + defaults loader** — DataStore Proto, `res/raw/aura_defaults.json`,
   settings screen with server URL field.
4. **Installer abstraction** — `Installer` interface, `UserConfirmInstaller` via
   `PackageInstaller` session API, `InstallerFactory`. Smoke-test from the Local screen.
5. **Local screen** — file picker, recent picks (Room), single + batch install.
6. **Installed screen** — `PackageManager.getInstalledPackages`, list, uninstall flow.
7. **Networking layer** — Retrofit + OkHttp + `AuthProvider` (no-auth impl), dynamic base URL.
8. **Nexus browse** — `NexusApi`, `NexusApkSource` implementing `ApkSource`, Paging 3,
   search, detail screen, streaming download with progress, hand-off to `Installer`.
9. **Polish** — download notifications, foreground service, error/empty states,
   expressive motion, screenshots, README.
10. **Distribution page** + release workflow.
11. **i18n pass** — extract all strings, German translations, locale override in settings.

## 13. Open items / future work

- Authenticated Nexus (Basic / Bearer) — implement `BasicAuthProvider`, settings UI.
- Multiple Nexus servers — "manage servers" screen.
- Update detection (compare installed version vs newest in Nexus).
- Download caching with explicit invalidation.
- Shizuku-based silent install for power users.
- Privileged / system install path for AAOS / custom-ROM deployments.
- Signature pinning ("only install APKs signed by key X").
