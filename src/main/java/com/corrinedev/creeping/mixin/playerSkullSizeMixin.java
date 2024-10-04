package com.corrinedev.creeping.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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


@Mixin(SkullBlockRenderer.class)
public class playerSkullSizeMixin {

    /**
     * @author Corrinedev
     * @reason Set size of skulls to helmet nbt
     */
    @Overwrite
    public static void renderSkull(@Nullable Direction pDirection, float pYRot, float pMouthAnimation, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, SkullModelBase pModel, RenderType pRenderType) {
        pPoseStack.pushPose();
        if (pDirection == null) {
            pPoseStack.translate(0.5F, 0.0F, 0.5F);
        } else {
            float f = 0.25F;
            pPoseStack.translate(0.5F - (float)pDirection.getStepX() * 0.25F, 0.25F, 0.5F - (float)pDirection.getStepZ() * 0.25F);
        }
        Minecraft minecraft = Minecraft.getInstance();
        try {
            if(minecraft.player.getItemBySlot(EquipmentSlot.HEAD).is(Items.PLAYER_HEAD)) {
                pPoseStack.scale(-minecraft.player.getItemBySlot(EquipmentSlot.HEAD).getTag().getFloat("Size"), -minecraft.player.getItemBySlot(EquipmentSlot.HEAD).getTag().getFloat("Size"), minecraft.player.getItemBySlot(EquipmentSlot.HEAD).getTag().getFloat("Size"));
            } else {
                pPoseStack.scale(- 1.0f, -1.0f, 1.0f);
            }
        } catch(Exception e) {
            pPoseStack.scale(- 1.0f, -1.0f, 1.0f);
        }
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(pRenderType);
        pModel.setupAnim(pMouthAnimation, pYRot, 0.0F);
        pModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }

}

