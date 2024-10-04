package com.corrinedev.creeping.init;

import com.corrinedev.creeping.CreepingMod;
import com.corrinedev.creeping.item.CreepingSkullItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem;




public class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, CreepingMod.MODID);
	public static final RegistryObject<CreepingSkullItem> CREEPING_SKULL_HELMET = REGISTRY.register("creeping_skull", () -> new CreepingSkullItem(ArmorItem.Type.HELMET, new Item.Properties()));
	// Start of user code block custom items
	// End of user code block custom items
}