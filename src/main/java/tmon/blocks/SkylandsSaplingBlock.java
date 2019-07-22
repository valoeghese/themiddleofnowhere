package tmon.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import tmon.TMoN;

public class SkylandsSaplingBlock extends SaplingBlock {
	public SkylandsSaplingBlock(SaplingGenerator generator, Block.Settings settings) {
		super(generator, settings);
	}

	@Override
	protected boolean canPlantOnTop(BlockState state, BlockView view, BlockPos pos) {
		Block block = state.getBlock();
		return block == TMoN.SKY_DIRT || block == TMoN.SKY_GRASS;
	}
}
