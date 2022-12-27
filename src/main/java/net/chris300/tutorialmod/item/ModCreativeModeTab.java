package net.chris300.tutorialmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
	public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab("tutorialtab") {
		@Override
		public @NotNull ItemStack makeIcon() {
			return new ItemStack(ModItems.RAW_ZIRCON.get());
		}
	};
}
