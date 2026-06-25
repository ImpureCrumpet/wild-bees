# v1
### 1.0.0
Initial Release
### 1.0.1
- Updated for MC 1.20.5
### 1.0.2
- Updated for MC 1.21
- Fixed hives having a chance to create superfluous bees, after remaining in an unloaded chunk.
	- Changed how time is counted; now storing the duration the bee has left since, instead of the date the bee left at.
	- Hives no longer virtually tick when unloaded.
### 1.0.3
- Updated for MC 1.21.2
### 1.0.4
- Updated for MC 1.21.4 and MC 1.21.5
### 1.0.5
- Updated for MC 1.21.6

### Unreleased
- Consolidate 1.21.4+ onto branch `1.21.11`; build target MC 1.21.11.
- Version policy: two 1.21.x release lines — **1.21–1.21.1** (`1.21` branch) and **1.21.4–1.21.11** (`1.21.11` branch). **26.x** planned separately.
- Port to 1.21.11 APIs: registry game rules, `Angerable` duration, `ServerWorld` damage signature.
- Fix client renderer mixins for 1.21.11 (`getShadowRadius`, `scale` signature).
