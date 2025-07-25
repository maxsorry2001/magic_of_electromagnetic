package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeMagicType;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NerveBlocking implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.NERVE_BLOCKING;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(20));
        for (LivingEntity target : list){
            if(Math.sqrt(Math.pow(target.getX() - livingEntity.getX(), 2) + Math.pow(target.getZ() - livingEntity.getZ(), 2)) < 10 && (target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == livingEntity))) {
                target.addEffect(new MobEffectInstance(MoeEffects.NERVE_BLOCKING, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) (1 * MoeFunction.getStrengthRate(itemStack))));
                MoeFunction.checkTargetEnhancement(itemStack, livingEntity);
            }
        }
        if(livingEntity.level() instanceof ServerLevel){
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 384;
    }

    @Override
    public int getBaseCooldown() {
        return 30;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
