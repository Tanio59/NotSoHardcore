package fr.factionbedrock.notsohardcore.platform;

import fr.factionbedrock.notsohardcore.platform.services.IPlatformHelper;
import fr.factionbedrock.notsohardcore.registry.FabricTrackedData;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedValue;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.level.ServerPlayer;

import java.nio.file.Path;
import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper
{
    @Override public String getPlatformName() {return "Fabric";}

    @Override public boolean isModLoaded(String modId) {return FabricLoader.getInstance().isModLoaded(modId);}

    @Override public boolean isDevelopmentEnvironment() {return FabricLoader.getInstance().isDevelopmentEnvironment();}

    @Override public Path getConfigDir() {return FabricLoader.getInstance().getConfigDir();}

    @Override public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {ServerPlayNetworking.send(player, payload);}

    @Override public NSHTrackedValue<Integer> registerTrackedInt(String name, Supplier<Integer> defaultValue) {return FabricTrackedData.register(EntityDataSerializers.INT, defaultValue);}

    @Override public NSHTrackedValue<Long> registerTrackedLong(String name, Supplier<Long> defaultValue) {return FabricTrackedData.register(EntityDataSerializers.LONG, defaultValue);}
}
