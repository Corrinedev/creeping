package com.corrinedev.creeping.item.model;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SkullBlock;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import com.corrinedev.creeping.item.CreepingSkullItem;

import java.util.Map;

import static net.minecraft.client.renderer.blockentity.SkullBlockRenderer.SKIN_BY_TYPE;

public class CreepingSkullModel extends GeoModel<CreepingSkullItem> {
	@Override
	public ResourceLocation getAnimationResource(CreepingSkullItem object) {
		return new ResourceLocation("creeping", "animations/creepingskull.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CreepingSkullItem object) {
		Minecraft minecraft = Minecraft.getInstance();
		//This sets the geo model to the nbt value of "Size"
        return switch (minecraft.player.getItemBySlot(EquipmentSlot.HEAD).getTag().getInt("Size")) {
            case 2 -> new ResourceLocation("creeping", "geo/creepingskull2.geo.json");
            case 3 -> new ResourceLocation("creeping", "geo/creepingskull3.geo.json");
            case 4 -> new ResourceLocation("creeping", "geo/creepingskull4.geo.json");
			case 5 -> new ResourceLocation("creeping", "geo/creepingskull5.geo.json");
			case 6 -> new ResourceLocation("creeping", "geo/creepingskull6.geo.json");
			case 7 -> new ResourceLocation("creeping", "geo/creepingskull7.geo.json");
			case 8 -> new ResourceLocation("creeping", "geo/creepingskull8.geo.json");
			case 9 -> new ResourceLocation("creeping", "geo/creepingskull9.geo.json");
			case 10 -> new ResourceLocation("creeping", "geo/creepingskull10.geo.json");
			case 11 -> new ResourceLocation("creeping", "geo/creepingskull11.geo.json");
			case 12 -> new ResourceLocation("creeping", "geo/creepingskull12.geo.json");
			case 13 -> new ResourceLocation("creeping", "geo/creepingskull13.geo.json");
			case 14 -> new ResourceLocation("creeping", "geo/creepingskull14.geo.json");
			case 15 -> new ResourceLocation("creeping", "geo/creepingskull15.geo.json");
			case 16 -> new ResourceLocation("creeping", "geo/creepingskull16.geo.json");

            default -> new ResourceLocation("creeping", "geo/creepingskull.geo.json");
        };

	}

	@Override
	public ResourceLocation getTextureResource(CreepingSkullItem creepingSkullItem) {
		Minecraft minecraft = Minecraft.getInstance();
		return minecraft.player.getSkinTextureLocation();
	}
}
