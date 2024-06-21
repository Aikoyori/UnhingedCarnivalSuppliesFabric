package xyz.aikoyori.unhinged_carnival_supplies.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.aikoyori.unhinged_carnival_supplies.UnhingedCarnivalSupplies;

public class FunBallEntity extends Entity {

    public static TrackedData<Float> ROLL_FLOOR = DataTracker.registerData(FunBallEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static TrackedData<Float> PREVIOUS_Y_VEL = DataTracker.registerData(FunBallEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static String ROLL_FLOOR_TAG = "roll";
    public static String PREVIOUS_Y_VEL_TAG = "previous_y_velocity";
    public FunBallEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    public FunBallEntity(World world, double x, double y, double z)
    {
        this(UnhingedCarnivalSupplies.FUN_BALL_ENTITY, world);
        this.setPosition(x, y, z);
    }

    @Override
    public void tick() {

        this.addVelocity(new Vec3d(0,-0.09,0));
        this.move(MovementType.SELF, this.getVelocity());
        if(this.isTouchingWater())
        {

            this.addVelocity(new Vec3d(0,0.23,0));
            this.setVelocity(this.getVelocity().multiply(0.6,0.6,0.6));
        }
        if(this.isOnGround()){
            this.setVelocity(this.getVelocity().multiply(0.1,1,0.1));
            if(MathHelper.abs((float) this.getVelocity().getY()) < 0.01){
                //if(MinecraftClient.getInstance() != null) MinecraftClient.getInstance().player.sendMessage(Text.literal("pls"));
                this.addVelocity(0,-getDataTracker().get(PREVIOUS_Y_VEL),0);
            }
        }
        else{
            this.getDataTracker().set(ROLL_FLOOR,getDataTracker().get(ROLL_FLOOR)+(float)this.getVelocity().lengthSquared());
            this.setVelocity(this.getVelocity().multiply(0.98,1,0.98));
        }

        this.getDataTracker().set(PREVIOUS_Y_VEL,(float)this.getVelocity().getY());

        super.tick();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean canHit() {

        return true;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        if(attacker.isPlayer() && !attacker.isSpectator() && !this.isRemoved())
        {
            this.discard();
        }
        return super.handleAttack(attacker);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(ROLL_FLOOR,0f);
        builder.add(PREVIOUS_Y_VEL,0f);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return super.collidesWith(other);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.getDataTracker().set(ROLL_FLOOR,nbt.getFloat(ROLL_FLOOR_TAG));
        this.getDataTracker().set(PREVIOUS_Y_VEL,nbt.getFloat(PREVIOUS_Y_VEL_TAG));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat(ROLL_FLOOR_TAG,this.getDataTracker().get(ROLL_FLOOR));
        nbt.putFloat(PREVIOUS_Y_VEL_TAG,this.getDataTracker().get(PREVIOUS_Y_VEL));

    }
}
