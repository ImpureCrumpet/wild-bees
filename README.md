# Wild Bees (Self-Care Hive Fork)

> **This is a fork of [Self-Care Hive](https://modrinth.com/mod/selfcare-hive) by Estecka.**
> 
> Wild Bees adds defensive mechanics to bee nests and smaller bee dimensions while preserving all original Self-Care Hive functionality.

---

## Supported Versions

| Minecraft | Branch | Status |
|-----------|--------|--------|
| 1.21.4–1.21.11 | `1.21.11` | ✅ Active development |
| 1.21–1.21.1 | `1.21` | 🔜 Planned |

**Future:** **26.x** (new-gen builds, Mojmap) — not started.

All supported lines include the full feature set: wild bee mechanics, bee dimensions, healing, and breeding.

**Not supported:** 1.21.2–1.21.3 (gap between release lines). Pre-1.21 — use [upstream Self-Care Hive](https://modrinth.com/mod/selfcare-hive).

---

## Wild Bee Mechanics

Bee nests now pose a real threat when harvested without smoke. Disturb a wild hive, and you'll face the consequences.

### Nest Defense

When harvesting honey from a **bee nest** without smoke:
- Bees are released with **extended anger duration** (1200–2400 ticks vs vanilla's 400–800)
- **Nearby nests escalate** — all bee nests within 8 blocks also release their angry bees
- Creates a genuine swarm encounter rather than a trivial 1-3 bee annoyance

With smoke present, nests behave like vanilla — safe harvest, no aggression.

### Non-Lethal Stings

To prevent wild bees from dying during swarm encounters:
- Wild bees survive **self-inflicted stinging damage** but are reduced to **≥ 2 hearts** (4.0 HP)
- **Wild bees can sting multiple times** - unlike vanilla (where bees die after one sting), wild bees survive and can continue attacking
- Protection **only applies during the anger period** (while bees are escalated/angry, 1200–2400 ticks)
- Wild bees **can still be killed by external damage** (player attacks, mobs, etc.)
- This creates sustained swarm pressure - multiple bees repeatedly stinging during the escalation period
- Poison effects and knockback still apply normally

### Beehives Unchanged

Player-crafted **beehives** retain vanilla behavior. Smoke still fully protects you, and bees use standard anger duration. All Self-Care healing and breeding mechanics work normally.

### Wild Bee Configuration (Game Rules)

| Game Rule | Default | Description |
|-----------|---------|-------------|
| `selfcarehive.wildbees.escalation_radius` | 8 | Radius to poll nearby nests |
| `selfcarehive.wildbees.nest_anger_min` | 1200 | Minimum anger duration (ticks) |
| `selfcarehive.wildbees.nest_anger_max` | 2400 | Maximum anger duration (ticks) |
| `selfcarehive.wildbees.min_sting_health` | 4.0 | Minimum HP for wild bees after stinging (prevents death) |
| `selfcarehive.wildbees.non_lethal_stings` | true | Prevent wild bees from dying when stinging |

---

## Bee Dimensions

Bees are now smaller and hives hold more of them.

### Size & Capacity

- **Bees are 65% of vanilla size** — more realistic proportions
- **Hives hold 21 bees** instead of vanilla's 3 — allows for proper colonies
- **Suffocation prevention** — small bees won't get stuck and die in blocks

### Bee Dimensions Configuration (Game Rules)

| Game Rule | Default | Description |
|-----------|---------|-------------|
| `selfcarehive.beedimensions.modifier` | 0.65 | Bee size multiplier (0.65 = 65% of vanilla) |
| `selfcarehive.beedimensions.hive_capacity` | 21 | Maximum bees per hive (vanilla = 3) |
| `selfcarehive.beedimensions.prevent_suffocation` | true | Prevent small bees from suffocation damage |

---

## Self-Care Hive Features

The original Self-Care Hive functionality remains fully intact:

### Self-Healing

Every time a bee exits the hive, it may consume honey to heal itself. Bees will refrain from over-healing unless the hive is overflowing with honey.

By default, bees consume 1 honey level to heal 1 heart at a time.

### Auto-Breeding

Hives that haven't reached maximum population will create babies by consuming honey. Hives track when bees leave — if a bee has been gone too long, it's considered missing and may be replaced.

Babies are created when a bee exits the nest. Only a single adult bee is required. This also triggers the parent's breeding cooldown.

By default, 5 honey levels are required for each offspring.

### Self-Care Configuration (Game Rules)

| Game Rule | Default | Description |
|-----------|---------|-------------|
| `selfcarehive.healing` | true | Enable self-healing |
| `selfcarehive.healing.cost` | 1 | Honey consumed per heal |
| `selfcarehive.healing.potency` | 2.0 | Health restored per heal |
| `selfcarehive.breeding` | true | Enable auto-breeding |
| `selfcarehive.breeding.cost` | 5 | Honey consumed per offspring |
| `selfcarehive.tracking.duration` | 12000 | Ticks before bee is considered missing |

---

## Installation

- **Platform:** Fabric
- **Requirements:** Fabric API, Java 21+

---

## Credits

**Wild Bees** is a fork of **Self-Care Hive** by **Estecka**.

- Original mod: [Self-Care Hive on Modrinth](https://modrinth.com/mod/selfcare-hive)
- Original source: [GitHub](https://github.com/Estecka/mc-SelfCare-Hive)
- License: [EUPL-1.2](LICENSE)
