package fr.factionbedrock.notsohardcore;

import fr.factionbedrock.notsohardcore.commands.NSHCommands;
import fr.factionbedrock.notsohardcore.events.NSHPlayerEvents;
import fr.factionbedrock.notsohardcore.packet.NSHS2CSynchData;
import fr.factionbedrock.notsohardcore.registry.NSHItems;
import fr.factionbedrock.notsohardcore.registry.NeoForgeTrackedData;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(NotSoHardcore.MOD_ID)
public class NotSoHardcoreNeoForge
{
    public NotSoHardcoreNeoForge(IEventBus modEventBus)
    {
        NotSoHardcore.onInitialize();

        //Player data attachments (created by NSHTrackedData, loaded in NotSoHardcore.onInitialize)
        NeoForgeTrackedData.ATTACHMENT_TYPES.register(modEventBus);

        //Items : NSHItems registers directly into the item registry, which NeoForge only opens during RegisterEvent
        modEventBus.addListener(RegisterEvent.class, event -> {if (event.getRegistryKey().equals(Registries.ITEM)) {NSHItems.load();}});

        //Networking (the client handler is registered in NotSoHardcoreNeoForgeClient)
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("1").playToClient(NSHS2CSynchData.ID, NSHS2CSynchData.CODEC));

        //Player events
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> NSHPlayerEvents.onPlayerJoin((ServerPlayer) event.getEntity()));
        //Commands
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> NSHCommands.registerImpl(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection()));
    }
}
