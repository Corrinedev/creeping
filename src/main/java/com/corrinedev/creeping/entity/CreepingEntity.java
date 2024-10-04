
package com.corrinedev.creeping.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;

import com.corrinedev.creeping.init.CreepingModEntities;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CreepingEntity extends Monster {

	public CreepingEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CreepingModEntities.CREEPING.get(), world);

	}

	public CreepingEntity(EntityType<CreepingEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(0.6f);
		xpReward = 0;
		setNoAi(false);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		this.goalSelector.addGoal(6, new EndermanFreezeWhenLookedAt(this));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new AvoidEntityGoal(this, Player.class, 8, 2, 2));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this,Player.class, 32));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));


	}    protected void defineSynchedData() {
		super.defineSynchedData();

	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public double getMyRidingOffset() {
		return -0.35D;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes()
				.add(Attributes.ATTACK_DAMAGE, 5)
				.add(Attributes.MAX_HEALTH, 40)
				.add(Attributes.FOLLOW_RANGE, 32)
				.add(Attributes.MOVEMENT_SPEED, 0.1f)
				.add(Attributes.ATTACK_KNOCKBACK, 0);


		return builder;
	}
	@Override
	protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
		super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.PLAYER_HEAD));
	}
	static class EndermanFreezeWhenLookedAt extends Goal {
		private final CreepingEntity enderman;
		@Nullable
		private LivingEntity target;

		public EndermanFreezeWhenLookedAt(CreepingEntity pEnderman) {
			this.enderman = pEnderman;
			this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
		}

		public boolean canUse() {
			this.target = this.enderman.getTarget();
			if (!(this.target instanceof Player)) {
				return false;
			} else {
				double d0 = this.target.distanceToSqr(this.enderman);
				return d0 > 256.0 ? false : this.enderman.isLookingAtMe((Player)this.target);
			}
		}

		public void start() {
			this.enderman.getNavigation().stop();
		}

		public void tick() {
			this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
		}
	}
	boolean isLookingAtMe(Player pPlayer) {
			Vec3 vec3 = pPlayer.getViewVector(1.0F).normalize();
			Vec3 vec31 = new Vec3(this.getX() - pPlayer.getX(), this.getEyeY() - pPlayer.getEyeY(), this.getZ() - pPlayer.getZ());
			double d0 = vec31.length();
			vec31 = vec31.normalize();
			double d1 = vec3.dot(vec31);
			return d1 > 1.0 - 0.025 / d0 ? pPlayer.hasLineOfSight(this) : false;
		}

}
