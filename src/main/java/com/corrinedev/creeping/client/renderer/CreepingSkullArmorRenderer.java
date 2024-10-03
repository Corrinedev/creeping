package com.corrinedev.creeping.client.renderer;

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
		this.head = new GeoBone(null, "armorHead", false, (double) 0, false, false);
	}

}
