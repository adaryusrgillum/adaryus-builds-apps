param(
    [string]$JavaHome = "",
    [string]$AndroidSdkRoot = ""
)

$ErrorActionPreference = "Stop"
$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectRoot

function Test-JavaHome([string]$path) {
    if ([string]::IsNullOrWhiteSpace($path)) { return $false }
    $javaExe = Join-Path $path "bin\java.exe"
    if (-not (Test-Path $javaExe)) { return $false }

    $versionOutput = & $javaExe -version 2>&1
    $versionText = ($versionOutput | Select-Object -First 1)
    if ($versionText -match '"(\d+)(\.|$)') {
        $major = [int]$matches[1]
        return ($major -ge 17)
    }

    return $false
}

$candidates = @(
    $JavaHome,
    $env:JAVA_HOME,
    "C:\Users\adary\Downloads\APK\tooling\jdk-17.0.18+8",
    "C:\Program Files\Android\Android Studio\jbr",
    "C:\Users\$env:USERNAME\AppData\Local\AndroidStudio\android-studio\jbr"
) | Where-Object { -not [string]::IsNullOrWhiteSpace($_) }

$resolvedJavaHome = $null
foreach ($candidate in $candidates) {
    if (Test-JavaHome $candidate) {
        $resolvedJavaHome = $candidate
        break
    }
}

if (-not $resolvedJavaHome) {
    Write-Error "No valid Java 17+ runtime found. Pass -JavaHome or install Android Studio/JDK 17."
}

$env:JAVA_HOME = $resolvedJavaHome
$env:Path = "$resolvedJavaHome\bin;$env:Path"
Write-Host "Using JAVA_HOME=$resolvedJavaHome"

$sdkCandidates = @(
    $AndroidSdkRoot,
    $env:ANDROID_SDK_ROOT,
    $env:ANDROID_HOME,
    "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk",
    "C:\Users\adary\Downloads\APK\tooling\android-sdk"
) | Where-Object { -not [string]::IsNullOrWhiteSpace($_) }

$resolvedSdkRoot = $null
foreach ($candidate in $sdkCandidates) {
    if (Test-Path $candidate) {
        $resolvedSdkRoot = $candidate
        break
    }
}

if ($resolvedSdkRoot) {
    $env:ANDROID_HOME = $resolvedSdkRoot
    $env:ANDROID_SDK_ROOT = $resolvedSdkRoot
    Write-Host "Using ANDROID_SDK_ROOT=$resolvedSdkRoot"

    $localPropsPath = Join-Path $projectRoot "local.properties"
    $escapedSdk = $resolvedSdkRoot -replace '\\','\\\\'
    Set-Content -Path $localPropsPath -Value "sdk.dir=$escapedSdk" -Encoding ASCII
} else {
    Write-Warning "Android SDK not found automatically. Pass -AndroidSdkRoot to avoid build failure."
}

& ".\gradlew.bat" assembleRelease
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Host "Release APK output: app\build\outputs\apk\release\app-release.apk"
