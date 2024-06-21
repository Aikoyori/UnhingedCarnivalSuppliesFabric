package xyz.aikoyori.unhinged_carnival_supplies.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.lwjgl.system.MathUtil;
import xyz.aikoyori.unhinged_carnival_supplies.client.UnhingedCarnivalSuppliesClient;
import xyz.aikoyori.unhinged_carnival_supplies.utils.UnhingedCarnivalUtils;

public class FunBallEntityRenderer extends EntityRenderer<FunBallEntity> {
    private static Identifier BALL_TEX = UnhingedCarnivalUtils.makeID("textures/entity/ballin.png");
    protected FunBallEntityModel model;


    public FunBallEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);

        model =  new FunBallEntityModel(ctx.getPart(UnhingedCarnivalSuppliesClient.MODEL_BALL));
    }

    @Override
    public void render(FunBallEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F + entity.getDataTracker().get(FunBallEntity.ROLL_FLOOR)*20));
        matrices.translate(0,-14/16f,0);
        model.render(matrices,vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity))),light, OverlayTexture.DEFAULT_UV,16777215);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(FunBallEntity entity) {
        return BALL_TEX;
    }
}
