
package com.corrinedev.creeping.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import com.corrinedev.creeping.procedures.SkulkGoblinOnEntityTickUpdateProcedure;
import com.corrinedev.creeping.procedures.ChaseNotProcedure;
import com.corrinedev.creeping.procedures.ChaseContinueProcedure;
import com.corrinedev.creeping.init.CreepingModEntities;

public class SkulkGoblinEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(SkulkGoblinEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SkulkGoblinEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(SkulkGoblinEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_HoldingItem = SynchedEntityData.defineId(SkulkGoblinEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private boolean lastloop;
	private long lastSwing;
	public String animationprocedure = "empty";

	public SkulkGoblinEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(CreepingModEntities.SKULK_GOBLIN.get(), world);
	}

	public SkulkGoblinEntity(EntityType<SkulkGoblinEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setMaxUpStep(0.6f);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOOT, false);
		this.entityData.define(ANIMATION, "undefined");
		this.entityData.define(TEXTURE, "skulk_goblin");
		this.entityData.define(DATA_HoldingItem, false);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, (float) 12, 1.25, 1.25) {
			@Override
			public boolean canUse() {
				double x = SkulkGoblinEntity.this.getX();
				double y = SkulkGoblinEntity.this.getY();
				double z = SkulkGoblinEntity.this.getZ();
				Entity entity = SkulkGoblinEntity.this;
				Level world = SkulkGoblinEntity.this.level();
				return super.canUse() && ChaseContinueProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, (float) 24) {
			@Override
			public boolean canUse() {
				double x = SkulkGoblinEntity.this.getX();
				double y = SkulkGoblinEntity.this.getY();
				double z = SkulkGoblinEntity.this.getZ();
				Entity entity = SkulkGoblinEntity.this;
				Level world = SkulkGoblinEntity.this.level();
				return super.canUse() && ChaseNotProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new FloatGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, false));
		this.goalSelector.addGoal(1, new StealItemGoal(this));
		this.goalSelector.addGoal(1, new KnockBackGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("DataHoldingItem", this.entityData.get(DATA_HoldingItem));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Texture"))
			this.setTexture(compound.getString("Texture"));
		if (compound.contains("DataHoldingItem"))
			this.entityData.set(DATA_HoldingItem, compound.getBoolean("DataHoldingItem"));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_33597_) {
		return super.getDimensions(p_33597_).scale((float) 1);
	}

	public static void init() {
		SpawnPlacements.register(CreepingModEntities.SKULK_GOBLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		return builder;
	}

	private PlayState movementPredicate(AnimationState event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationState event) {
		if (!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
			if (!this.animationprocedure.equals(prevAnim))
				event.getController().forceAnimationReset();
			event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
			if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
				this.animationprocedure = "empty";
				event.getController().forceAnimationReset();
			}
		} else if (animationprocedure.equals("empty")) {
			prevAnim = "empty";
			return PlayState.STOP;
		}
		prevAnim = this.animationprocedure;
		return PlayState.CONTINUE;
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
		data.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
	class StealItemGoal extends Goal {
		private final SkulkGoblinEntity thief;

		public StealItemGoal(SkulkGoblinEntity thief) {
			this.thief = thief;
		}


		@Override
		public boolean canUse() {
			return true;
		}

		@Override
		public void tick() {
			if(!thief.getPersistentData().getBoolean("HoldingItem")) {
				LivingEntity closestPlayer = thief.level().getNearestPlayer(thief, 1);
				if (closestPlayer != null) {
					for (ItemStack stack : closestPlayer.getAllSlots()) {
						if (!stack.isEmpty()) {
							ItemStack stolenItem = stack.split(1);
							closestPlayer.getAllSlots();
							thief.getPersistentData().putBoolean("HoldingItem", true);
							thief.setItemSlotAndDropWhenKilled(EquipmentSlot.OFFHAND, stolenItem);
							thief.level().playSound(null, thief.blockPosition(), SoundEvents.WARDEN_TENDRIL_CLICKS, SoundSource.AMBIENT, 1.0f, 1.0f);
							break;
						}
					}
				}
			}
		}
	}
	class KnockBackGoal extends Goal {
		private final SkulkGoblinEntity thief;

		public KnockBackGoal(SkulkGoblinEntity thief) {
			this.thief = thief;
		}


		@Override
		public boolean canUse() {
			return true;
		}

		@Override
		public void tick() {
			if(thief.getPersistentData().getBoolean("HoldingItem")) {
				LivingEntity closestPlayer = thief.level().getNearestPlayer(thief, 3);
				if (closestPlayer != null) {
					closestPlayer.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20));
					if(!(closestPlayer.getPersistentData().getInt("GoblinSoundTimer") >= 1)) {
						closestPlayer.level().playSound(null, closestPlayer.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.AMBIENT, 1.0f, 1.0f);
						closestPlayer.getPersistentData().putInt("GoblinSoundTimer", 10);
					} else {
						closestPlayer.getPersistentData().putInt("GoblinSoundTimer", closestPlayer.getPersistentData().getInt("GoblinSoundTimer") -1);
					}
				}
			}
		}
	}
}
