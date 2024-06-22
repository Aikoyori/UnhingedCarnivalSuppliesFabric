package xyz.aikoyori.unhinged_carnival_supplies.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.world.World;

public class FunBallHitBoxEntity extends Entity {
    private FunBallEntity ball;
    public FunBallHitBoxEntity(FunBallEntity ball) {
        super(ball.getType(),ball.getWorld());
        this.calculateDimensions();
        this.ball = ball;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public void tick() {
        super.tick();
        this.move(MovementType.SELF,ball.getVelocity());
        this.setPosition(ball.getPos());
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1,1);
    }
    public ItemStack getPickBlockStack() {
        return this.ball.getPickBlockStack();
    }

    public boolean canHit() {
        return true;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        return ball.handleAttack(attacker);
    }

    public boolean isPartOf(Entity entity) {
        return this == entity || this.ball == entity;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }
    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        throw new UnsupportedOperationException();
    }
    public boolean shouldSave() {
        return false;
    }
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
