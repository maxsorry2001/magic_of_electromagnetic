package net.Gmaj7.magic_of_electromagnetic.MoeEntity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.model.PlasmaEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PlasmaEntityRender extends EntityRenderer<PlasmaEntity> {
    private final PlasmaEntityModel plasmaEntityModel;
    public PlasmaEntityRender(EntityRendererProvider.Context context) {
        super(context);
        this.plasmaEntityModel = new PlasmaEntityModel(context.bakeLayer(PlasmaEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaEntity plasmaEntity) {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/entity/plasma_entity.png");
    }

    @Override
    public void render(PlasmaEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, p_entity.yRotO, p_entity.getYRot())));
        poseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(partialTick, p_entity.xRotO, p_entity.getXRot())));
        poseStack.scale(5F, 5F, 5F);
        VertexConsumer buffer = bufferSource.getBuffer(this.plasmaEntityModel.renderType(this.getTextureLocation(p_entity)));
        this.plasmaEntityModel.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}