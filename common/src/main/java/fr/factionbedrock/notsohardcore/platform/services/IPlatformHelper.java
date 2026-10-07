package fr.factionbedrock.notsohardcore.platform.services;

import fr.factionbedrock.notsohardcore.registry.NSHTrackedValue;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.nio.file.Path;
import java.util.function.Supplier;

//Everything the common code needs from the mod loader
public interface IPlatformHelper
{
    //Name of the current mod loader ("Fabric" or "NeoForge")
    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    //Mod loader config folder (the mod config is stored in <config dir>/notsohardcore/config.json)
    Path getConfigDir();

    //Sends a server to client payload, registered by each mod loader entrypoint
    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    //Creates a player value synced from server to client (see NSHTrackedData)
    NSHTrackedValue<Integer> registerTrackedInt(String name, Supplier<Integer> defaultValue);

    NSHTrackedValue<Long> registerTrackedLong(String name, Supplier<Long> defaultValue);
}
