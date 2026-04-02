# Adaryus Builds Apps

Neon-cyber Adaryus project with:

- A Jetpack Compose Android app branded as `Adaryus Builds Apps`
- A matching static webapp at [`web/index.html`](/c:/Users/adary/Downloads/APK/web/index.html)
- Shared dark neon styling inspired by the selected theme at `C:\Users\adary\Desktop\adaryus-themes\theme1-neon-cyber.html`

## Current project shape

- Android app categories for business systems, storefronts, media hubs, utility tools, learning apps, and secure platforms
- Neon cyan, purple, and green cyber theme across launcher assets, cards, hero sections, and motion accents
- Build request screen that prepares an email brief from inside the app
- Static one-page web experience with the same visual direction and product categories

## Key paths

- Android source: [`app/src/main/java/com/agentic/android`](/c:/Users/adary/Downloads/APK/app/src/main/java/com/agentic/android)
- Android resources: [`app/src/main/res`](/c:/Users/adary/Downloads/APK/app/src/main/res)
- Web app entry: [`web/index.html`](/c:/Users/adary/Downloads/APK/web/index.html)

## Build Android locally

On Windows PowerShell:

```powershell
$env:JAVA_HOME='c:\Users\adary\Downloads\APK\tooling\jdk-17.0.18+8'
$env:ANDROID_HOME='c:\Users\adary\Downloads\APK\tooling\android-sdk'
$env:ANDROID_SDK_ROOT='c:\Users\adary\Downloads\APK\tooling\android-sdk'
$env:GRADLE_USER_HOME='c:\Users\adary\Downloads\APK\.gradle-user-home'
.\tooling\gradle-8.7\bin\gradle.bat assembleRelease
```

Expected release output when the build completes:

- `app/build/outputs/apk/release/app-release.apk`

## Open the web app

Open [`web/index.html`](/c:/Users/adary/Downloads/APK/web/index.html) in a browser.

## Latest build result

The release build completed successfully on April 1, 2026.

- APK: `app/build/outputs/apk/release/app-release.apk`

## Note On Capacitor Commands

The current repo is a native Android/Jetpack Compose project plus a static web page in `web/`. It is not a Capacitor project yet, so commands like `npm run build`, `npx cap sync android`, and `npx cap open android` do not apply to this workspace unless we intentionally convert the web app into a Capacitor app later.
