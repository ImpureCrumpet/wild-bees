# Version Support

## Release lines

Two active **1.21.x** build lines (one Gradle module per branch). **26.x** is a separate future line (new-gen / Mojmap).

| Minecraft | Branch | Status | Features |
|-----------|--------|--------|----------|
| 1.21.4–1.21.11 | `1.21.11` | Active (build target **1.21.11**) | Wild bees, bee dimensions, healing, breeding |
| 1.21–1.21.1 | `1.21` | Planned | Same (once branch is created) |
| 26.x | TBD | Future | New-gen builds; see `mc-fabric-mojmap-migration-26x` |

**Platform:** Fabric only

**Unsupported gap:** 1.21.2–1.21.3 (between the two 1.21.x lines).

## Interim branches (retired)

Superseded by `1.21.11` — safe to delete locally; remove from `origin` when you push the consolidated branch:

- `1.21.10`, `1.21.8`, `1.21.5`

## Archived Versions

The following versions are no longer supported:

- **1.20.5** — Original Self-Care Hive only
- **1.19.4** — Original Self-Care Hive only

For Minecraft versions not covered by this fork, use the [original Self-Care Hive mod](https://modrinth.com/mod/selfcare-hive) by Estecka.

---

## API Differences Between Versions

Use this when porting or consolidating branches. The **1.21.4–1.21.11** line crosses several vanilla API boundaries; the branch should target the **latest** MC in the range and absorb breaks listed below.

### 1.21.11 (Branch: `1.21.11`, build target)
- `GameRules` → `net.minecraft.world.rule`; access via `ServerWorld.getGameRules().getValue(GameRule<T>)`
- Custom rules: Fabric `GameRuleBuilder` (replaces `GameRuleRegistry` / `GameRules.Key`)
- `Entity.getEntityWorld()` (not `getWorld()` on entities)
- `LivingEntity.damage(ServerWorld, DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BlockState, BeeState)`; `isFullOfBees()`, `addBee()`
- Bee anger: `Angerable.setAngerDuration()` / `hasAngerTime()` (not `setAngerTime`)

### 1.21.9–1.21.10 (historical; was branch `1.21.10`)
- `LivingEntity.damage(DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BeeState, Entity)`
- `BeehiveBlockEntity.isFull()`, `addOccupant()`
- Serialization: `writeData`/`readData` with `Codec`

### 1.21.6–1.21.8 (within `1.21.11` line, pre-1.21.9)
- `LivingEntity.damage(ServerWorld, DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BlockState, BeeState)`
- `BeehiveBlockEntity.isFullOfBees()`, `addBee()`
- Serialization: `writeData`/`readData` with `Codec`

### 1.21.4–1.21.5 (within `1.21.11` line, pre-1.21.6)
- `LivingEntity.damage(ServerWorld, DamageSource, float)`
- `BeehiveBlockEntity.angerBees(PlayerEntity, BlockState, BeeState)`
- `BeehiveBlockEntity.isFullOfBees()`, `addBee()`
- Serialization: `writeNbt`/`readNbt` with `NbtCompound`

### 1.21–1.21.1 (Branch: `1.21`, planned)
- Baseline TBD when the branch is created; expect NBT-era APIs similar to 1.21.4–1.21.5.

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
