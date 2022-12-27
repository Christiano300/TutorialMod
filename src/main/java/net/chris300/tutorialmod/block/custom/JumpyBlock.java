package net.chris300.tutorialmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JumpyBlock extends Block {
	public JumpyBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		player.sendSystemMessage(Component.literal("Right clicked on " + (level.isClientSide() ? "client" : "server") + " with " + hand.name()));
		return InteractionResult.PASS;
	}


	@Override
	public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 2));
		}
		super.stepOn(level, pos, blockState, entity);
	}

	public void fallOn(Level level, BlockState blockState, BlockPos pos, Entity entity, float height) {
		if (entity.isSuppressingBounce()) {
			super.fallOn(level, blockState, pos, entity, height);
		} else {
			entity.causeFallDamage(height, 0.0F, DamageSource.FALL);
		}

	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity) {
		if (entity.isSuppressingBounce()) {
			super.updateEntityAfterFallOn(blockGetter, entity);
		} else {
			this.bounceUp(entity, 1.3);
		}
	}

	protected void bounceUp(Entity entity, double bounceFactor) {
		Vec3 vec3 = entity.getDeltaMovement();
		if (vec3.y < 0.0D) {
			double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setDeltaMovement(vec3.x, -vec3.y * d0 * bounceFactor, vec3.z);
		}

	}
}
