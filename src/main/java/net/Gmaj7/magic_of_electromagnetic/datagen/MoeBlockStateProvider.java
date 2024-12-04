package net.Gmaj7.magic_of_electromagnetic.datagen;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class MoeBlockStateProvider extends BlockStateProvider {
    public MoeBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MagicOfElectromagnetic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
    }

    private  void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
