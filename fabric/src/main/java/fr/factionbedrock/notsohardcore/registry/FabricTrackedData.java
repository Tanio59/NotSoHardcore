package fr.factionbedrock.notsohardcore.registry;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//Fabric storage of NSHTrackedData : player entity data
public class FabricTrackedData
{
    //Values defined in player synched data by PlayerDataTrackerMixin
    public static final List<EntityDataTrackedValue<?>> VALUES = new ArrayList<>();

    public static <T> NSHTrackedValue<T> register(EntityDataSerializer<T> serializer, Supplier<T> defaultValue)
    {
        EntityDataTrackedValue<T> value = new EntityDataTrackedValue<>(SynchedEntityData.defineId(Player.class, serializer), defaultValue);
        VALUES.add(value);
        return value;
    }

    public record EntityDataTrackedValue<T>(EntityDataAccessor<T> accessor, Supplier<T> defaultValue) implements NSHTrackedValue<T>
    {
        @Override public T get(Player player) {return player.getEntityData().get(this.accessor);}

        @Override public void set(Player player, T value) {player.getEntityData().set(this.accessor, value);}

        public void define(SynchedEntityData.Builder builder) {builder.define(this.accessor, this.defaultValue.get());}
    }
}
