# Multi-Version Build Guide

This guide explains how to build the Wild Bees mod for multiple Minecraft versions.

## How It Works

The mod uses **version-specific source files** and **separate builds** for each Minecraft version:

1. **Version-specific mixins**: Different mixin implementations for different API versions
2. **Gradle configuration**: Automatically selects the correct source files based on `minecraft_version` in `gradle.properties`
3. **Separate builds**: Each version gets its own jar with correct mappings and dependencies

## Version-Specific Code

The `LivingEntityDamageMixin` has different implementations for different versions:

- **1.21.7-1.21.8**: `LivingEntityDamageMixin_v1_21_7.java` - Uses `damage(WorldAccess world, ...)`
- **1.21.9+**: `LivingEntityDamageMixin_v1_21_9.java` - Uses `damage(DamageSource source, ...)` and `getEntityWorld()`

The build system automatically:
1. Excludes all version-specific files
2. Includes only the file matching the current `minecraft_version`
3. Renames it to `LivingEntityDamageMixin.java` during compilation

## Building Locally

### Option 1: Build Script (Recommended)

Use the provided build script to build all versions:

```bash
./build-multi-version.sh
```

Or build specific versions:

```bash
./build-multi-version.sh 1.21.7 1.21.9
```

The script will:
- Build each version sequentially
- Update `gradle.properties` and `fabric.mod.json` for each version
- Output jars to `build/multi-version/`
- Restore original configuration files

### Option 2: Manual Build

1. **Edit `gradle.properties`**:
   ```properties
   minecraft_version=1.21.7
   yarn_mappings=1.21.7+build.2
   loader_version=0.17.3
   fabric_version=0.129.0+1.21.7
   ```

2. **Build**:
   ```bash
   ./gradlew clean build
   ```

3. **Repeat for each version** with different `gradle.properties` values

## GitHub Actions

The `.github/workflows/build-multi-version.yml` workflow automatically builds all configured versions on push/PR.

**Configured versions:**
- 1.21.7
- 1.21.9
- 1.21.10

Each version builds in parallel and uploads artifacts separately.

## Adding a New Version

1. **Create version-specific mixin** (if needed):
   ```java
   // src/main/java/tk/estecka/selfcarehive/mixin/LivingEntityDamageMixin_v1_21_X.java
   ```

2. **Update `build.gradle`**:
   - Add version check in `sourceSets.main.java` exclude/include logic
   - Add version check in `JavaCompile` task for file copying

3. **Update build script** (`build-multi-version.sh`):
   ```bash
   VERSIONS[1.21.X]="yarn_mappings|loader_version|fabric_version"
   ```

4. **Update GitHub Actions** (`.github/workflows/build-multi-version.yml`):
   - Add version to `matrix.mc-version`
   - Add version config to `include` section

5. **Test the build**:
   ```bash
   ./build-multi-version.sh 1.21.X
   ```

## Version Compatibility

| Minecraft Version | Yarn Mappings | Fabric Loader | Fabric API |
|-------------------|---------------|---------------|------------|
| 1.21.7            | 1.21.7+build.2 | 0.17.3        | 0.129.0+1.21.7 |
| 1.21.9            | 1.21.9+build.2 | 0.18.2        | 0.138.0+1.21.9 |
| 1.21.10           | 1.21.10+build.2 | 0.18.2        | 0.138.0+1.21.10 |

Check [Fabric's development page](https://fabricmc.net/develop) for the latest versions.

## Troubleshooting

### "Class not found" errors
- Make sure the correct version-specific mixin file exists
- Check that `gradle.properties` has the correct `minecraft_version`

### Mixin transformation errors
- Verify the method signature matches the target Minecraft version
- Check intermediary mappings are correct for the version

### Build script fails
- Ensure `gradle.properties.backup` exists (created on first run)
- Check that all version configs in the script are correct
- Verify Fabric API versions are available

## Key Differences Between Versions

### 1.21.7 vs 1.21.9+

**Entity methods:**
- 1.21.7: `getWorld()`, `getPos()`
- 1.21.9+: `getEntityWorld()`, `getEntityPos()`

**LivingEntity.damage():**
- 1.21.7: `damage(WorldAccess world, DamageSource source, float amount)`
- 1.21.9+: `damage(DamageSource source, float amount)`

These differences are handled by version-specific mixin implementations.

