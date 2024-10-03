
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.creeping.init;

import com.corrinedev.creeping.item.CreepingSkullItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import com.corrinedev.creeping.CreepingMod;
import net.minecraftforge.registries.RegistryObject;

public class CreepingModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CreepingMod.MODID);
	public static final RegistryObject<CreepingSkullItem> CREEPING_SKULL_HELMET = REGISTRY.register("creeping_skull_helmet", () -> new CreepingSkullItem(ArmorItem.Type.HELMET, new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}
