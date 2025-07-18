package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class TemperatureEnergyMakerBE extends AbstractEnergyMakerBE {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    public TemperatureEnergyMakerBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.TEMPERATURE_ENERGY_MAKER_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energy.setEnergy(tag.getInt("energy"));
    }

    @Override
    protected void energyMake(AbstractEnergyMakerBE blockEntity) {
        blockEntity.getEnergy().receiveEnergy(128, false);
    }

    protected boolean canEnergyMake() {
        BlockState blockStateUp = level.getBlockState(getBlockPos().above());
        BlockState blockStateDown = level.getBlockState(getBlockPos().below());
        return (isCold(blockStateUp) && isHot(blockStateDown)) || (isHot(blockStateUp) && isCold(blockStateDown));
    }
    private boolean isHot(BlockState blockState) {
        return blockState.is(MoeTags.moeBlockTags.HOT_BLOCK) || (blockState.getBlock() instanceof AbstractFurnaceBlock && blockState.getValue(AbstractFurnaceBlock.LIT));
    }

    private boolean isCold(BlockState blockState) {
        return blockState.is(MoeTags.moeBlockTags.COLD_BLOCK);
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }
}
