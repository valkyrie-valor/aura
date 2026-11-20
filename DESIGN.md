---
name: Aura
description: Private Android package distribution system and Nexus/ChriGa API installer
colors:
  accent-viridian: "#006B5E"
  accent-viridian-deep: "#005248"
  accent-viridian-dark: "#5CE1CA"
  accent-viridian-deep-dark: "#7CEAD7"
  accent-prism: "#38B29F"
  accent-mint: "#67E8D3"
  accent-mint-soft: "#A7F3E6"
  bg-canvas: "#F2F6F5"
  bg-canvas-dark: "#081211"
  bg-surface: "#FFFFFF"
  bg-surface-dark: "#0F1E1C"
  bg-surface-tonal: "#E3ECEA"
  bg-surface-tonal-dark: "#162A27"
  bg-surface-accent: "#C9EFE8"
  bg-surface-accent-dark: "#123B34"
  bg-emblem-deep: "#071A17"
  bg-emblem-deep-dark: "#050E0D"
  ink-primary: "#0B1A18"
  ink-primary-dark: "#E4F0ED"
  ink-secondary: "#2E4440"
  ink-secondary-dark: "#B0C7C2"
  ink-muted: "#47605B"
  ink-muted-dark: "#88A39D"
  ink-on-primary: "#FFFFFF"
  ink-on-accent: "#00241F"
  signal-amber-bg: "#FEF3C7"
  signal-amber-bg-dark: "#3B230B"
  signal-amber-ink: "#78350F"
  signal-amber-ink-dark: "#FDE68A"
  border-hairline: "#C2D2CE"
  border-hairline-dark: "#213A36"
  border-strong: "#8FA9A3"
  border-strong-dark: "#355953"
typography:
  display:
    fontFamily: "\"Bricolage Grotesque\", -apple-system, BlinkMacSystemFont, sans-serif"
    fontSize: "2.75rem"
    fontWeight: 700
    lineHeight: 1.08
    letterSpacing: "-0.03em"
  headline:
    fontFamily: "\"Bricolage Grotesque\", -apple-system, BlinkMacSystemFont, sans-serif"
    fontSize: "1.75rem"
    fontWeight: 700
    lineHeight: 1.18
    letterSpacing: "-0.025em"
  title:
    fontFamily: "\"Bricolage Grotesque\", -apple-system, BlinkMacSystemFont, sans-serif"
    fontSize: "1.25rem"
    fontWeight: 700
    lineHeight: 1.3
    letterSpacing: "-0.02em"
  body:
    fontFamily: "\"Public Sans\", -apple-system, BlinkMacSystemFont, sans-serif"
    fontSize: "1rem"
    fontWeight: 400
    lineHeight: 1.55
  body-sm:
    fontFamily: "\"Public Sans\", -apple-system, BlinkMacSystemFont, sans-serif"
    fontSize: "0.9375rem"
    fontWeight: 400
    lineHeight: 1.55
  label:
    fontFamily: "\"JetBrains Mono\", ui-monospace, SFMono-Regular, Menlo, monospace"
    fontSize: "0.8125rem"
    fontWeight: 600
    lineHeight: 1.4
    letterSpacing: "0.02em"
rounded:
  xs: "6px"
  sm: "8px"
  md: "12px"
  lg: "16px"
  xl: "20px"
  pill: "9999px"
spacing:
  xs: "6px"
  sm: "12px"
  md: "18px"
  lg: "24px"
  xl: "32px"
  2xl: "56px"
components:
  button-primary:
    backgroundColor: "{colors.accent-viridian}"
    textColor: "{colors.ink-on-primary}"
    rounded: "{rounded.md}"
    padding: "16px 28px"
    typography: "{typography.body}"
  button-primary-hover:
    backgroundColor: "{colors.accent-viridian-deep}"
  version-pill:
    backgroundColor: "{colors.bg-surface-accent}"
    textColor: "{colors.ink-on-accent}"
    rounded: "{rounded.xs}"
    padding: "2px 8px"
    typography: "{typography.label}"
  surface-panel:
    backgroundColor: "{colors.bg-surface}"
    textColor: "{colors.ink-primary}"
    rounded: "{rounded.lg}"
    padding: "24px"
---

# Design System: Aura

## Overview

**Creative North Star: "The Architectural Package Specification Ledger"**

Aura is a private, self-hosted Android package distribution system connecting a personal Nexus 3 repository and `chriga-api` directly to Android devices. Its visual system refuses both the consumer app-store card carousel and the neon hacker terminal, drawing instead from architectural material specification binders and precision hardware release ledgers. Every package row carries its canonical title, semantic version pill, package identifier, and tabular file size with zero decorative filler.

At the center of the identity is the **Prismatic Aura Portal** emblem: a deep obsidian-viridian tile housing an octagonal aura halo, a rotated diamond portal frame, an inner architectural square, and a descending package chevron resting on a receiving tray. This emblem scales cleanly from the 108dp Android adaptive icon (including a dedicated single-color `<monochrome>` layer for Android 13+ Material You themed drawers) to in-app Compose headers and the static web bootstrap portal.

**Key Characteristics:**
- **Prismatic Aura Portal Emblem:** Geometric layered diamond-and-square portal framing a download core, replacing generic monogram circles.
- **Edition-Number Rigor:** Semantic versions (`v1.1.0`) and artifact names (`Aura-v1.1.0.apk`) are treated as first-class specification badges set in tabular monospace.
- **Hue-Tinted Mineral Surfaces:** Secondary ink and borders are tinted from the Mineral Viridian and Obsidian Teal ramp rather than flat neutral gray.
- **Native Material You Harmony:** On Android 12+, the Compose client adapts seamlessly to the user's dynamic wallpaper palette while preserving structural ledger rows and monospace version pills.

## Colors

The palette pairs warm mineral stone grounds in light mode and deep obsidian-teal grounds in dark mode with high-contrast Mineral Viridian and Prism Mint accents.

### Primary
- **Mineral Viridian** (`#006B5E` light / `#5CE1CA` dark): Primary action buttons, active filter borders, and focus rings.
- **Deep Viridian** (`#005248` light / `#7CEAD7` dark): Hover and active state for primary actions.
- **Tonal Mint Container** (`#C9EFE8` light / `#123B34` dark): Background container for semantic version pills (`v1.1.0`) and active category toggles, paired with **Deep Forest Ink** (`#00241F` / `#A7F3E6`).

### Secondary
- **Prism Teal** (`#38B29F`) & **Luminous Mint** (`#67E8D3`): Structural facet strokes inside the Prismatic Aura Portal emblem on **Deep Obsidian Emblem Ground** (`#071A17` / `#050E0D`).

### Tertiary
- **Amber Release Signal** (`#FEF3C7` light / `#3B230B` dark container, `#78350F` light / `#FDE68A` dark ink): Highlights major release milestones and update indicators.

### Neutral
- **Alabaster Stone Canvas** (`#F2F6F5` light / `#081211` dark): Primary page and screen ground.
- **Pure Ledger Surface** (`#FFFFFF` light / `#0F1E1C` dark): Resting surface for specification panels and package rows.
- **Tonal Slate Container** (`#E3ECEA` light / `#162A27` dark): Table headers, monogram avatars, and hover highlights.
- **Obsidian Ink** (`#0B1A18` / `#E4F0ED`), **Mineral Slate Ink** (`#2E4440` / `#B0C7C2`), and **Muted Viridian Slate** (`#47605B` / `#88A39D`): Three-step hue-tinted text hierarchy meeting WCAG AA contrast.
- **Hairline Sage Rule** (`#C2D2CE` light / `#213A36` dark) & **Strong Sage Border** (`#8FA9A3` / `#355953`): Crisp 1px structural dividers.

### Named Rules
**The Tinted-Neutral Rule.** Secondary text, table headers, and borders are always tinted with viridian-teal (`#2E4440`, `#47605B`, `#C2D2CE`); pure achromatic gray is never used.

## Typography

**Display Font:** Bricolage Grotesque (with system sans fallback on native Android)
**Body Font:** Public Sans (Roboto on native Android)
**Label/Mono Font:** JetBrains Mono (Monospace on native Android)

**Character:** Geometric architectural display headlines paired with an engineered body sans and strict tabular monospace metadata for version tags, package identifiers, and byte sizes.

### Hierarchy
- **Display** (700, `2.75rem`, line-height 1.08, tracking `-0.03em`): Primary application title in the specification hero.
- **Headline** (700, `1.75rem`, line-height 1.18, tracking `-0.025em`): Major section headings.
- **Title** (700, `1.25rem`, line-height 1.3, tracking `-0.02em`): Ledger panel titles and package row headers.
- **Body** (400/500, `1rem` and `0.9375rem`, line-height 1.55, max measure 62–72ch): Product descriptions and architectural specifications.
- **Label / Metadata** (600, `0.8125rem` / `13px`, line-height 1.4, `tabular-nums`): Semantic version badges (`v1.1.0`), package IDs (`com.aura.installer`), and file sizes (`3.9 MB`).

### Named Rules
**The Tabular Version Rule.** Every version number, byte count, and artifact path is set in monospace with `font-variant-numeric: tabular-nums` at a minimum of `13px` (`0.8125rem`).

## Layout

The web distribution portal uses a 1180px max-width shell (`24px` horizontal padding, `16px` on compact mobile) opening with an asymmetric 5:7 split (`hero-ledger-split`): the left column presents the Prismatic Aura Portal manifest and primary APK download CTA, while the right column presents the live interactive Homelab Package Registry Ledger. On viewports below `960px`, the split collapses cleanly into a single vertical stack. Inside the Android app, `NavigationSuiteScaffold` adapts automatically between a bottom navigation bar on phones and a navigation rail on tablets.

## Elevation & Depth

Aura conveys structure through **tonal surface layering and crisp 1px hairline borders** (`#C2D2CE` light / `#213A36` dark) rather than drop shadows. Containers rest flat on the canvas (`#F2F6F5`) with `#FFFFFF` fill and a single 1px border; interactive rows shift their background tone to `#E3ECEA` on hover or press.

### Named Rules
**The Single-Elevation Rule.** Declare elevation once: resting panels and package rows use a 1px structural border (`border-hairline`) and zero box-shadow, avoiding ghost cards.

## Shapes

- **Panels & Ledgers:** `16px` border radius (`--radius-lg`) with `overflow: hidden` so table headers and dividers clip cleanly to the container geometry.
- **Buttons & Inputs:** `12px` border radius (`--radius-md`) for primary actions and `8px` (`--radius-sm`) for compact filter controls.
- **Version Pills & Monograms:** `6px` to `8px` radius (`--radius-xs` / `--radius-sm`), echoing the faceted geometry of the Prismatic Aura Portal emblem.

## Components

### Buttons
- **Shape:** Medium architectural radius (`12px`).
- **Primary:** Mineral Viridian (`#006B5E`) background, white text, inline SVG download icon, and an embedded monospace artifact chip (`Aura-v1.1.0.apk`).
- **Hover / Focus:** Shifts to Deep Viridian (`#005248`) with a `-1px` vertical lift and `2px` viridian focus outline (`3px` offset).

### Chips
- **Version Pill:** Tonal Mint Container (`#C9EFE8`) with Deep Forest Ink (`#00241F`), `13px` JetBrains Mono (`600`), `2px 8px` padding, `6px` radius.
- **Filter Buttons:** Tonal Slate (`#E3ECEA`) at rest with `1px` border; active state (`aria-pressed="true"`) shifts to Tonal Mint Container (`#C9EFE8`) with a Mineral Viridian border.

### Cards / Containers
- **Corner Style:** `16px` radius (`14dp` in Android Compose).
- **Background:** Pure Ledger Surface (`#FFFFFF` light / `#0F1E1C` dark).
- **Shadow Strategy:** Flat by default; depth comes from `1px solid` hairline border and tonal contrast against `#F2F6F5`.
- **Internal Padding:** `20px` to `32px` on web panels; `14dp` to `20dp` on Android Compose ledger rows.

### Inputs / Fields
- **Style:** `1px solid` hairline border on canvas ground (`#F2F6F5`), `13px` monospace input text, `8px` radius on web / `12dp–14dp` in Compose.
- **Focus:** `2px solid` Mineral Viridian outline with `3px` offset.

### Navigation
- **Web Top Bar:** Sticky header with `1px` bottom border, housing the Prismatic Aura Portal emblem, title, `v1.1.0` badge, and underlined anchor links (`text-underline-offset: 5px`).
- **Android Navigation:** Material 3 `NavigationSuiteScaffold` switching between 4 top-level destinations (`Browse`, `Local`, `Installed`, `Settings`).

### Prismatic Aura Portal Emblem
- **Structure:** Layered vector mark combining an 8-point aura polygon, a 45° rotated diamond portal, an inner square frame, and a centered package chevron + receiving tray. Ships with a dedicated single-color stencil in `@drawable/ic_launcher_monochrome` for Android 13+ Material You themed icons.

## Do's and Don'ts

### Do:
- **Do** display every application with its canonical PascalCase/Title name (`Aura`, `Flux`, `NetCal`, `Beanfreak`, `FuelSpotter`) and a distinct `vX.Y.Z` semantic version badge.
- **Do** use `font-variant-numeric: tabular-nums` and monospace typography for all version strings, package IDs, and MB file sizes.
- **Do** provide a single-color transparent-ground vector `<monochrome>` drawable for every Android app icon so Material You launcher theming works across light and dark themes.

### Don't:
- **Don't** place uppercase eyebrow/kicker labels above section headings; let the heading carry its own weight.
- **Don't** use 3-column or 4-column grids of identical icon-heading-text cards; use structured specification tables and package ledger rows.
- **Don't** combine a 1px border with a wide soft drop shadow on cards or panels.
