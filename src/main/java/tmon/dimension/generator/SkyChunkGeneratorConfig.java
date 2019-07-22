package tmon.dimension.generator;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import tmon.TMoN;

public class SkyChunkGeneratorConfig extends ChunkGeneratorConfig {
	public static final BlockState SKYSTONE = TMoN.SKYSTONE.getDefaultState();

	@Override
	public BlockState getDefaultBlock() {
		return SKYSTONE;
	}

}
