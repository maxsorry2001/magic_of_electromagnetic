package net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.MoeEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeTabs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class PlasmaTorchBeaconEntity extends AbstractArrow {
    private int startTime;
    private boolean start = false;
    private float damage;
    private ItemStack itemStack;
    public PlasmaTorchBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public PlasmaTorchBeaconEntity(Level level){
        super(MoeEntities.PLASMA_TORCH_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public PlasmaTorchBeaconEntity(Level level, LivingEntity owner, ItemStack itemStack){
        super(MoeEntities.PLASMA_TORCH_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = itemStack;
    }

    @Override
    public void tick() {
        super.tick();
        if(start){
            this.startTime++;
            for (int i = 0; i < 20; i++)
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY() + 0.3 * (100 - startTime + i), this.getZ(), 0, 0, 0);
            if(startTime > 99 & level().isClientSide()){
                for (int j = 1; j < 90; j++){
                    double theta = j * 2 *Math.PI / 90;
                    for (int k = 0; k < 10; k++ )
                        this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + 5 * Math.sin(theta), this.getY() + k, this.getZ() + 5 * Math.cos(theta), 0, 0, 0);
                }
            }
            if(startTime > 100){
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(5));
                for (LivingEntity target : list){
                    if(target != this.getOwner() && Math.sqrt(Math.pow(target.getX() - this.getX(), 2) + Math.pow(target.getZ() - this.getZ(), 2)) <= 4.5 && target.getY() >= this.getBlockY()) {
                        target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), this.getOwner()), damage);
                        MoeFunction.checkTargetEnhancement(itemStack, target);
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ENCHANTMENT_TABLE_USE;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.start = true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(MoeItems.EMPTY_MODULE.get());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("moe_start_time", this.startTime);
        compound.put("moe_magic_item", this.itemStack.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.startTime = compound.getInt("moe_start_time");
        this.itemStack = ItemStack.parse(this.registryAccess(), compound.getCompound("moe_magic_item")).get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
