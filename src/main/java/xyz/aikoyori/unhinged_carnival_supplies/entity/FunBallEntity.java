package xyz.aikoyori.unhinged_carnival_supplies.entity;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.unhinged_carnival_supplies.UnhingedCarnivalSupplies;

public class FunBallEntity extends Entity {

    public static TrackedData<Float> ROLL_FLOOR = DataTracker.registerData(FunBallEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static TrackedData<Float> PREVIOUS_Y_VEL = DataTracker.registerData(FunBallEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static String ROLL_FLOOR_TAG = "roll";
    private final FunBallHitBoxEntity hitbox;
    public static String PREVIOUS_Y_VEL_TAG = "previous_y_velocity";
    public FunBallEntity(EntityType<?> type, World world) {
        super(type, world);

        this.hitbox = new FunBallHitBoxEntity(this);
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
            this.setVelocity(this.getVelocity().multiply(0.75,0.75,0.75));
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
        this.hitbox.setPosition(this.getPos());
        this.hitbox.updateTrackedPosition(hitbox.getX(),hitbox.getY(),hitbox.getZ());
        this.hitbox.prevX = this.prevX;
        this.hitbox.prevY = this.prevY;
        this.hitbox.prevZ = this.prevZ;
        this.hitbox.lastRenderX = this.lastRenderX;
        this.hitbox.lastRenderY = this.lastRenderY;
        this.hitbox.lastRenderZ = this.lastRenderZ;
        hitbox.tick();
        super.tick();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean canHit() {

        return true;
    }

    @Override
    public boolean handleAttack(Entity attacker) {

        if(attacker.isPlayer() && !attacker.isSpectator() && !this.isRemoved())
        {
            if(attacker.isSneaking() && this.isAttackable()){
                killIt(attacker instanceof PlayerEntity pe && pe.isCreative());
            }
            else{
                Vec3d bounceVec = attacker.getRotationVector().add(this.getVelocity());
                if(attacker.fallDistance > 0.1){
                    bounceVec = bounceVec.multiply(1,-1,1);
                }
                this.addVelocity(bounceVec);
            }
        }
        return super.handleAttack(attacker);
    }



    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.isDirect() && source.getAttacker() == null){

            killIt(true);
        }
        return super.damage(source, amount);
    }

    private void killIt(boolean dropItem){

        if(!dropItem){

        }else {this.dropStack(new ItemStack(UnhingedCarnivalSupplies.FUN_BALL_ITEM,1));}
        this.discard();
    }
    public void discardWithChild(){
        this.hitbox.discard();
        this.discard();
    }
    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(UnhingedCarnivalSupplies.FUN_BALL_ITEM);
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
        if(this.getVelocity().length() > 0.1){
            Vec3d vectopush = this.getVelocity().multiply(0.5f);
            Vec3d vectoFinal = other.getVelocity().add(vectopush);
            vectoFinal = new Vec3d(MathHelper.clamp(vectoFinal.getX(),-5,5),MathHelper.clamp(vectoFinal.getY(),-5,5),MathHelper.clamp(vectoFinal.getZ(),-5,5));
            other.setVelocity(vectoFinal);
            this.setVelocity(this.getVelocity().multiply(1/160f));
            other.updateVelocity(0.02F,other.getVelocity());
        }
        if(other instanceof FunBallEntity)
        {
            return true;
        }
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
