package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
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
        livingEntity.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_FIELD_DOMAIN, 200));
        livingEntity.setData(MoeAttachmentType.ELECTRIC_FIELD_DOMAIN_DAMAGE, MoeFunction.getMagicAmount(itemStack) / 2);
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }
}
