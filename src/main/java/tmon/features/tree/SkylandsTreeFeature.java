package tmon.features.tree;

import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import tmon.TMoN;

/** Shadows some static methods to use Sky Dirt and Sky Grass blocks instead. */
public abstract class SkylandsTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig> {
	public SkylandsTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> deserialize, boolean notifyBlocks) {
		super(deserialize, notifyBlocks);
	}

	protected static boolean isNaturalDirtOrGrass(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, state -> {
			Block block = state.getBlock();
			return Block.isNaturalDirt(block)
					|| block == TMoN.SKY_DIRT
					|| block == TMoN.SKY_GRASS;
		});
	}

	protected static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, state -> {
			Block block = state.getBlock();
			return Block.isNaturalDirt(block)
					|| block == TMoN.SKY_DIRT
					|| block == TMoN.SKY_GRASS;
		});
	}

	@Override
	protected void setToDirt(ModifiableTestableWorld world, BlockPos pos) {
		if (!isNaturalDirt(world, pos)) {
			this.setBlockState(world, pos, TMoN.SKY_DIRT.getDefaultState());
		}
	}
}
