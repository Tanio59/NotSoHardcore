package fr.factionbedrock.notsohardcore;

import fr.factionbedrock.notsohardcore.client.packet.NSHClientNetworking;
import fr.factionbedrock.notsohardcore.client.registry.NSHKeyBinds;
import fr.factionbedrock.notsohardcore.packet.NSHS2CSynchData;
import fr.factionbedrock.notsohardcore.registry.NSHKeyBinding;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = NotSoHardcore.MOD_ID, dist = Dist.CLIENT)
public class NotSoHardcoreNeoForgeClient
{
    public NotSoHardcoreNeoForgeClient(IEventBus modEventBus)
    {
        //Keybinds
        modEventBus.addListener(RegisterKeyMappingsEvent.class, event ->
        {
            event.registerCategory(NSHKeyBinding.NOT_SO_HARDCORE_CATEGORY);
            event.register(NSHKeyBinds.INFO_MENU_KEY);
        });
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class, event -> NSHKeyBinds.onEndClientTick(Minecraft.getInstance()));

        //Client networking
        modEventBus.addListener(RegisterClientPayloadHandlersEvent.class, event -> event.register(NSHS2CSynchData.ID, (payload, context) -> NSHClientNetworking.handleSyncData(payload)));
    }
}
