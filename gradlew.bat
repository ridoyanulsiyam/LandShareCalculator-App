@echo off
setlocal EnableExtensions
set "GRADLE_VERSION=8.11.1"
set "GRADLE_HOME=%USERPROFILE%\.gradle\landshare-gradle"
set "GRADLE_DIR=%GRADLE_HOME%\gradle-%GRADLE_VERSION%"
set "GRADLE_BIN=%GRADLE_DIR%\bin\gradle.bat"
set "DIST_ZIP=%GRADLE_HOME%\gradle-%GRADLE_VERSION%-bin.zip"
set "DIST_URL=https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip"
where gradle >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    gradle %*
    exit /b %ERRORLEVEL%
)
if not exist "%GRADLE_BIN%" (
    if not exist "%GRADLE_HOME%" mkdir "%GRADLE_HOME%"
    echo Downloading Gradle %GRADLE_VERSION%...
    powershell -NoProfile -ExecutionPolicy Bypass -Command "$ProgressPreference='SilentlyContinue'; Invoke-WebRequest -Uri '%DIST_URL%' -OutFile '%DIST_ZIP%'"
    if errorlevel 1 (
        echo Error: Could not download Gradle %GRADLE_VERSION%.
        exit /b 1
    )
    if exist "%GRADLE_DIR%" rmdir /s /q "%GRADLE_DIR%"
    powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -LiteralPath '%DIST_ZIP%' -DestinationPath '%GRADLE_HOME%' -Force"
    if errorlevel 1 (
        echo Error: Could not extract Gradle %GRADLE_VERSION%.
        exit /b 1
    )
)
call "%GRADLE_BIN%" %*
exit /b %ERRORLEVEL%
