package com.corrinedev.creeping.item.model;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import com.corrinedev.creeping.item.CreepingSkullItem;

import javax.annotation.Nullable;

public class CreepingSkullModel extends GeoModel<CreepingSkullItem> {
	@Override
	public ResourceLocation getAnimationResource(CreepingSkullItem object) {
		return new ResourceLocation("creeping", "animations/creepingskull.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CreepingSkullItem object) {
		return new ResourceLocation("creeping", "geo/creepingskull.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CreepingSkullItem object) {
		return null;
	}

}
