# Version Support

## Supported Versions

| Minecraft | Branch | Features |
|-----------|--------|----------|
| 1.21.9–1.21.10 | `1.21.10` | All features (wild bees, bee dimensions, healing, breeding) |
| 1.21.6–1.21.8 | `1.21.8` | All features |
| 1.21.2–1.21.5 | `1.21.5` | All features |

**Platform:** Fabric only

## Archived Versions

The following versions are no longer supported and have been archived:

- **1.20.5** (1.20.5–1.21.1) — Original selfcare-hive only
- **1.19.4** — Original selfcare-hive only

For these older versions, use the [original Self-Care Hive mod](https://modrinth.com/mod/selfcare-hive) by Estecka.

---

## API Differences Between Versions

### 1.21.9+ (Branch: 1.21.10)
- `LivingEntity.damage(DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BeeState, Entity)`
- `BeehiveBlockEntity.isFull()`, `addOccupant()`
- Serialization: `writeData`/`readData` with `Codec`

### 1.21.6–1.21.8 (Branch: 1.21.8)
- `LivingEntity.damage(ServerWorld, DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BlockState, BeeState)`
- `BeehiveBlockEntity.isFullOfBees()`, `addBee()`
- Serialization: `writeData`/`readData` with `Codec`

### 1.21.2–1.21.5 (Branch: 1.21.5)
- `LivingEntity.damage(ServerWorld, DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BlockState, BeeState)`
- `BeehiveBlockEntity.isFullOfBees()`, `addBee()`
- Serialization: `writeNbt`/`readNbt` with `NbtCompound`

---

## Breaking Changes Reference

The following documents Minecraft API changes that affected this mod's development:

### 1.21.9
- `Entity::getPos` and `Entity::getWorld` were replaced with `Entity::getEntityPos` and `Entity::getEntityWorld`.
- `LivingEntity.damage()` signature changed from 3 to 2 parameters.

### 1.21.6
- Serialization no longer directly manipulates NBT and must be backed by a codec.

### 1.21.5
- `NbtCompound::contains` no longer checks the element type.
- `NbtCompound` get methods are now wrapped in optionals, or require a fallback parameter.

### 1.21.4
- `tryEnterHive` now takes a `BeeEntity` instead of an `Entity`.

### 1.21.2
- `World.getGameRules()` was moved to `ServerWorld`. Use `MinecraftServer::getGameRules` instead.

### 1.20.5 (Archived)
- `BlockEntity::readNbt` and `writeNbt` now require a Registry Wrapper Lookup parameter.
- `BeehiveBlockEntity` now uses a `BeeData` record that tracks ticks-in-hive and nectar.

### 1.19.4 (Archived)
Original Self-Care Hive baseline.

---

## Original Mod

The [original Self-Care Hive mod](https://modrinth.com/mod/selfcare-hive) by Estecka supports additional versions. For Minecraft versions not covered by this fork, use the original mod.
