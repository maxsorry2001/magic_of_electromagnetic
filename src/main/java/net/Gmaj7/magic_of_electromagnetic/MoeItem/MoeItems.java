package net.Gmaj7.magic_of_electromagnetic.MoeItem;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.LcOscillatorModuleItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicUseItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MoeMagicTypeModuleItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.PowerAmplifierItem;
import net.Gmaj7.magic_of_electromagnetic.magic.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction.setEmpty;

public class MoeItems {
    public static final DeferredRegister.Items MOE_ITEM = DeferredRegister.createItems(MagicOfElectromagnetic.MODID);

    public static final Supplier<BlockItem> ELECTROMAGNETIC_ASSEMBLY_TABLE = MOE_ITEM.registerSimpleBlockItem("electromagnetic_assembly_table", MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);

    public static final Supplier<Item> RAY_MODULE = MOE_ITEM.register("ray_module",
            () -> new MoeMagicTypeModuleItem(new ElectromagneticRay(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PULSED_PLASMA_MODULE = MOE_ITEM.register("pulsed_plasma_module",
            () -> new MoeMagicTypeModuleItem(new PulsedPlasma(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PROTECT_MODULE = MOE_ITEM.register("protecting_module",
            () -> new MoeMagicTypeModuleItem(new Protecting(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EXCITING_MODULE = MOE_ITEM.register("exciting_module",
            () -> new MoeMagicTypeModuleItem(new Exciting(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ELECTRIC_FIELD_DOMAIN_MODULE = MOE_ITEM.register("electric_field_domain_module",
            () -> new MoeMagicTypeModuleItem(new ElectricFieldDomain(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMPTY_MODULE = MOE_ITEM.register("empty_module",
            () -> new MoeMagicTypeModuleItem(null, new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> IRON_LC = MOE_ITEM.register("iron_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.IRON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GOLD_LC = MOE_ITEM.register("gold_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.GOLD, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COPPER_LC = MOE_ITEM.register("copper_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.COPPER, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> NETHERITE_LC = MOE_ITEM.register("netherite_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.NETHERITE, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMPTY_LC = MOE_ITEM.register("empty_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.EMPTY, new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> IRON_POWER = MOE_ITEM.register("iron_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.IRON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GOLD_POWER = MOE_ITEM.register("gold_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.GOLD, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COPPER_POWER = MOE_ITEM.register("copper_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.COPPER, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> NETHERITE_POWER = MOE_ITEM.register("netherite_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.NETHERITE, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMPTY_POWER = MOE_ITEM.register("empty_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.EMPTY, new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> ELECTROMAGNETIC_ROD = MOE_ITEM.register("electromagnetic_rod",
            () -> new MagicUseItem(new Item.Properties().stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, setEmpty())
                    .component(MoeDataComponentTypes.MAGIC_SLOT.get(), 2)));
    public static final Supplier<Item> ELECTROMAGNETIC_BOOK = MOE_ITEM.register("electromagnetic_book",
            () -> new MagicUseItem(new Item.Properties().stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, setEmpty())
                    .component(MoeDataComponentTypes.MAGIC_SLOT.get(), 2)));
}
