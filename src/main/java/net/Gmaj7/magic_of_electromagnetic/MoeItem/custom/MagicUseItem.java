package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class MagicUseItem extends Item {
    public MagicUseItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        if(livingEntity instanceof Player player){
            player.getCooldowns().addCooldown(stack.getItem(), 10);
        }

    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        int energy = stack.get(MoeDataComponentTypes.MOE_ENERGY.get());
        if(energy < 100)
            livingEntity.stopUsingItem();
        else if(remainingUseDuration % 5 == 0){
            Vec3 start = livingEntity.getEyePosition().subtract(0,0.25, 0);
            Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
            MoeFunction.RayHitResult hitResult = MoeFunction.getRayHitResult(level, livingEntity, start, end, true, 0.15F);
            MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, hitResult.getEnd(), livingEntity);
            level.addFreshEntity(moeRayEntity);
            stack.set(MoeDataComponentTypes.MOE_ENERGY.get(), energy - 400);
            for (HitResult result: hitResult.getTargets()){
                if(result instanceof EntityHitResult){
                    Entity target = ((EntityHitResult) result).getEntity();
                    if(target instanceof LivingEntity)
                        target.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), livingEntity), 8);
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        MoeMagicType type = getType(itemStack);
        int energy = itemStack.get(MoeDataComponentTypes.MOE_ENERGY.get());
        if (type == MoeMagicType.RAY && energy > 20) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemStack);
        }
        else if (type == MoeMagicType.PLASMA && energy > 600) {
            PlasmaEntity plasmaEntity = new PlasmaEntity(player, level);
            itemStack.set(MoeDataComponentTypes.MOE_ENERGY.get(), energy - 600);
            plasmaEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 5, 1.5F);
            level.addFreshEntity(plasmaEntity);
            player.getCooldowns().addCooldown(itemStack.getItem(), 10);
            return InteractionResultHolder.consume(itemStack);
        }
        else return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Component typeLiteral;
        switch (getType(stack)){
            case RAY -> typeLiteral = Component.translatable("moe_ray");
            case PLASMA -> typeLiteral = Component.translatable("moe_plasma");
            case EMPTY -> typeLiteral = Component.translatable("moe_no_magic");
            default -> typeLiteral = Component.translatable("moe_no_magic");
        }
        tooltipComponents.add(typeLiteral);
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        int i = energyStorage.getEnergyStored(),j = energyStorage.getMaxEnergyStored();
        if(i < j){
            tooltipComponents.add(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored() < stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int i = stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
        int stackMaxEnergy = stack.getCapability(Capabilities.EnergyStorage.ITEM).getMaxEnergyStored();
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    private MoeMagicType getType(ItemStack itemStack){
        MoeMagicType result = MoeMagicType.EMPTY;
        if(itemStack.has(DataComponents.CONTAINER)) {
            ItemContainerContents contents = itemStack.get(DataComponents.CONTAINER);
            ItemStack typeStack = contents.getStackInSlot(0);
            Item item = typeStack.getItem();
            if(item instanceof MoeMagicTypeModuleItem) result = ((MoeMagicTypeModuleItem) item).getMagicType();
        }
        return result;
    }
}
