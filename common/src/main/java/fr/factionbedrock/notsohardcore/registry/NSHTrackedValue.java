package fr.factionbedrock.notsohardcore.registry;

import net.minecraft.world.entity.player.Player;

//A player value synced from server to client, stored the mod loader way :
//Fabric : player entity data (see FabricTrackedData), NeoForge : player data attachment (see NeoForgeTrackedData)
public interface NSHTrackedValue<T>
{
    T get(Player player);

    void set(Player player, T value);
}
