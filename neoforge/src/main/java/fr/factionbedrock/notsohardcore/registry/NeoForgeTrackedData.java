package fr.factionbedrock.notsohardcore.registry;

import fr.factionbedrock.notsohardcore.NotSoHardcore;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

//NeoForge storage of NSHTrackedData : player data attachments, synced to clients
//(NeoForge forbids adding synced entity data to vanilla entities). Saving is still done by PlayerNbtMixin.
public class NeoForgeTrackedData
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NotSoHardcore.MOD_ID);

    public static <T> NSHTrackedValue<T> register(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, Supplier<T> defaultValue)
    {
        return new AttachmentTrackedValue<>(ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> new MutableValue<>(defaultValue.get())).sync(codec.map(MutableValue::new, MutableValue::get)).build()));
    }

    public record AttachmentTrackedValue<T>(Supplier<AttachmentType<MutableValue<T>>> type) implements NSHTrackedValue<T>
    {
        @Override public T get(Player player) {return player.getData(this.type).get();}

        @Override public void set(Player player, T value)
        {
            player.getData(this.type).set(value);
            //Values are also set while the player save is loaded, before the player is connected : syncing is only possible once connected
            //(NeoForge syncs every player attachment when the player joins and respawns)
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.connection != null) {serverPlayer.syncData(this.type.get());}
        }
    }

    //Mutable container, so that values can be changed without AttachmentHolder.setData, which always tries to sync
    public static final class MutableValue<T>
    {
        private T value;

        public MutableValue(T value) {this.value = value;}

        public T get() {return this.value;}

        public void set(T value) {this.value = value;}
    }
}
