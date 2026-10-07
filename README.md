# Not So Hardcore
Semi-Hardcore Minecraft mod

Hi, welcome to Not So Hardcore mod repository! :D

This mod is primarily created for my personal use, but feel free to download it on CurseForge : https://www.curseforge.com/minecraft/mc-mods/not-so-hardore.

If you need information, there is a Wiki here on GitHub : https://github.com/nicochart/NotSoHardcore/wiki.

If you want to chat about the mod or chat with me, come on discord! (My server: https://discord.gg/dthhGtn, my nickname: "nicochart").

## Project structure

This is a multiloader project (based on [MultiLoader-Template](https://github.com/jaredlll08/MultiLoader-Template)): one build produces a Fabric jar and a NeoForge jar.

| Folder | Content |
|---|---|
| `common/` | Code and resources shared by every mod loader: config, info screen, item, commands, sync packet, mixins, assets. Compiled against vanilla Minecraft only, it must not use any mod loader API. |
| `fabric/` | Fabric entrypoint (`NotSoHardcoreFabric`), `fabric.mod.json` and Fabric-only code. |
| `neoforge/` | NeoForge entrypoints (`NotSoHardcoreNeoForge`, `NotSoHardcoreNeoForgeClient`), `neoforge.mods.toml` and NeoForge-only code. |
| `build-logic/` | Gradle conventions shared by the subprojects. |

The common code reaches mod loader specific features through `IPlatformHelper` (`common/.../platform`), implemented by `FabricPlatformHelper` and `NeoForgePlatformHelper` and loaded with a Java `ServiceLoader` (see the `META-INF/services` files):
- config folder and server to client packet sending,
- player data (`NSHTrackedData`: lives and life regain markers). Fabric stores it in the player entity data (`FabricTrackedData`, `PlayerDataTrackerMixin`), NeoForge in synced player data attachments (`NeoForgeTrackedData`), because NeoForge forbids adding synced entity data to vanilla entities. In both cases it is saved in the player save by `PlayerNbtMixin`.

Access to private Minecraft code is declared twice: `common/src/main/resources/notsohardcore.classtweaker` for Fabric, and `common/src/main/resources/META-INF/accesstransformer.cfg` for NeoForge (and the common project).

## Building from source

Requirements: Java 17 or newer to run Gradle. The mod is compiled with Java 25, which Gradle downloads automatically if it is not installed.

```bash
./gradlew build
```

The jars are created in `fabric/build/libs/` and `neoforge/build/libs/`.

To start a development client: `./gradlew :fabric:runClient` or `./gradlew :neoforge:runClient` (or the "Fabric Client" / "NeoForge Client (:neoforge)" run configurations generated in IntelliJ after a Gradle reload).

Minecraft, Fabric and NeoForge versions are set in `gradle.properties`.

Shield: [![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

This work is licensed under a
[Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg