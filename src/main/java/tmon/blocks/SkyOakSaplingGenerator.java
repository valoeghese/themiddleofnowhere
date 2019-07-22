package tmon.blocks;

import java.util.Random;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import tmon.TMoN;
import tmon.features.tree.SkyTreeFeature;

public class SkyOakSaplingGenerator extends SaplingGenerator {
	@Override
	protected AbstractTreeFeature<DefaultFeatureConfig> createTreeFeature(Random var1) {
		return new SkyTreeFeature(DefaultFeatureConfig::deserialize,
				true,
				4,
				TMoN.SKY_OAK_LOG.getDefaultState(),
				TMoN.SKY_OAK_LEAVES.getDefaultState(),
				false);
	}
}
