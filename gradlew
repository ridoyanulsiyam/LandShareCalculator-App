#!/bin/sh
set -e
GRADLE_VERSION="8.11.1"
GRADLE_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}/landshare-gradle"
GRADLE_DIR="$GRADLE_HOME/gradle-$GRADLE_VERSION"
GRADLE_BIN="$GRADLE_DIR/bin/gradle"
DIST_ZIP="$GRADLE_HOME/gradle-$GRADLE_VERSION-bin.zip"
DIST_URL="https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"

if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
fi

if [ ! -x "$GRADLE_BIN" ]; then
  mkdir -p "$GRADLE_HOME"
  echo "Downloading Gradle $GRADLE_VERSION..."
  if command -v curl >/dev/null 2>&1; then
    curl -L --fail --silent --show-error "$DIST_URL" -o "$DIST_ZIP"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DIST_URL" -O "$DIST_ZIP"
  else
    echo "Error: curl or wget is required to bootstrap Gradle." >&2
    exit 1
  fi
  rm -rf "$GRADLE_DIR"
  if command -v unzip >/dev/null 2>&1; then
    unzip -q "$DIST_ZIP" -d "$GRADLE_HOME"
  else
    echo "Error: unzip is required to extract Gradle." >&2
    exit 1
  fi
fi

exec "$GRADLE_BIN" "$@"
