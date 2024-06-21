package xyz.aikoyori.unhinged_carnival_supplies.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import xyz.aikoyori.unhinged_carnival_supplies.UnhingedCarnivalSupplies;

public class SpecialInventoryScreenHandler extends ScreenHandler {
    public SpecialInventoryScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(UnhingedCarnivalSupplies.SPECIAL_INVENTORY_SCREEN_HANDLER,syncId);
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
