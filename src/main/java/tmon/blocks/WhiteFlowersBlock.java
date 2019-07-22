package tmon.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WhiteFlowersBlock extends SkylandsPlantBlock {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);

	public WhiteFlowersBlock(Block.Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView blockview, BlockPos pos, EntityContext ctx) {
		return SHAPE;
	}
}
