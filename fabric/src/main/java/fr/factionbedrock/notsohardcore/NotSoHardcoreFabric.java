package fr.factionbedrock.notsohardcore;

import fr.factionbedrock.notsohardcore.client.packet.NSHClientNetworking;
import fr.factionbedrock.notsohardcore.client.registry.NSHKeyBinds;
import fr.factionbedrock.notsohardcore.commands.NSHCommands;
import fr.factionbedrock.notsohardcore.events.NSHPlayerEvents;
import fr.factionbedrock.notsohardcore.packet.NSHS2CSynchData;
import fr.factionbedrock.notsohardcore.registry.NSHItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class NotSoHardcoreFabric implements ModInitializer, ClientModInitializer
{
    @Override public void onInitialize()
    {
        NotSoHardcore.onInitialize();

        NSHItems.load();

        //Networking
        PayloadTypeRegistry.serverboundPlay().register(NSHS2CSynchData.ID, NSHS2CSynchData.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(NSHS2CSynchData.ID, NSHS2CSynchData.CODEC);

        //Player events
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> NSHPlayerEvents.onPlayerJoin(handler.getPlayer()));

        //Commands
        CommandRegistrationCallback.EVENT.register(NSHCommands::registerImpl);
    }

    @Override public void onInitializeClient()
    {
        //Keybinds
        KeyMappingHelper.registerKeyMapping(NSHKeyBinds.INFO_MENU_KEY);
        ClientTickEvents.END_CLIENT_TICK.register(NSHKeyBinds::onEndClientTick);

        //Client networking
        ClientPlayNetworking.registerGlobalReceiver(NSHS2CSynchData.ID, (payload, context) -> NSHClientNetworking.handleSyncData(payload));
    }
}
