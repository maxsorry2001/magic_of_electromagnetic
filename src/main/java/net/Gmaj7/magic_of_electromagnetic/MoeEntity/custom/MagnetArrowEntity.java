package net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.MoeEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagnetArrowEntity extends AbstractArrow {
    private int liveTime;
    public MagnetArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public MagnetArrowEntity(Level level){
        super(MoeEntities.MAGNET_ARROW_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public MagnetArrowEntity(Level level, LivingEntity owner){
        super(MoeEntities.MAGNET_ARROW_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if(++tickCount > liveTime && !this.level().isClientSide()) this.discard();
        List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(10));
        for (Entity target : list){
            if (target == this.getOwner()) continue;
            Vec3 vec3 = new Vec3(this.getX() - target.getX(), this.getY() - target.getY(), this.getZ() - target.getZ());
            Vec3 vec31 = vec3.normalize().multiply(0.1, 0.1, 0.1);
            target.move(MoverType.SELF, vec31);
        }
        if(this.level().isClientSide() && tickCount % 20 == 0){
            for (int j = 1; j < 90; j++){
                double theta = j * 2 *Math.PI / 90;
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + 10 * Math.sin(theta), this.getY(), this.getZ() + 10 * Math.cos(theta), - Math.sin(theta), 0, - Math.cos(theta));
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + 5 * Math.sin(theta), this.getY(), this.getZ() + 5 * Math.cos(theta), - Math.sin(theta), 0, - Math.cos(theta));
            }
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BEACON_POWER_SELECT;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(MoeItems.EMPTY_MODULE.get());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("moe_live_time", this.liveTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.liveTime = compound.getInt("moe_live_time");
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }
}