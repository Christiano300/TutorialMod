package net.chris300.tutorialmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class EightBallItem extends Item {
	public EightBallItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (level.isClientSide())
			player.getCooldowns().addCooldown(this, 2);
		return super.use(level, player, hand);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Objects.requireNonNull(context.getPlayer()).sendSystemMessage(Component.literal("useOn was called on " + (context.getLevel().isClientSide() ? "client" : "server")));
		Level level = context.getLevel();
		if (!level.isClientSide()) {
			if (level.getBlockState(context.getClickedPos()).getDestroySpeed(level, context.getClickedPos()) >= 0) {
				level.setBlockAndUpdate(context.getClickedPos(), Blocks.AIR.defaultBlockState());
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
		if (Screen.hasShiftDown()) {
			components.add(Component.literal("A thing that can make anything dissapear if you right-click. 0.1 seconds cooldown").withStyle(ChatFormatting.YELLOW));
		} else {
			components.add(Component.literal("Press shift for more info").withStyle(ChatFormatting.AQUA));
		}
		super.appendHoverText(itemStack, level, components, tooltipFlag);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity entity, InteractionHand interactionHand) {
		player.sendSystemMessage(Component.literal("interactLivingEntity was called on " + (player.getLevel().isClientSide() ? "client" : "server")));
		if (!player.getLevel().isClientSide()) {
			entity.remove(Entity.RemovalReason.KILLED);
			return InteractionResult.SUCCESS;
		}
		return super.interactLivingEntity(itemStack, player, entity, interactionHand);
	}
}
