package fr.factionbedrock.notsohardcore.mixin;

import fr.factionbedrock.notsohardcore.config.LoadedConfig;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class PlayerDeathMixin
{
    @Inject(at = @At("HEAD"), method = "die")
    private void applyOnDeath(DamageSource damageSource, CallbackInfo info)
    {
        ServerPlayer player = (ServerPlayer) (Object) this;

        int previousLives = NSHTrackedData.LIVES.get(player);
        if (previousLives > 0 && !player.isSpectator() && !(player.isCreative() && LoadedConfig.Server.CREATIVE_RESETS_LIFE_COUNT))
        {
            if (previousLives == LoadedConfig.Server.MAX_LIVES)
            {
                NSHTrackedData.LIFE_REGAIN_REALTIME_MARKER.set(player, System.currentTimeMillis());
                NSHTrackedData.LIFE_REGAIN_TICK_MARKER.set(player, player.level().getGameTime());
            }
            NSHTrackedData.LIVES.set(player, previousLives - 1);
        }
    }

    @Inject(at = @At("HEAD"), method = "restoreFrom")
    private void applyOnCopyFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo info)
    {
        ServerPlayer newPlayer = (ServerPlayer) (Object) this;

        long realtimeMarker = NSHTrackedData.LIFE_REGAIN_REALTIME_MARKER.get(oldPlayer);
        NSHTrackedData.LIFE_REGAIN_REALTIME_MARKER.set(newPlayer, realtimeMarker);

        int lives = NSHTrackedData.LIVES.get(oldPlayer);
        NSHTrackedData.LIVES.set(newPlayer, lives);
        long live_regain_timer = NSHTrackedData.LIFE_REGAIN_TICK_MARKER.get(oldPlayer);
        NSHTrackedData.LIFE_REGAIN_TICK_MARKER.set(newPlayer, live_regain_timer);
    }

}
