# Product

<!-- impeccable:product-schema 1 -->

## Platform

adaptive

## Stack

Kotlin / Jetpack Compose (Material 3, Hilt, Room, Retrofit, Paging 3) for the Android application (`app/`), plus a self-contained static HTML/CSS distribution landing page (`web/index.html`).

## Users

Christoph (and invited homelab users) managing and installing self-hosted Android applications (`Aura`, `Flux`, `NetCal`, `Beanfreak`, `FuelSpotter`, `KingInstaller`, `microG`) on personal Android devices without relying on third-party app stores.

## Product Purpose

Aura is a personal, self-hosted Android app store and APK package installer. It connects to `chriga-api` (backed by a Nexus 3 raw repository) to browse, search, download, and install versioned Android packages, as well as installing local APKs from device storage and inspecting/uninstalling user-installed apps. The accompanying static web page (`web/index.html`) bootstraps the installation of the Aura APK itself.

## Positioning

A private, zero-telemetry Android package distribution client purpose-built for a personal Nexus + `chriga-api` homelab stack, combining authenticated repository browsing, streaming APK downloads with foreground notifications, and local package installation in one lightweight native client.

## Operating Context

Used on Android 11+ (API 30–36) phones and tablets with Material You dynamic color support, paired with `https://chriga-api.coolifyapp.chriga.me` (protected via `X-Api-Key`) and `https://nexus.coolifyapp.chriga.me/repository/raw/apks/`. Apps are released via GitHub Actions on `v*` git tags and uploaded as `<AppName>-v<Version>.apk`.

## Capabilities and Constraints

- Browse and search APKs from `chriga-api` (`/api/apps` and `/api/apps/{name}/download`), displaying clean human-readable app names, version badges, and file sizes.
- Pick and install local `.apk` files via Android's document picker, persisting recent picks in Room.
- Inspect user-installed applications on the device with version and package metadata and trigger uninstallation.
- Configure `chriga-api` server URL, encrypted API key (EncryptedSharedPreferences), theme mode (System / Light / Dark), and Material You dynamic color toggle.
- Full English and German localization (`values/strings.xml` and `values-de/strings.xml`).
- Expressive, distinctive brand mark (replacing the generic circle/monogram) with full Android 13+ Material You `<monochrome>` adaptive icon support.

## Brand Commitments

- Name: **Aura**
- Distinct, expressive emblem/logo replacing the plain circle icon across the Android app launcher icon, in-app branding, and the `web/index.html` bootstrap landing page.
- Material You dynamic coloring support on Android while maintaining a cohesive, high-craft visual identity across the native app and web landing page.

## Evidence on Hand

- Existing Android app codebase under `app/src/main/`
- Static distribution page under `web/index.html`
- Live backend endpoint at `https://chriga-api.coolifyapp.chriga.me/api/apps`
- Companion apps in the suite: `Flux`, `NetCal` (`net-cal`), `Beanfreak`

## Product Principles

1. **Version & Package Clarity:** Every package in the catalog immediately communicates its canonical title, exact semantic version, file size, and installation status.
2. **Tactile Package Craft:** Installing and managing self-hosted software should feel deliberate, trustworthy, and engineered—never like a generic debug list.
3. **Native Material Harmony:** Honor Android system capabilities—edge-to-edge layouts, adaptive navigation, and Material You themed monochrome icons—while giving Aura a recognizable signature mark.
