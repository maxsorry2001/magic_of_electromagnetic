package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementData;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class MagicCastItem extends Item {
    private static final int maxMagicSlots = 10;
    private static final int magicBaseSlots = 2;
    private static final int powerNum = 0;
    private static final int lcNum = 1;
    public MagicCastItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        ItemStack typeStack = getMagic(itemStack);
        MoeMagicType type = getType(itemStack);
        int energy = itemStack.get(MoeDataComponentTypes.MOE_ENERGY.get());
        if(!MoeMagicType.isEmpty(type) && typeStack.getItem() instanceof MoeMagicTypeModuleItem item
                && energy >= item.getBaseEnergyCost() && !player.getCooldowns().isOnCooldown(item)
                && !(usedHand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof BatteryItem)) {
            item.cast(player, itemStack);
            itemStack.set(MoeDataComponentTypes.MOE_ENERGY, energy - (int)(item.getBaseEnergyCost() * MoeFunction.getEfficiency(itemStack)));
            player.getCooldowns().addCooldown(item, (int) (item.getBaseCooldown() * MoeFunction.getCoolDownRate(itemStack)));
            return InteractionResultHolder.consume(itemStack);
        }
        else return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(MoeMagicType.getTranslate(getType(stack)));
        IEnergyStorage energyStorage = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        int i = energyStorage.getEnergyStored(),j = energyStorage.getMaxEnergyStored();
        if(i < j){
            tooltipComponents.add(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
        }
        EnhancementData enhancementData = stack.get(MoeDataComponentTypes.ENHANCEMENT_DATA);
        tooltipComponents.add(Component.literal("cooldown:").append(String.valueOf(enhancementData.coolDown())));
        tooltipComponents.add(Component.literal("strength:").append(String.valueOf(enhancementData.strength())));
        tooltipComponents.add(Component.literal("efficiency:").append(String.valueOf(enhancementData.efficiency())));
        tooltipComponents.add(Component.literal("potential_difference:").append(String.valueOf(enhancementData.potential_difference())));
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
        ItemStack typeStack = getMagic(itemStack);
        Item item = typeStack.getItem();
        if(item instanceof MoeMagicTypeModuleItem) result = ((MoeMagicTypeModuleItem) item).getMagicType();
        return result;
    }

    private ItemStack getMagic(ItemStack itemStack){
        if(itemStack.has(DataComponents.CONTAINER) && itemStack.has(MoeDataComponentTypes.MAGIC_SELECT)) {
            ItemContainerContents contents = itemStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            ItemStack typeStack = contents.getStackInSlot(itemStack.get(MoeDataComponentTypes.MAGIC_SELECT));
            return typeStack;
        }
        else return new ItemStack(MoeItems.EMPTY_MODULE.get());
    }

    public static int getMaxMagicSlots() {
        return maxMagicSlots;
    }

    public static int getPowerNum() {
        return powerNum;
    }

    public static int getLcNum() {
        return lcNum;
    }
}
