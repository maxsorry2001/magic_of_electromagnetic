package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeMagicType;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ElectricFieldDomain implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ELECTRIC_FIELD_DOMAIN;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        livingEntity.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_FIELD_DOMAIN, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) (1 * MoeFunction.getStrengthRate(itemStack))));
        if(livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0,0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
