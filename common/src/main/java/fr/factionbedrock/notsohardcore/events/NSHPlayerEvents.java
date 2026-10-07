package fr.factionbedrock.notsohardcore.events;

import fr.factionbedrock.notsohardcore.packet.NSHNetworking;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class NSHPlayerEvents
{
    //Called by each mod loader player join event
    public static void onPlayerJoin(ServerPlayer player)
    {
        NSHNetworking.sendS2CSync(player);

        /* Modifying player game mode after respawn is now done in HardcoreRespawnRedirectMixin
         *
         * ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) ->
         * {
         *     NSHNetworking.sendS2CSync(newPlayer);
         *
         *     // Force Survival on the next tick if the player has lives (avoids ordering with hardcore spectator enforcement)
         *     int lives = NSHTrackedData.LIVES.get(newPlayer);
         *     if (lives > 0 && !newPlayer.isCreative())
         *     {
         *         newPlayer.level().getServer().execute(() -> newPlayer.setGameMode(GameType.SURVIVAL));
         *     }
         * });
         */
    }
}
