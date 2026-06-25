# AGENTS.md — Wild Bees

Authoritative guidance for AI agents in this repo. **Read this before applying any skill in `.cursor/skills/`.** Portable **`mc-*`** skills were synced from [Minecraft Minder](https://github.com/ImpureCrumpet/Minecraft-Minder); most assume multi-`mc*` Gradle subprojects — this repo uses **git branches per MC line** instead.

## Repo at a glance

- **Mod:** Wild Bees — fork of Self-Care Hive; nest defense, escalation, non-lethal wild bee stings, smaller bee dimensions.
- **Platform:** Fabric only.
- **Version strategy:** **Branch-per-release-line** — not `mc*/` subprojects.
  - **`1.21.11`** — active development (`origin/HEAD` after push); MC **1.21.4–1.21.11**; build target **1.21.11**.
  - **`1.21`** — planned; MC **1.21–1.21.1**.
  - **26.x** — future new-gen line (Mojmap); not started.
- **Unsupported:** 1.21.2–1.21.3. Interim branches `1.21.5`, `1.21.8`, `1.21.10` retired — see `port.md`.
- **Build:** Single Gradle module per branch (`build.gradle`, `settings.gradle`), Groovy DSL, Yarn mappings.
- **Java:** Temurin **21** (`j21` via SDKMAN). See **`mc-gradle-daemon-hygiene`** and `.cursor/rules/temurin.mdc`.
- **Package:** `tk.estecka.selfcarehive` (legacy from upstream fork).
- **26.x:** Use **`mc-fabric-mojmap-migration-26x`** for planning only until the line is opened.

## Branch workflow

1. Check out `1.21.11` for the 1.21.4+ release line.
2. `gradle.properties` on that branch pins `minecraft_version`, Yarn mappings, and Fabric API.
3. Backport API changes across branches using `port.md` and `ref/` plans — do not merge unrelated branches blindly.
4. JAR version format: `{mod_version}+{minecraft_version}` (see `build.gradle` `version`).

See **`README.md`**, **`port.md`**, and **`ref/MULTI_VERSION_BUILD.md`** for supported lines and API diffs.

## Source layout (typical per branch)

- `src/main/java/` — mod logic, mixins, game rules.
- `src/main/resources/` — `fabric.mod.json`, assets, mixins JSON.
- `ref/` — gitignored local plans and backport checklists.

**Not** used today: `mc26.1/`, `mc1.21.10/`, or other `mc*/` Gradle subprojects on the same branch.

## Open work (non-blocking unless noted)

- **onUse remapping warning** — tracked in `ref/Fix onUse Remapping Warning – Technical Plan.md` (non-blocking).

## Skills index

| Skill | Applies here? | Use when | Notes |
|-------|---------------|----------|-------|
| [`mc-gradle-daemon-hygiene`](.cursor/skills/mc-gradle-daemon-hygiene/SKILL.md) | **Yes** | Before any `./gradlew` | `j21` for all current 1.21.x branches. |
| [`mc-isolate-version-mixin`](.cursor/skills/mc-isolate-version-mixin/SKILL.md) | **Partial** | Mixin work on a branch | Mixins live under shared `src/main/java/` per branch; use branch checkout instead of `mc*/` folders. |
| [`mc-extract-fabric-version-adapter`](.cursor/skills/mc-extract-fabric-version-adapter/SKILL.md) | **Partial** | API differs between branches | Backport via branches + `port.md`; ignore `mc*/` adapter layout until 26.x migration. |
| [`mc-fabric-minecraft-recipe-datapack`](.cursor/skills/mc-fabric-minecraft-recipe-datapack/SKILL.md) | Reference | Recipe/datapack changes | Subproject sections do not apply. |
| [`mc-fabric-mojmap-migration-26x`](.cursor/skills/mc-fabric-mojmap-migration-26x/SKILL.md) | **26.x prep** | Planning 26.x ports | Will require Yarn → Mojmap and a layout decision (branches vs `mc*`). |
| [`mc-scaffold-new-fabric-subproject`](.cursor/skills/mc-scaffold-new-fabric-subproject/SKILL.md) | **Not today** | — | New MC lines use **new branches**, not `mc*` modules. Revisit if adopting Minder multi-`mc*` layout for 26.x. |
| [`mc-gitlab-mod-mirror-push`](.cursor/skills/mc-gitlab-mod-mirror-push/SKILL.md) | **Yes** | Mirror a release branch to GitLab | Ignore Minder-only `mods.yaml` references. |

## Companion docs

- `.cursor/fabric-mod-build-release-guide-v4.3.md` — Minder multi-version Fabric guide (reference for 26.x).
- `.cursor/fabric-loader-yarn-fabric-api.yaml` — version matrix reference.
- `port.md`, `changelog.md`, `ref/Branch Strategy – Version Management Plan.md` — mod-specific versioning.
