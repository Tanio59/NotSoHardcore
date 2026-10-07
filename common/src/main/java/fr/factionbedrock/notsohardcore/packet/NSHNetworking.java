package fr.factionbedrock.notsohardcore.packet;

import fr.factionbedrock.notsohardcore.NotSoHardcore;
import fr.factionbedrock.notsohardcore.config.LoadedConfig;
import fr.factionbedrock.notsohardcore.platform.Services;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//NSHS2CSynchData registration is done by each mod loader entrypoint (NotSoHardcoreFabric, NotSoHardcoreNeoForge)
public class NSHNetworking
{
    public static void sendS2CSync(@Nullable MinecraftServer server)
    {
        if (server == null)
        {
            NotSoHardcore.LOGGER.error("NSH ERROR - Tried to send S2C Sync from null Minecraft Server !");
        }
        else {sendS2CSync(server.getPlayerList().getPlayers());}
    }

    public static void sendS2CSync(List<ServerPlayer> players)
    {
        for (ServerPlayer player : players) {sendS2CSync(player);}
    }

    public static void sendS2CSync(ServerPlayer player)
    {
        //The packet is sent from server, so calling LoadedConfig.Local or LoadedConfig.Server is the same.
        Services.PLATFORM.sendToPlayer(player, new NSHS2CSynchData("sync_nsh_data", LoadedConfig.Local.MAX_LIVES, LoadedConfig.Local.TIME_TO_REGAIN_LIFE, LoadedConfig.Local.CREATIVE_RESETS_LIFE_COUNT, NSHTrackedData.LIVES.get(player), NSHTrackedData.LIFE_REGAIN_TICK_MARKER.get(player), LoadedConfig.Local.USE_REALTIME_REGAIN, NSHTrackedData.LIFE_REGAIN_REALTIME_MARKER.get(player), LoadedConfig.Local.ALWAYS_RENDER_HARDCORE_HEARTS));
    }
}
