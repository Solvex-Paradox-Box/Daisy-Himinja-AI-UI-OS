@echo off
REM Gradle wrapper shim for Windows - uses system gradle if wrapper JAR not present.
SET DIR=%~dp0
IF EXIST "%DIR%\gradle\wrapper\gradle-wrapper.jar" (
  java -jar "%DIR%\gradle\wrapper\gradle-wrapper.jar" %*
) ELSE (
  where gradle >nul 2>nul
  IF %ERRORLEVEL%==0 (
    gradle %*
  ) ELSE (
    echo Gradle wrapper JAR not found and gradle is not installed.
    echo Run: gradle wrapper && git add gradlew gradle\wrapper && git commit -m "Add gradle wrapper"
    exit /b 1
  )
)
