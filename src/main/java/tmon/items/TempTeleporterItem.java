package tmon.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import tmon.TMoN;

public class TempTeleporterItem extends Item {
	public TempTeleporterItem(Item.Settings settings) {
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if (player instanceof ServerPlayerEntity) {
			ServerWorld serverWorld = (ServerWorld) world;
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
			serverWorld.getServer()
					.executeSync(() -> serverPlayer.changeDimension(serverPlayer.dimension == TMoN.SKY_DIMENSION ? DimensionType.OVERWORLD : TMoN.SKY_DIMENSION));
		}

		return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, stack);
	}
}
