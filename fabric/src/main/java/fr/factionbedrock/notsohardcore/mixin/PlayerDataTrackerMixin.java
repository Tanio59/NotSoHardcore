package fr.factionbedrock.notsohardcore.mixin;

import fr.factionbedrock.notsohardcore.registry.FabricTrackedData;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Fabric only : defines NSHTrackedData values (LIVES, LIFE_REGAIN_TICK_MARKER, LIFE_REGAIN_REALTIME_MARKER) in player entity data
@Mixin(Player.class)
public class PlayerDataTrackerMixin
{
	@Inject(at = @At("RETURN"), method = "defineSynchedData")
	private void init(SynchedEntityData.Builder builder, CallbackInfo info)
	{
		for (FabricTrackedData.EntityDataTrackedValue<?> value : FabricTrackedData.VALUES) {value.define(builder);}
	}
}
