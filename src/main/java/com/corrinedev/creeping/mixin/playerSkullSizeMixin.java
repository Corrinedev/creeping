package com.corrinedev.creeping.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.PlayerHeadItem;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

import java.util.Map;

import static net.minecraft.client.renderer.blockentity.SkullBlockRenderer.getRenderType;



@Mixin(SkullBlockRenderer.class)
public class playerSkullSizeMixin {
    private Map<SkullBlock.Type, SkullModelBase> modelByType;

    @Unique
    private static void renderSkull(@Nullable Direction pDirection, float pYRot, float pMouthAnimation, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, SkullModelBase pModel, RenderType pRenderType, float pSize) {
        pPoseStack.pushPose();
        if (pDirection == null) {
            pPoseStack.translate(0.5F, 0.0F, 0.5F);
        } else {
            float f = 0.25F;
            pPoseStack.translate(0.5F - (float) pDirection.getStepX() * 0.25F, 0.25F, 0.5F - (float) pDirection.getStepZ() * 0.25F);
        }

        pPoseStack.scale(-pSize, -pSize, pSize);
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(pRenderType);
        pModel.setupAnim(pMouthAnimation, pYRot, 0.0F);
        pModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void render(SkullBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource
            pBuffer, int pPackedLight, int pPackedOverlay) {
        float f = pBlockEntity.getAnimation(pPartialTick);
        BlockState blockstate = pBlockEntity.getBlockState();
        boolean flag = blockstate.getBlock() instanceof WallSkullBlock;
        Direction direction = flag ? (Direction) blockstate.getValue(WallSkullBlock.FACING) : null;
        int i = flag ? RotationSegment.convertToSegment(direction.getOpposite()) : (Integer) blockstate.getValue(SkullBlock.ROTATION);
        float f1 = RotationSegment.convertToDegrees(i);
        SkullBlock.Type skullblock$type = ((AbstractSkullBlock) blockstate.getBlock()).getType();
        SkullModelBase skullmodelbase = (SkullModelBase) this.modelByType.get(skullblock$type);
        RenderType rendertype = getRenderType(skullblock$type, pBlockEntity.getOwnerProfile());
        float size = 4.0f;
        renderSkull(direction, f1, f, pPoseStack, pBuffer, pPackedLight, skullmodelbase, rendertype, size);
    }
}
