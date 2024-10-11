package com.corrinedev.creeping.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.cache.object.GeoBone;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import com.corrinedev.creeping.item.model.CreepingSkullModel;
import com.corrinedev.creeping.item.CreepingSkullItem;

public class CreepingSkullArmorRenderer extends GeoArmorRenderer<CreepingSkullItem> {
	public CreepingSkullArmorRenderer() {
		super(new CreepingSkullModel());
		Minecraft minecraft = Minecraft.getInstance();
		//This code is fucking useless, delete it at some point and replace it with a regular GeoBone this.head
		try {
				this.head = new GeoBone(null, "armorHead", false, minecraft.player.getItemBySlot(EquipmentSlot.HEAD).getTag().getDouble("Size"), false, false);
		} catch(Exception e) {
			this.head = new GeoBone(null, "armorHead", false, (double) 0, false, false);
		}
		this.body = new GeoBone(null, "armorBody", false, (double) 0, false, false);
		this.rightArm = new GeoBone(null, "armorRightArm", false, (double) 0, false, false);
		this.leftArm = new GeoBone(null, "armorLeftArm", false, (double) 0, false, false);
		this.rightLeg = new GeoBone(null, "armorRightLeg", false, (double) 0, false, false);
		this.leftLeg = new GeoBone(null, "armorLeftLeg", false, (double) 0, false, false);
		this.rightBoot = new GeoBone(null, "armorRightBoot", false, (double) 0, false, false);
		this.leftBoot = new GeoBone(null, "armorLeftBoot", false, (double) 0, false, false);
	}

	@Override
	public RenderType getRenderType(CreepingSkullItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
