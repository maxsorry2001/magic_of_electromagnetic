package net.Gmaj7.magic_of_electromagnetic.MoeGui.menu;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MoeAssemblyTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    private final ContainerLevelAccess access;
    private final int toolSlotNum = 10;
    private final int typeSlotStartNum = 2;
    private final int lcSlotNum = 1;
    private final int powerSlotNum = 0;
    private final int typeSlotEndNum = 10;
    Runnable slotUpdateListener;

    public MoeAssemblyTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public MoeAssemblyTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(MoeMenuType.ASSEMBLY_TABLE_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(inventory, 11);
        this.level = inventory.player.level();
        this.slotUpdateListener = () -> {};
        this.container = new SimpleContainer(11){
            @Override
            public void setChanged() {
                super.setChanged();
                MoeAssemblyTableMenu.this.slotsChanged(this);
                MoeAssemblyTableMenu.this.slotUpdateListener.run();
            }
        };
        this.addSlot(new Slot(this.container, powerSlotNum, 70, 10));
        this.addSlot(new Slot(this.container, lcSlotNum, 88, 10));
        for (int i = typeSlotStartNum; i < typeSlotEndNum; i++)
            this.addSlot(new Slot(this.container, i, 18 * i - 16, 40));
        this.addSlot(new Slot(this.container, toolSlotNum, 20, 10));
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index < 11) {
                if (!this.moveItemStackTo(itemstack1, 11, 47, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.slots.get(toolSlotNum).hasItem() && item instanceof  MagicUseItem && !this.moveItemStackTo(itemstack1, toolSlotNum, toolSlotNum + 1, false)){
                return ItemStack.EMPTY;
            }else if (!this.slots.get(lcSlotNum).hasItem() && item instanceof LcOscillatorModuleItem && !this.moveItemStackTo(itemstack1, lcSlotNum, lcSlotNum + 1, false)){
                return ItemStack.EMPTY;
            }else if (!this.slots.get(powerSlotNum).hasItem() && item instanceof PowerAmplifierItem && !this.moveItemStackTo(itemstack1, powerSlotNum, powerSlotNum + 1, false)){
                return ItemStack.EMPTY;
            }else if (item instanceof MoeMagicTypeModuleItem){
                boolean flag = true;
                for (int i = typeSlotStartNum; i < typeSlotEndNum; i++){
                    if(!this.slots.get(i).hasItem() && this.moveItemStackTo(itemstack1, i, i + 1, false)){
                        flag = false;
                        break;
                    }
                }
                if (flag) return ItemStack.EMPTY;
            }
            else if (index >= 11 && index < 38) {
                if (!this.moveItemStackTo(itemstack1, 38, 47, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 38 && index < 47 && !this.moveItemStackTo(itemstack1, 11, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute(((level1, blockPos) -> {
            this.clearContainer(player, this.container);
        }));
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        ItemStack toolSlot = this.slots.get(toolSlotNum).getItem();
        ItemStack toolStack = toolSlot.copy();
        this.access.execute((level1, blockPos) -> {
            if(toolStack.getItem() instanceof MagicUseItem) {
                ItemContainerContents contents = toolStack.get(DataComponents.CONTAINER);
                List<ItemStack> newList = new ArrayList<>();
                for (int i = powerSlotNum; i < typeSlotEndNum; i++){
                    ItemStack itemStack = this.slots.get(i).getItem();
                    ItemStack menuSlot = contents.getStackInSlot(i).copy();
                    if(menuSlot.getItem() instanceof IMoeModuleItem && ((IMoeModuleItem) menuSlot.getItem()).isEmpty())
                        menuSlot = ItemStack.EMPTY;
                    if(itemStack.isEmpty()){
                        if(i == powerSlotNum) newList.add(new ItemStack(MoeItems.EMPTY_POWER.get()));
                        else if (i == lcSlotNum) newList.add(new ItemStack(MoeItems.EMPTY_LC.get()));
                        else newList.add(new ItemStack(MoeItems.EMPTY_MODULE.get()));
                    }
                    else newList.add(itemStack);
                    toolSlot.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(newList));
                    this.container.setItem(i, menuSlot);
                }
            }
        });
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.container){
            ItemStack itemstack = container.getItem(toolSlotNum);
            if(!itemstack.isEmpty()){
                this.access.execute((level1, blockPos) -> {

                });
            }
        }
    }

    private void addPlayerInventory(Inventory inventory){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        }
    }

    private void addPlayerHotbar(Inventory inventory){
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
    }

    private ElectromagneticTier checkTier(ItemStack itemStack){
        ElectromagneticTier tier = ElectromagneticTier.NULL;
        if(itemStack.isEmpty()) tier = ElectromagneticTier.EMPTY;
        else if (itemStack.getItem() instanceof ElectromagneticTierItem) tier = ((ElectromagneticTierItem) itemStack.getItem()).getTier();
        return tier;
    }

    public Slot getToolSlot(){
        return this.slots.get(toolSlotNum);
    }
}
