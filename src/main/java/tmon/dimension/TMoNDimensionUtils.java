package tmon.dimension;

import java.util.HashSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import tmon.TMoN;

// Largely copied from SimpleVoidWorld by modmuss50
public class TMoNDimensionUtils {
	public static void teleportToSky(Entity entity, ServerWorld previousWorld, ServerWorld newWorld) {
		if (newWorld.dimension.getType() == TMoN.SKY_DIMENSION) {
			BlockPos spawnPos = new BlockPos(0, 72, 0);
			spawnVoidPlatform(newWorld, spawnPos.down());
			entity.setPositionAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
		}
	}

	public static void teleportFromSky(Entity entity, ServerWorld previousWorld, ServerWorld newWorld) {
		BlockPos spawnLocation = newWorld.getSpawnPos();

		setEntityLocation(entity, spawnLocation);
	}

	public static void spawnVoidPlatform(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() != TMoN.SKY_GRASS) {
			BlockState platformBlock = TMoN.SKYSTONE.getDefaultState();
			for (int x = -3; x < 4; x++) {
				for (int z = -3; z < 4; z++) {
					if (world.isAir(pos.add(x, 0, z))) {
						world.setBlockState(pos.add(x, 0, z), platformBlock);
					}
				}
			}
			// world.setBlockState(pos, SimpleVoidWorld.PORTAL_BLOCK.getDefaultState());
			for (Direction facing : Direction.values()) {
				if (facing.getAxis().isHorizontal()) {
					world.setBlockState(pos.up().offset(facing), Blocks.TORCH.getDefaultState());
				}
			}
		}
	}

	public static void setEntityLocation(Entity entity, BlockPos pos) {
		if (entity instanceof ServerPlayerEntity) {
			((ServerPlayerEntity) entity).networkHandler.teleportRequest(pos.getX(), pos.getY(), pos.getZ(), 0, 0,
					new HashSet<>());
			((ServerPlayerEntity) entity).networkHandler.syncWithPlayerPosition();
		} else {
			entity.setPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
		}
	}
}
