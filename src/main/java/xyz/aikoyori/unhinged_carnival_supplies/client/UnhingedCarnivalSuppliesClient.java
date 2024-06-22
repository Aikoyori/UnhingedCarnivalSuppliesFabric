package xyz.aikoyori.unhinged_carnival_supplies.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import xyz.aikoyori.unhinged_carnival_supplies.UnhingedCarnivalSupplies;
import xyz.aikoyori.unhinged_carnival_supplies.entity.FunBallEntityModel;
import xyz.aikoyori.unhinged_carnival_supplies.entity.FunBallEntityRenderer;
import xyz.aikoyori.unhinged_carnival_supplies.networking.OpenSpecialInventoryC2S;
import xyz.aikoyori.unhinged_carnival_supplies.screens.SpecialInventoryHandledScreen;
import xyz.aikoyori.unhinged_carnival_supplies.utils.UnhingedCarnivalUtils;

public class UnhingedCarnivalSuppliesClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_BALL = new EntityModelLayer(UnhingedCarnivalUtils.makeID("fun_ball"), "main");

    public static KeyBinding actionKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            String.format("key.%s.action", UnhingedCarnivalUtils.MOD_ID),
            GLFW.GLFW_KEY_G,
            String.format("category.%s.keys",UnhingedCarnivalUtils.MOD_ID)
            ));
    @Override
    public void onInitializeClient() {
        HandledScreens.register(UnhingedCarnivalSupplies.SPECIAL_INVENTORY_SCREEN_HANDLER, SpecialInventoryHandledScreen::new);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            /*
            while(actionKey.isPressed()){
                // SCRATCH THAT I WANNA MAKE A BALL BOUNCE
                //MinecraftClient.getInstance().setScreen(new SpecialInventoryHandledScreen());
                //ClientPlayNetworking.send(new OpenSpecialInventoryC2S(true));
            }*/
        });
        EntityModelLayerRegistry.registerModelLayer(MODEL_BALL, FunBallEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(UnhingedCarnivalSupplies.FUN_BALL_ENTITY, FunBallEntityRenderer::new);
    }
}
