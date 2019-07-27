package tmon.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CelestialPearlItem extends Item {
	public CelestialPearlItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		if (!player.abilities.creativeMode) {
			stack.decrement(1);
		}

		world.playSound(null, player.x, player.y, player.z, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
		//if (!world.isClient) {
//			CelestialPearlEntity entity = new CelestialPearlEntity(world, player);
//			entity.setItem(stack);
//			entity.method_19207(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);
//			world.spawnEntity(entity);
		//}

		player.incrementStat(Stats.USED.getOrCreateStat(this));
		return new TypedActionResult<>(ActionResult.SUCCESS, stack);
	}
}
