package fr.factionbedrock.notsohardcore.registry;

import fr.factionbedrock.notsohardcore.config.LoadedConfig;
import fr.factionbedrock.notsohardcore.platform.Services;

public class NSHTrackedData
{
    public static final NSHTrackedValue<Integer> LIVES = Services.PLATFORM.registerTrackedInt("lives", () -> LoadedConfig.Local.MAX_LIVES);
    public static final NSHTrackedValue<Long> LIFE_REGAIN_TICK_MARKER = Services.PLATFORM.registerTrackedLong("life_regain_tick_marker", () -> 0L);
    public static final NSHTrackedValue<Long> LIFE_REGAIN_REALTIME_MARKER = Services.PLATFORM.registerTrackedLong("life_regain_realtime_marker", () -> 0L);

    public static void load() {}
}
