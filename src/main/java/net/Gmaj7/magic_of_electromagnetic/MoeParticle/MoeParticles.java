package net.Gmaj7.magic_of_electromagnetic.MoeParticle;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeParticles {
    public static final DeferredRegister<ParticleType<?>> MOE_PARTICLE = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EXPAND_CIRCLE_PARTICLE = MOE_PARTICLE.register("expand_circle",
            () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus){MOE_PARTICLE.register(eventBus);}
}
