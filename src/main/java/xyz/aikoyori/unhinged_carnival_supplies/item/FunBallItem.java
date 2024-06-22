package xyz.aikoyori.unhinged_carnival_supplies.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.aikoyori.unhinged_carnival_supplies.entity.FunBallEntity;

public class FunBallItem extends Item {
    public FunBallItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getStackInHand(hand).getItem() instanceof FunBallItem)
        {
            user.getStackInHand(hand).decrementUnlessCreative(1,user);
            Vec3d spawnPos = user.getPos().add(0,user.getEyeHeight(user.getPose()),0).add(user.getRotationVector().multiply(1.5));
            FunBallEntity funBallEntity = new FunBallEntity(world,spawnPos.getX(),spawnPos.getY(),spawnPos.getZ());
            funBallEntity.setVelocity(user.getRotationVector().multiply(1));
            world.spawnEntity(funBallEntity);

            user.getItemCooldownManager().set(this,4);
            return TypedActionResult.<ItemStack>success(user.getStackInHand(hand),true);
        }
        return super.use(world, user, hand);
    }
}
