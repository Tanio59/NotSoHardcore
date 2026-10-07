package fr.factionbedrock.notsohardcore.mixin;

import fr.factionbedrock.notsohardcore.config.LoadedConfig;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedData;
import fr.factionbedrock.notsohardcore.util.NSHHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class PlayerTickMixin
{
    @Inject(at = @At("RETURN"), method = "tick")
    private void onTick(CallbackInfo info)
    {
        ServerPlayer player = (ServerPlayer) (Object) this;

        int lives = NSHTrackedData.LIVES.get(player);
        if (lives > LoadedConfig.Server.MAX_LIVES)
        {
            NSHTrackedData.LIVES.set(player, LoadedConfig.Server.MAX_LIVES);
            lives = LoadedConfig.Server.MAX_LIVES;
        }

        if (player.isCreative() && LoadedConfig.Server.CREATIVE_RESETS_LIFE_COUNT)
        {
            if (lives != LoadedConfig.Server.MAX_LIVES)
            {
                NSHTrackedData.LIVES.set(player, LoadedConfig.Server.MAX_LIVES);
            }
        }
        else if (lives != LoadedConfig.Server.MAX_LIVES)
        {
            //dividing by 50 turns milliseconds into ticks
            long currentTime = NSHHelper.getCurrentTime(player, LoadedConfig.Server.USE_REALTIME_REGAIN);
            long liveRegainTimeMarker = NSHHelper.getLiveRegainTimeMarker(player, LoadedConfig.Server.USE_REALTIME_REGAIN);
            long timeDelta = currentTime - liveRegainTimeMarker;

            if (timeDelta < LoadedConfig.Server.TIME_TO_REGAIN_LIFE)
            {
                if (lives == 0 && !player.isSpectator() && !player.isCreative())
                {
                    player.setGameMode(GameType.SPECTATOR);
                }
            }
            else
            {
                int livePlayerCanRegain = (int) (timeDelta / LoadedConfig.Server.TIME_TO_REGAIN_LIFE);
                long extraTime = timeDelta - (long) livePlayerCanRegain * LoadedConfig.Server.TIME_TO_REGAIN_LIFE;
                if (lives == 0)
                {
                    NSHHelper.respawnPlayer(player);
                }
                NSHTrackedData.LIVES.set(player, Math.min(LoadedConfig.Server.MAX_LIVES, lives + livePlayerCanRegain));
                NSHTrackedData.LIFE_REGAIN_TICK_MARKER.set(player, NSHHelper.getCurrentTime(player, false) - extraTime);
                NSHTrackedData.LIFE_REGAIN_REALTIME_MARKER.set(player, System.currentTimeMillis() - (extraTime * 50));
            }
        }
    }
}
