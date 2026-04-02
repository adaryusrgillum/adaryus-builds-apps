# Adaryus Builds Apps

Editorial Adaryus project with:

- A Jetpack Compose Android app branded as Adaryus Builds Apps
- A matching static web app in web/index.html
- Shared editorial premium styling and updated AI/business positioning

## Owner and contact

- Owner: Adaryus Gillum
- Location: West Virginia
- Email: adaryus@mail.com
- Phone: 681-837-2078
- Business: AdvertiseWV
- Business site: https://advertisewv.com
- Portfolio showcase: https://adaryus.com

## Project shape

- Android app categories for business systems, storefronts, media hubs, utility tools, learning apps, and secure platforms
- Softer editorial visual system with refined typography, calmer color, and buyer-focused messaging
- AI strategy section covering practical themes like local models, agent workflows, edge AI, voice, and security
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

## Note on Adaryus.com

This repository can reference Adaryus.com as the portfolio/showcase, but it does not contain the source code for Adaryus.com itself. If you want that site fixed directly, I’ll need the website files or the repo that powers it.

## Important note

This repository is a native Android/Jetpack Compose project plus a static web page in web/. It is not a Capacitor project, so npm and npx cap commands do not apply unless the project is intentionally migrated to Capacitor.
