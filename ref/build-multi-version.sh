#!/bin/bash
# Build script for multiple Minecraft versions
# Usage: ./build-multi-version.sh [version1] [version2] ...
# If no versions specified, builds all configured versions

set -e

# Define version configurations (format: yarn_mappings|loader_version|fabric_version)
declare -A VERSIONS
VERSIONS[1.21.7]="1.21.7+build.2|0.17.3|0.129.0+1.21.7"
VERSIONS[1.21.9]="1.21.9+build.2|0.18.2|0.138.0+1.21.9"
VERSIONS[1.21.10]="1.21.10+build.2|0.18.2|0.138.0+1.21.10"

# Parse versions to build (or use all if none specified)
if [ $# -eq 0 ]; then
    BUILD_VERSIONS=("${!VERSIONS[@]}")
else
    BUILD_VERSIONS=("$@")
fi

echo "=========================================="
echo "Multi-Version Build Script"
echo "=========================================="
echo "Building for versions: ${BUILD_VERSIONS[*]}"
echo ""

# Backup original files
BACKUP_DIR=".build-backup"
mkdir -p "$BACKUP_DIR"
if [ ! -f "$BACKUP_DIR/gradle.properties" ]; then
    cp gradle.properties "$BACKUP_DIR/gradle.properties"
    cp src/main/resources/fabric.mod.json "$BACKUP_DIR/fabric.mod.json" 2>/dev/null || true
    echo "✓ Backed up original files"
fi

# Create output directory
OUTPUT_DIR="build/multi-version"
mkdir -p "$OUTPUT_DIR"

# Build each version
SUCCESS_COUNT=0
FAIL_COUNT=0

for MC_VERSION in "${BUILD_VERSIONS[@]}"; do
    if [ -z "${VERSIONS[$MC_VERSION]}" ]; then
        echo "⚠ Error: Unknown version $MC_VERSION"
        echo "  Available versions: ${!VERSIONS[@]}"
        ((FAIL_COUNT++))
        continue
    fi
    
    echo "=========================================="
    echo "Building for Minecraft $MC_VERSION"
    echo "=========================================="
    
    # Parse version config
    IFS='|' read -r YARN_MAPPINGS LOADER_VERSION FABRIC_VERSION <<< "${VERSIONS[$MC_VERSION]}"
    
    # Update gradle.properties using sed (works on both macOS and Linux)
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        sed -i '' "s/^minecraft_version=.*/minecraft_version=$MC_VERSION/" gradle.properties
        sed -i '' "s/^yarn_mappings=.*/yarn_mappings=$YARN_MAPPINGS/" gradle.properties
        sed -i '' "s/^loader_version=.*/loader_version=$LOADER_VERSION/" gradle.properties
        sed -i '' "s|^fabric_version=.*|fabric_version=$FABRIC_VERSION|" gradle.properties
    else
        # Linux
        sed -i "s/^minecraft_version=.*/minecraft_version=$MC_VERSION/" gradle.properties
        sed -i "s/^yarn_mappings=.*/yarn_mappings=$YARN_MAPPINGS/" gradle.properties
        sed -i "s/^loader_version=.*/loader_version=$LOADER_VERSION/" gradle.properties
        sed -i "s|^fabric_version=.*|fabric_version=$FABRIC_VERSION|" gradle.properties
    fi
    
    # Clean and build
    echo "Cleaning..."
    if ./gradlew clean --no-daemon > /dev/null 2>&1; then
        echo "Building..."
        if ./gradlew build --no-daemon; then
            # Find and copy the jar
            JAR_FILE=$(find build/libs -name "selfcare-hive-*.jar" -not -name "*-sources.jar" -not -name "*-dev.jar" | head -1)
            if [ -n "$JAR_FILE" ]; then
                OUTPUT_FILE="$OUTPUT_DIR/selfcare-hive-${MC_VERSION}.jar"
                cp "$JAR_FILE" "$OUTPUT_FILE"
                echo "✓ Successfully built $MC_VERSION"
                echo "  Output: $OUTPUT_FILE"
                ((SUCCESS_COUNT++))
            else
                echo "✗ Build succeeded but jar not found"
                ((FAIL_COUNT++))
            fi
        else
            echo "✗ Build failed for $MC_VERSION"
            ((FAIL_COUNT++))
        fi
    else
        echo "✗ Clean failed for $MC_VERSION"
        ((FAIL_COUNT++))
    fi
    echo ""
done

# Restore original files
if [ -f "$BACKUP_DIR/gradle.properties" ]; then
    cp "$BACKUP_DIR/gradle.properties" gradle.properties
    echo "✓ Restored gradle.properties"
fi

# Summary
echo "=========================================="
echo "Build Summary"
echo "=========================================="
echo "Successful: $SUCCESS_COUNT"
echo "Failed: $FAIL_COUNT"
echo ""

if [ $SUCCESS_COUNT -gt 0 ]; then
    echo "Built jars are in: $OUTPUT_DIR/"
    ls -lh "$OUTPUT_DIR/" 2>/dev/null || echo "No jars found"
fi

if [ $FAIL_COUNT -gt 0 ]; then
    exit 1
fi
