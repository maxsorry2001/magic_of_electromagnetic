package net.Gmaj7.electrofynamic_thaumatury.MoeEntity;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeEntities {
    public static final DeferredRegister<EntityType<?>> MOE_ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MagicOfElectromagnetic.MODID);

    public static final Supplier<EntityType<MoeRayEntity>> MOE_RAY_ENTITY =
            MOE_ENTITY_TYPES.register("moe_ray_entity", () -> EntityType.Builder.<MoeRayEntity>of(MoeRayEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(64)
                    .build("moe_ray_entity"));
    public static final Supplier<EntityType<PulsedPlasmaEntity>> PULSED_PLASMA_ENTITY =
            MOE_ENTITY_TYPES.register("pulsed_plasma_entity", () -> EntityType.Builder.<PulsedPlasmaEntity>of(PulsedPlasmaEntity::new, MobCategory.MISC)
                    .sized(3F, 3F)
                    .clientTrackingRange(4)
                    .build("pulsed_plasma_entity"));
    public static final Supplier<EntityType<AttractBeaconEntity>> ATTRACT_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("attract_beacon_entity", () -> EntityType.Builder.<AttractBeaconEntity>of(AttractBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .build("attract_beacon_entity"));
    public static final Supplier<EntityType<PlasmaTorchBeaconEntity>> PLASMA_TORCH_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("plasma_torch_beacon_entity", () -> EntityType.Builder.<PlasmaTorchBeaconEntity>of(PlasmaTorchBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .build("plasma_torch_beacon_entity"));
    public static final Supplier<EntityType<MagmaLightingBeaconEntity>> MAGMA_LIGHTING_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("magma_lighting_entity", () -> EntityType.Builder.<MagmaLightingBeaconEntity>of(MagmaLightingBeaconEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.13F)
                    .build("magma_lighting_entity"));
    public static final Supplier<EntityType<CoulombDomainBeaconEntity>> COULOMB_DOMAIN_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("coulomb_domain_entity", () -> EntityType.Builder.<CoulombDomainBeaconEntity>of(CoulombDomainBeaconEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.13F)
                    .build("coulomb_domain_entity"));
    public static final Supplier<EntityType<MirageEntity>> MIRAGE_ENTITY =
            MOE_ENTITY_TYPES.register("mirage_entity", () -> EntityType.Builder.<MirageEntity>of(MirageEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .build("mirage_entity"));

    public static void register(IEventBus eventBus){MOE_ENTITY_TYPES.register(eventBus);}
}
