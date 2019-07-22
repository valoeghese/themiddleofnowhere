package tmon.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.container.BlockContext;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import tmon.TMoN;

@Mixin(CraftingTableContainer.class)
public abstract class MixinCraftingTableContainer {
	@Shadow
	@Final
	private BlockContext context;

	@Inject(at = @At("HEAD"), method = "canUse", cancellable = true)
	public void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
		if (canUse2(this.context, player, TMoN.SKYOAK_CRAFTING_TABLE)) {
			ci.setReturnValue(true);
			ci.cancel();
		}
	}

	private static boolean canUse2(BlockContext blockContext_1, PlayerEntity playerEntity_1, Block block_1) {
		return (Boolean) blockContext_1.run((world_1, blockPos_1) -> {
			return world_1.getBlockState(blockPos_1).getBlock() != block_1 ? false : playerEntity_1.squaredDistanceTo((double) blockPos_1.getX() + 0.5D, (double) blockPos_1.getY() + 0.5D, (double) blockPos_1.getZ() + 0.5D) <= 64.0D;
		}, true);
	}
}
