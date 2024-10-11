package com.corrinedev.creeping.event;

import com.corrinedev.creeping.entity.SkulkGoblinEntity;
import com.corrinedev.creeping.init.ModItems;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event) {

        if(event.getEntity() instanceof SkulkGoblinEntity) {

            ItemEntity itemEntity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(ModItems.CREEPING_SKULL_HELMET.get()));
            itemEntity.setPickUpDelay(20);
            LevelAccessor _level = event.getEntity().level();
                    double x = event.getEntity().getX();
                    double y = event.getEntity().getY();
                    double z = event.getEntity().getZ();
            event.getEntity().level().getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, (ServerLevel) _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "/summon item ~ ~1 ~ {Item:{id:\"creeping:creeping_skull\",Count:1,tag:{Size:1}}}");

            //Add itemEntity


        }
    }
}
