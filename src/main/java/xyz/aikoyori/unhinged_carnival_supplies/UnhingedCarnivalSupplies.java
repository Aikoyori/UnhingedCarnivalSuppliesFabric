package xyz.aikoyori.unhinged_carnival_supplies;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import xyz.aikoyori.unhinged_carnival_supplies.entity.FunBallEntity;
import xyz.aikoyori.unhinged_carnival_supplies.item.FunBallItem;
import xyz.aikoyori.unhinged_carnival_supplies.networking.OpenSpecialInventoryC2S;
import xyz.aikoyori.unhinged_carnival_supplies.screens.SpecialInventoryHandledScreen;
import xyz.aikoyori.unhinged_carnival_supplies.screens.SpecialInventoryScreenHandler;
import xyz.aikoyori.unhinged_carnival_supplies.utils.UnhingedCarnivalUtils;

public class UnhingedCarnivalSupplies implements ModInitializer {

    public static final ComponentType<UnhingedCarnivalUtils.SPECIAL_ITEM_LOCATION> SPECIAL_ITEM_LOCATION = ComponentType.<UnhingedCarnivalUtils.SPECIAL_ITEM_LOCATION>builder().codec(UnhingedCarnivalUtils.SPECIAL_ITEMS_CODEC).build();
    public static final ScreenHandlerType<SpecialInventoryScreenHandler> SPECIAL_INVENTORY_SCREEN_HANDLER = new ScreenHandlerType<>(SpecialInventoryScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    public static final Item FUN_BALL_ITEM = Registry.register(Registries.ITEM, UnhingedCarnivalUtils.makeID("fun_ball"),new FunBallItem(new Item.Settings()));
    public static final EntityType<FunBallEntity> FUN_BALL_ENTITY = Registry.register(Registries.ENTITY_TYPE,UnhingedCarnivalUtils.makeID("fun_ball"),EntityType.Builder.<FunBallEntity>create(FunBallEntity::new,SpawnGroup.MISC).dimensions(10/16f, 10/16f).build());
    @Override
    public void onInitialize() {
        Registry.register(Registries.DATA_COMPONENT_TYPE,UnhingedCarnivalUtils.makeID("special_item_location"),SPECIAL_ITEM_LOCATION);
        Registry.register(Registries.SCREEN_HANDLER,UnhingedCarnivalUtils.makeID("special_inventory_schand"), SPECIAL_INVENTORY_SCREEN_HANDLER);
        PayloadTypeRegistry.playC2S().register(OpenSpecialInventoryC2S.ID,OpenSpecialInventoryC2S.CODEC);
        // TODO : Components to allow you to eat and store item in some inventory space@
        // TODO : Add Sword you can eat (Extra inventory slot when needed)
        // TODO : Add Glue Gun (Attach any block)
        // TODO : ADD PARTY POPPERS]
        ServerPlayNetworking.registerGlobalReceiver(OpenSpecialInventoryC2S.ID,(payload, context) -> {

        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(FUN_BALL_ITEM);
        });
    }
}
