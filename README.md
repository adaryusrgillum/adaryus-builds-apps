# Adaryus Builds Apps

Neon-cyber Adaryus project with:

- A Jetpack Compose Android app branded as Adaryus Builds Apps
- A matching static web app in web/index.html
- Shared dark neon styling inspired by the selected theme

## Project shape

- Android app categories for business systems, storefronts, media hubs, utility tools, learning apps, and secure platforms
- Neon cyan, purple, and green cyber theme across launcher assets, cards, hero sections, and motion accents
- Build request screen that prepares an email brief from inside the app
- Static one-page web experience with the same visual direction and product categories

## Key paths

- Android source: app/src/main/java/com/agentic/android
- Android resources: app/src/main/res
- Web app entry: web/index.html

## Build Android locally (Windows)

The easiest way is to use the included build helper, which works even if JAVA_HOME is broken:

```powershell
.\build-release.ps1
```

Optional explicit values:

```powershell
.\build-release.ps1 -JavaHome "C:\Path\To\JDK17" -AndroidSdkRoot "C:\Path\To\Android\Sdk"
```

Expected release output:

- app/build/outputs/apk/release/app-release.apk

## Open the web app

Open web/index.html in a browser.

## Important note

This repository is a native Android/Jetpack Compose project plus a static web page in web/. It is not a Capacitor project, so npm and npx cap commands do not apply unless the project is intentionally migrated to Capacitor.
