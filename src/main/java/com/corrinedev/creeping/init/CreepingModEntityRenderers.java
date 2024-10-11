
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.corrinedev.creeping.init;

import com.corrinedev.creeping.client.renderer.SkulkGoblinRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreepingModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(CreepingModEntities.SKULK_GOBLIN.get(), SkulkGoblinRenderer::new);
	}
}
