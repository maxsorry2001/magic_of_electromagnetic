package net.Gmaj7.electrofynamic_thaumatury.MoeInit;

import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.LcOscillatorModuleItem;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.MagicCastItem;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.PowerAmplifierItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.entity.PartEntity;

import java.util.ArrayList;
import java.util.List;

public class MoeFunction {
    public static float getMagicAmount(ItemStack itemStack){
        float result = getBaseAmount(itemStack) * getBasePower(itemStack) * getStrengthRate(itemStack);
        return result;
    }
    private static float getBaseAmount(ItemStack itemStack){
        float amount = 0;
        if(itemStack.has(DataComponents.CONTAINER)){
            ItemContainerContents contents = itemStack.get(DataComponents.CONTAINER);
            ItemStack lcModule = contents.getStackInSlot(MagicCastItem.getLcNum());
            Item item = lcModule.getItem();
            if(item instanceof LcOscillatorModuleItem) amount = ((LcOscillatorModuleItem) item).getBasicAmount();
        }
        return amount;
    }

    private static float getBasePower(ItemStack itemStack){
        float power = 1;
        if(itemStack.has(DataComponents.CONTAINER)){
            ItemContainerContents contents = itemStack.get(DataComponents.CONTAINER);
            ItemStack powerModule = contents.getStackInSlot(MagicCastItem.getPowerNum());
            Item item = powerModule.getItem();
            if(item instanceof PowerAmplifierItem) power = ((PowerAmplifierItem) item).getMagnification();
        }
        return power;
    }

    public static float getStrengthRate(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).strength();
    }

    public static float getCoolDownRate(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).coolDown();
    }

    public static float getEfficiency(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA).efficiency();
    }

    public static void checkTargetEnhancement(ItemStack itemStack, LivingEntity livingEntity){
        EnhancementData enhancementData = itemStack.get(MoeDataComponentTypes.ENHANCEMENT_DATA);
        int entropy = enhancementData.entropy();
        if(entropy > 0){
            livingEntity.igniteForTicks(entropy * 20);
        }
    }

    public static HitResult checkEntityIntersecting(Entity entity, Vec3 start, Vec3 end, float bbInflation) {
        Vec3 hitPos = null;
        if (entity.isMultipartEntity()) {
            for (PartEntity p : entity.getParts()) {
                var hit = p.getBoundingBox().inflate(bbInflation).clip(start, end).orElse(null);
                if (hit != null) {
                    hitPos = hit;
                    break;
                }
            }
        } else {
            hitPos = entity.getBoundingBox().inflate(bbInflation).clip(start, end).orElse(null);
        }
        if (hitPos != null)
            return new EntityHitResult(entity, hitPos);
        else
            return BlockHitResult.miss(end, Direction.UP, BlockPos.containing(end));

    }

    public static <T> Holder<T> getHolder(Level level, ResourceKey<Registry<T>> registry, ResourceKey<T> resourceKey){
        return level.registryAccess().registryOrThrow(registry).getHolderOrThrow(resourceKey);
    }

    public static BlockHitResult getHitBlock(Level level, Entity source, Vec3 start, Vec3 end){
        return level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, source));
    }
    public static RayHitResult getLineHitResult(Level level, Entity source, Vec3 start, Vec3 end, boolean checkForBlocks, float bbInflation) {
        BlockHitResult blockHitResult;
        if (checkForBlocks) {
            blockHitResult = getHitBlock(level, source, start, end);
            end = blockHitResult.getLocation();
        }
        AABB range = source.getBoundingBox().expandTowards(end.subtract(start));
        List<HitResult> hits = new ArrayList<>();
        List<? extends Entity> entities = level.getEntities(source, range);
        for (Entity target : entities) {
            HitResult hit = checkEntityIntersecting(target, start, end, bbInflation);
            if (hit.getType() != HitResult.Type.MISS) {
                hits.add(hit);
            }
        }
        return new RayHitResult(end, hits);
    }

    public static class RayHitResult{
        private Vec3 end;
        private List<HitResult> targets;

        public RayHitResult(Vec3 end, List<HitResult> hits) {
            this.end = end;
            this.targets = hits;
        }

        public List<HitResult> getTargets() {
            return targets;
        }

        public HitResult getNearest(LivingEntity livingEntity){
            HitResult nearest = null;
            double distance = 0;
            for (HitResult target : getTargets()){
                if(nearest == null) {
                    nearest = target;
                    distance = target.distanceTo(livingEntity);
                }
                else {
                    nearest =  target.distanceTo(livingEntity) < distance ? target : nearest;
                }
            }
            return nearest;
        }

        public Vec3 getEnd() {
            return end;
        }
    }

    public static ItemContainerContents setEmpty(){
        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(MoeItems.EMPTY_POWER.get()));
        list.add(new ItemStack(MoeItems.EMPTY_LC.get()));
        for (int i = 2; i < MagicCastItem.getMaxMagicSlots(); i ++)
            list.add(new ItemStack(MoeItems.EMPTY_PRIMARY_MODULE.get()));
        return ItemContainerContents.fromItems(list);
    }
}
