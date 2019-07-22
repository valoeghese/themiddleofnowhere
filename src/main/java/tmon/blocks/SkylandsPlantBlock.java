package tmon.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import tmon.TMoN;

public class SkylandsPlantBlock extends PlantBlock {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

	public SkylandsPlantBlock(Block.Settings settings) {
		super(settings);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ctx) {
		Vec3d offset = state.getOffsetPos(view, pos);
		return SHAPE.offset(offset.x, offset.y, offset.z);
	}

	@Override
	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.XZ;
	}

	@Override
	protected boolean canPlantOnTop(BlockState state, BlockView view, BlockPos pos) {
		Block block = state.getBlock();
		return block == TMoN.SKY_DIRT || block == TMoN.SKY_GRASS;
	}
}
