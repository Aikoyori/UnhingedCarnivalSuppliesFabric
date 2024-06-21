package xyz.aikoyori.unhinged_carnival_supplies.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.unhinged_carnival_supplies.UnhingedCarnivalSupplies;
import xyz.aikoyori.unhinged_carnival_supplies.utils.UnhingedCarnivalUtils;

public class SpecialInventoryHandledScreen extends HandledScreen<SpecialInventoryScreenHandler> implements NamedScreenHandlerFactory {
    private final PlayerEntity player;
    public SpecialInventoryHandledScreen(SpecialInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        player = inventory.player;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {

    }

    @Override
    public Text getDisplayName() {
        return Text.literal("EXAMPLE");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpecialInventoryScreenHandler(syncId,playerInventory);
    }
}
