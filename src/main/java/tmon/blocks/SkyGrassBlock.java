package tmon.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.FlowerFeature;
import tmon.TMoN;

// Based on GrassBlock
public class SkyGrassBlock extends Block implements Fertilizable {
	public SkyGrassBlock(Block.Settings settings) {
		super(settings);
	}

	public static boolean canSurvive(BlockState state, ViewableWorld viewworld, BlockPos pos) {
		BlockPos pos2 = pos.up();
		BlockState state2 = viewworld.getBlockState(pos2);

		int int_1 = ChunkLightProvider.method_20049(viewworld, state, pos, state2, pos2, Direction.UP, state2.getLightSubtracted(viewworld, pos2));
		return int_1 < viewworld.getMaxLightLevel();
	}

	private static boolean canSpread(BlockState state, ViewableWorld viewworld, BlockPos pos) {
		BlockPos pos2 = pos.up();
		return canSurvive(state, viewworld, pos) && !viewworld.getFluidState(pos2).matches(FluidTags.WATER);
	}

	@Override
	public void onScheduledTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!world.isClient) {
			if (!canSurvive(state, world, pos)) {
				world.setBlockState(pos, TMoN.SKY_DIRT.getDefaultState());
			} else if (world.getLightLevel(pos.up()) >= 4) {
				if (world.getLightLevel(pos.up()) >= 9) {
					BlockState state2 = this.getDefaultState();

					for (int i = 0; i < 4; ++i) {
						BlockPos pos2 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						if (world.getBlockState(pos2).getBlock() == TMoN.SKY_DIRT && canSpread(state2, world, pos2)) {
							world.setBlockState(pos2, state2);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isFertilizable(BlockView view, BlockPos pos, BlockState state, boolean flag) {
		return view.getBlockState(pos.up()).isAir();
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(World world, Random random, BlockPos pos, BlockState state) {
		BlockPos pos2 = pos.up();
		BlockState state2 = TMoN.WHITE_FLOWERS.getDefaultState();

		label48: for (int i = 0; i < 128; ++i) {
			BlockPos pos3 = pos2;

			for (int j = 0; j < i / 16; ++j) {
				pos3 = pos3.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
				if (world.getBlockState(pos3.down()).getBlock() != this
						|| isShapeFullCube(world.getBlockState(pos3).getCollisionShape(world, pos3))) {
					continue label48;
				}
			}

			BlockState state3 = world.getBlockState(pos3);

			if (state3.isAir()) {
				BlockState state4;
				if (random.nextInt(8) == 0) {
					List<ConfiguredFeature<?>> flowers = world.getBiome(pos3).getFlowerFeatures();
					if (flowers.isEmpty()) {
						continue;
					}

					state4 = ((FlowerFeature) ((DecoratedFeatureConfig) ((ConfiguredFeature<?>) flowers.get(0)).config).feature.feature).getFlowerToPlace(random, pos3);
				} else {
					state4 = state2;
				}

				if (state4.canPlaceAt(world, pos3)) {
					world.setBlockState(pos3, state4, 3);
				}
			}
		}

	}
}
