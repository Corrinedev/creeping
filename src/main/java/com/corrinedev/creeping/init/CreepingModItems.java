
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.creeping.init;

import com.corrinedev.creeping.helmets.CreepingHelmetItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.item.Item;

import com.corrinedev.creeping.CreepingMod;

public class CreepingModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CreepingMod.MODID);
	public static final RegistryObject<Item> CREEPING_HELMET_ITEM = REGISTRY.register("creeping_helmet", () -> new CreepingHelmetItem.Helmet());
	// Start of user code block custom items
	// End of user code block custom items
}
