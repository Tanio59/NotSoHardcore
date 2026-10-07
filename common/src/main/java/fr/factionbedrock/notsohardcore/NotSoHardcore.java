package fr.factionbedrock.notsohardcore;

import fr.factionbedrock.notsohardcore.config.*;
import fr.factionbedrock.notsohardcore.registry.NSHTrackedData;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotSoHardcore
{
    public static final String MOD_ID = "notsohardcore";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //Common initialization, called by each mod loader entrypoint (NotSoHardcoreFabric, NotSoHardcoreNeoForge)
    //before registering items, networking, events, commands and keybinds the mod loader way
    public static void onInitialize()
    {
        NSHConfigLoader.initLocalAndServerConfig();

		NSHTrackedData.load();
    }

	public static Identifier id(String path) {return Identifier.fromNamespaceAndPath(MOD_ID, path);}
}
