# Version Support

## This Fork

**Supported Minecraft Versions:** `1.21.9+` (Fabric)

This fork targets Minecraft 1.21.9 and later within the 1.21.x series. Earlier versions have breaking API changes that would require significant rework.

## Breaking Changes Reference

The following documents Minecraft API changes that affected this mod's development:

### 1.19.4
Original Self-Care Hive baseline.

### 1.20.5
- `BlockEntity::readNbt` and `writeNbt` now require a Registry Wrapper Lookup parameter.
- `BeehiveBlockEntity` now uses a `BeeData` record that tracks ticks-in-hive and nectar.
- `BeehiveBlockEntity::tryEnterHive` and `addBee` no longer take parameters carried by `BeeData`.
- `BeehiveBlockEntity::onReleaseBee` no longer directly calls `EntityType::loadEntityWithPassengers`.

### 1.21.2
- `World.getGameRules()` was moved to `ServerWorld`. Use `MinecraftServer::getGameRules` instead.

### 1.21.4
- `tryEnterHive` now takes a `BeeEntity` instead of an `Entity`.

### 1.21.5
- `NbtCompound::contains` no longer checks the element type.
- `NbtCompound` get methods are now wrapped in optionals, or require a fallback parameter.

### 1.21.6
- Serialization no longer directly manipulates NBT and must be backed by a codec.

### 1.21.9
- `Entity::getPos` and `Entity::getWorld` were replaced with `Entity::getEntityPos` and `Entity::getEntityWorld`.

## Original Mod Version Support

The [original Self-Care Hive mod](https://modrinth.com/mod/selfcare-hive) by Estecka supports:
- Minecraft 1.21–1.21.10
- Minecraft 1.20.x
- Minecraft 1.19.4
