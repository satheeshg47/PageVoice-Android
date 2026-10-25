#!/usr/bin/env sh
set -eu
GRADLE_VERSION=8.9
BASE_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
CACHE_DIR="${GRADLE_USER_HOME:-$HOME/.gradle}/wrapper/dists/manual-gradle-$GRADLE_VERSION"
GRADLE_HOME="$CACHE_DIR/gradle-$GRADLE_VERSION"
if [ ! -x "$GRADLE_HOME/bin/gradle" ]; then
  mkdir -p "$CACHE_DIR"
  ZIP="$CACHE_DIR/gradle.zip"
  if command -v curl >/dev/null 2>&1; then
    curl -fL "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -o "$ZIP"
  else
    echo "curl is required to download Gradle." >&2
    exit 1
  fi
  unzip -q -o "$ZIP" -d "$CACHE_DIR"
fi
exec "$GRADLE_HOME/bin/gradle" "$@"
