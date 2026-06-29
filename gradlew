#!/usr/bin/env sh
# Gradle wrapper shim — calls the installed gradle if wrapper JAR missing
# If you have gradle wrapper JAR committed, it will run that instead.
# This script is simplified; it's compatible with Linux/macOS CI.
set -e
DIR=$(cd "$(dirname "$0")" && pwd)
if [ -x "$DIR/gradlew" ] && [ "$0" != "$DIR/gradlew" ]; then
  exec "$DIR/gradlew" "$@"
fi
if [ -f "$DIR/gradle/wrapper/gradle-wrapper.jar" ]; then
  java -jar "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
else
  # Fallback to system gradle if available
  if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
  else
    echo "Gradle wrapper JAR not found and 'gradle' is not installed."
    echo "To add the wrapper, run: gradle wrapper && git add gradlew gradle/wrapper && git commit -m 'Add gradle wrapper'"
    exit 1
  fi
fi
