package fr.factionbedrock.notsohardcore.platform;

import fr.factionbedrock.notsohardcore.platform.services.IPlatformHelper;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedValue;
import fr.factionbedrock.notsohardcore.registry.NeoForgeTrackedData;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.PacketDistributor;

import java.nio.file.Path;
import java.util.function.Supplier;

public class NeoForgePlatformHelper implements IPlatformHelper
{
    @Override public String getPlatformName() {return "NeoForge";}

    @Override public boolean isModLoaded(String modId) {return ModList.get().isLoaded(modId);}

    @Override public boolean isDevelopmentEnvironment() {return !FMLLoader.getCurrent().isProduction();}

    @Override public Path getConfigDir() {return FMLPaths.CONFIGDIR.get();}

    @Override public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {PacketDistributor.sendToPlayer(player, payload);}

    @Override public NSHTrackedValue<Integer> registerTrackedInt(String name, Supplier<Integer> defaultValue) {return NeoForgeTrackedData.register(name, ByteBufCodecs.VAR_INT, defaultValue);}

    @Override public NSHTrackedValue<Long> registerTrackedLong(String name, Supplier<Long> defaultValue) {return NeoForgeTrackedData.register(name, ByteBufCodecs.VAR_LONG, defaultValue);}
}
