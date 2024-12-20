package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> MOE_DATA_COMPONENT_TYPE =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MagicOfElectromagnetic.MODID);

    public static final Supplier<DataComponentType<Integer>> MOE_ENERGY =
            MOE_DATA_COMPONENT_TYPE.register("moe_energy",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> MAGIC_SLOT =
            MOE_DATA_COMPONENT_TYPE.register("moe_magic_slot",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());


    public static void register(IEventBus eventBus){
        MOE_DATA_COMPONENT_TYPE.register(eventBus);
    }
}
