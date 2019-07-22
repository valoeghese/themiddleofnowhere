package tmon.blocks.entity;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import tmon.TMoN;

public class AltarBlockEntity extends BlockEntity {
	public AltarBlockEntity() {
		super(TMoN.ALTAR_ENTITY);
	}

	public boolean activate(PlayerEntity player) {
		Pair<Integer, Integer> blocks = countBlocks(getWorld(), getPos());

		int fluid = blocks.getKey();
		int stone = blocks.getValue();

		if (fluid < 25 && stone < 25) {
			player.addChatMessage(new TranslatableText("message.tmon.chi.absent_both"), true);
		} else if (stone / 2 > fluid) {
			player.addChatMessage(new TranslatableText("message.tmon.chi.absent_water"), true);
		} else if (fluid / 2 > stone) {
			player.addChatMessage(new TranslatableText("message.tmon.chi.absent_stone"), true);
		} else {
			player.addChatMessage(new TranslatableText("message.tmon.chi.balanced"), true);
		}

		return false;
	}

	public static Pair<Integer, Integer> countBlocks(World world, BlockPos center) {
		int stones = 0, fluids = 0;

		BlockPos.Mutable pos = new BlockPos.Mutable(center);
		for (int dx = -15; dx <= 15; dx++) {
			for (int dz = -15; dz <= 15; dz++) {
				int y = world.getTop(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, center.getX() + dx, center.getZ() + dz);
				if (Math.abs(center.getY() - y) >= 15) {
					continue;
				}

				for (int dy = 0; dy >= -2; dy--) {
					pos.set(center.getX() + dx, y + dy, center.getZ() + dz);

					BlockState state = world.getBlockState(pos);
					if (state.getMaterial() == Material.STONE
							|| state.getMaterial() == Material.METAL) {
						if (y == 0 || hasAirNeighbor(world, pos)) {
							stones++;
							if (state.getFluidState().getFluid() == Fluids.WATER
									|| state.getFluidState().getFluid() == Fluids.FLOWING_WATER) {
								fluids++;
								break;
							}
						}
					} else if (state.getFluidState().getFluid() == Fluids.WATER
							|| state.getFluidState().getFluid() == Fluids.FLOWING_WATER) {
						fluids++;
						break;
					} else if (state.isAir()
							|| state.getMaterial() == Material.PLANT
							|| state.getMaterial() == Material.REPLACEABLE_PLANT
							|| state.getMaterial() == Material.SNOW) {
						continue;
					} else {
						break;
					}
				}
			}
		}

		return Pair.of(fluids, stones);
	}

	public static boolean hasAirNeighbor(World world, BlockPos pos) {
		for (Direction direction : Direction.Type.HORIZONTAL) {
			BlockState state = world.getBlockState(pos.offset(direction));
			if (state.isAir()
					|| state.getMaterial() == Material.PLANT
					|| state.getMaterial() == Material.REPLACEABLE_PLANT
					|| state.getMaterial() == Material.SNOW
					|| state.getMaterial() == Material.WATER
					|| state.getMaterial() == Material.UNDERWATER_PLANT) {
				return true;
			}
		}
		return false;
	}
}
