package fr.factionbedrock.notsohardcore.client.registry;

import com.mojang.blaze3d.platform.InputConstants;
import fr.factionbedrock.notsohardcore.NotSoHardcore;
import fr.factionbedrock.notsohardcore.client.gui.InfoScreen;
import fr.factionbedrock.notsohardcore.config.LoadedConfig;
import fr.factionbedrock.notsohardcore.registry.NSHKeyBinding;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

//INFO_MENU_KEY registration and onEndClientTick call are done by each mod loader client entrypoint
public class NSHKeyBinds
{
    public static final KeyMapping INFO_MENU_KEY = new KeyMapping(
            "key."+ NotSoHardcore.MOD_ID+".ability",
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_H,
            NSHKeyBinding.NOT_SO_HARDCORE_CATEGORY
    );

    public static void onEndClientTick(Minecraft client)
    {
        while (INFO_MENU_KEY.consumeClick()) {
            if (client.player != null)
            {
                client.setScreenAndShow(new InfoScreen(client.player, LoadedConfig.Server.MAX_LIVES, LoadedConfig.Server.TIME_TO_REGAIN_LIFE, LoadedConfig.Server.USE_REALTIME_REGAIN));
            }
        }
    }
}
