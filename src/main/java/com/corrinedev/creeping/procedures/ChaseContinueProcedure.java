package com.corrinedev.creeping.procedures;

import net.minecraft.world.entity.Entity;

import com.corrinedev.creeping.entity.SkulkGoblinEntity;

public class ChaseContinueProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.getPersistentData().getBoolean("HoldingItem");
	}
}
