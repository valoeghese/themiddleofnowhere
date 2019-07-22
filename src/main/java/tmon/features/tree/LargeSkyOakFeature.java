package tmon.features.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import tmon.TMoN;

public class LargeSkyOakFeature extends SkylandsTreeFeature {
	private static final BlockState LOG = TMoN.SKY_OAK_LOG.getDefaultState();
	private static final BlockState LEAVES = TMoN.SKY_OAK_LEAVES.getDefaultState();

	public LargeSkyOakFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> deserialize, boolean notify) {
		super(deserialize, notify);
	}

	private void makeLeafLayer(ModifiableTestableWorld world, BlockPos pos, float float_1, MutableIntBoundingBox bbox, Set<BlockPos> set) {
		int size = (int) (float_1 + 0.618D);

		for (int dx = -size; dx <= size; ++dx) {
			for (int dz = -size; dz <= size; ++dz) {
				if (Math.pow(Math.abs(dx) + 0.5D, 2.0D) + Math.pow(Math.abs(dz) + 0.5D, 2.0D) <= float_1 * float_1) {
					BlockPos pos2 = pos.add(dx, 0, dz);
					if (isAirOrLeaves(world, pos2)) {
						this.setBlockState(set, world, pos2, LEAVES, bbox);
					}
				}
			}
		}
	}

	private float getBaseBranchSize(int int_1, int int_2) {
		if (int_2 < int_1 * 0.3F) {
			return -1.0F;
		}
		float float_1 = int_1 / 2.0F;
		float float_2 = float_1 - int_2;
		float float_3 = MathHelper.sqrt(float_1 * float_1 - float_2 * float_2);
		if (float_2 == 0.0F) {
			float_3 = float_1;
		} else if (Math.abs(float_2) >= float_1) {
			return 0.0F;
		}

		return float_3 * 0.5F;
	}

	private float getLeafRadiusForLayer(int int_1) {
		if (int_1 >= 0 && int_1 < 5) {
			return int_1 != 0 && int_1 != 4 ? 3.0F : 2.0F;
		}
		return -1.0F;
	}

	private void makeLeaves(ModifiableTestableWorld world, BlockPos pos, MutableIntBoundingBox bbox, Set<BlockPos> set) {
		for (int int_1 = 0; int_1 < 5; ++int_1) {
			this.makeLeafLayer(world, pos.up(int_1), this.getLeafRadiusForLayer(int_1), bbox, set);
		}
	}

	private int makeOrCheckBranch(Set<BlockPos> set, ModifiableTestableWorld world, BlockPos pos, BlockPos blockPos_2, boolean boolean_1, MutableIntBoundingBox bbox) {
		if (!boolean_1 && Objects.equals(pos, blockPos_2)) {
			return -1;
		}
		BlockPos blockPos_3 = blockPos_2.add(-pos.getX(), -pos.getY(), -pos.getZ());
		int int_1 = this.getLongestSide(blockPos_3);
		float float_1 = (float) blockPos_3.getX() / (float) int_1;
		float float_2 = (float) blockPos_3.getY() / (float) int_1;
		float float_3 = (float) blockPos_3.getZ() / (float) int_1;

		for (int int_2 = 0; int_2 <= int_1; ++int_2) {
			BlockPos blockPos_4 = pos.add(0.5F + int_2 * float_1, 0.5F + int_2 * float_2, 0.5F + int_2 * float_3);
			if (boolean_1) {
				this.setBlockState(set, world, blockPos_4, LOG.with(PillarBlock.AXIS, this.getLogAxis(pos, blockPos_4)), bbox);
			} else if (!canTreeReplace(world, blockPos_4)) {
				return int_2;
			}
		}

		return -1;
	}

	private int getLongestSide(BlockPos pos) {
		int int_1 = MathHelper.abs(pos.getX());
		int int_2 = MathHelper.abs(pos.getY());
		int int_3 = MathHelper.abs(pos.getZ());
		if (int_3 > int_1 && int_3 > int_2) {
			return int_3;
		}
		return int_2 > int_1 ? int_2 : int_1;
	}

	private Direction.Axis getLogAxis(BlockPos pos, BlockPos pos2) {
		Direction.Axis axis = Direction.Axis.Y;
		int int_1 = Math.abs(pos2.getX() - pos.getX());
		int int_2 = Math.abs(pos2.getZ() - pos.getZ());
		int int_3 = Math.max(int_1, int_2);
		if (int_3 > 0) {
			if (int_1 == int_3) {
				axis = Direction.Axis.X;
			} else if (int_2 == int_3) {
				axis = Direction.Axis.Z;
			}
		}

		return axis;
	}

	private void makeLeaves(ModifiableTestableWorld world, int int_1, BlockPos pos, List<LargeSkyOakFeature.BranchPosition> branches, MutableIntBoundingBox bbox, Set<BlockPos> set) {
		Iterator var7 = branches.iterator();

		while (var7.hasNext()) {
			LargeSkyOakFeature.BranchPosition branch = (LargeSkyOakFeature.BranchPosition) var7.next();
			if (this.isHighEnough(int_1, branch.getEndY() - pos.getY())) {
				this.makeLeaves(world, branch, bbox, set);
			}
		}

	}

	private boolean isHighEnough(int int_1, int int_2) {
		return int_2 >= int_1 * 0.2D;
	}

	private void makeTrunk(Set<BlockPos> set, ModifiableTestableWorld world, BlockPos pos, int int_1, MutableIntBoundingBox bbox) {
		this.makeOrCheckBranch(set, world, pos, pos.up(int_1), true, bbox);
	}

	private void makeBranches(Set<BlockPos> set, ModifiableTestableWorld world, int int_1, BlockPos pos, List<LargeSkyOakFeature.BranchPosition> list_1, MutableIntBoundingBox bbox) {
		Iterator var7 = list_1.iterator();

		while (var7.hasNext()) {
			LargeSkyOakFeature.BranchPosition largeOakTreeFeature$BranchPosition_1 = (LargeSkyOakFeature.BranchPosition) var7.next();
			int int_2 = largeOakTreeFeature$BranchPosition_1.getEndY();
			BlockPos blockPos_2 = new BlockPos(pos.getX(), int_2, pos.getZ());
			if (!blockPos_2.equals(largeOakTreeFeature$BranchPosition_1) && this.isHighEnough(int_1, int_2 - pos.getY())) {
				this.makeOrCheckBranch(set, world, blockPos_2, largeOakTreeFeature$BranchPosition_1, true, bbox);
			}
		}

	}

	@Override
	public boolean generate(Set<BlockPos> set, ModifiableTestableWorld world, Random random, BlockPos pos, MutableIntBoundingBox bbox) {
		Random random_2 = new Random(random.nextLong());
		int int_1 = this.getTreeHeight(set, world, pos, 5 + random_2.nextInt(12), bbox);
		if (int_1 == -1) {
			return false;
		} else {
			this.setToDirt(world, pos.down());
			int int_2 = (int) (int_1 * 0.618D);
			if (int_2 >= int_1) {
				int_2 = int_1 - 1;
			}

			int int_3 = (int) (1.382D + Math.pow(1.0D * int_1 / 13.0D, 2.0D));
			if (int_3 < 1) {
				int_3 = 1;
			}

			int int_4 = pos.getY() + int_2;
			int int_5 = int_1 - 5;
			List<LargeSkyOakFeature.BranchPosition> list_1 = Lists.newArrayList();
			list_1.add(new LargeSkyOakFeature.BranchPosition(pos.up(int_5), int_4));

			for (; int_5 >= 0; --int_5) {
				float float_1 = this.getBaseBranchSize(int_1, int_5);
				if (float_1 >= 0.0F) {
					for (int int_6 = 0; int_6 < int_3; ++int_6) {
						double double_3 = 1.0D * float_1 * (random_2.nextFloat() + 0.328D);
						double double_4 = random_2.nextFloat() * 2.0F * 3.141592653589793D;
						double double_5 = double_3 * Math.sin(double_4) + 0.5D;
						double double_6 = double_3 * Math.cos(double_4) + 0.5D;
						BlockPos blockPos_2 = pos.add(double_5, int_5 - 1, double_6);
						BlockPos blockPos_3 = blockPos_2.up(5);
						if (this.makeOrCheckBranch(set, world, blockPos_2, blockPos_3, false, bbox) == -1) {
							int int_7 = pos.getX() - blockPos_2.getX();
							int int_8 = pos.getZ() - blockPos_2.getZ();
							double double_7 = blockPos_2.getY() - Math.sqrt(int_7 * int_7 + int_8 * int_8) * 0.381D;
							int int_9 = double_7 > int_4 ? int_4 : (int) double_7;
							BlockPos blockPos_4 = new BlockPos(pos.getX(), int_9, pos.getZ());
							if (this.makeOrCheckBranch(set, world, blockPos_4, blockPos_2, false, bbox) == -1) {
								list_1.add(new LargeSkyOakFeature.BranchPosition(blockPos_2, blockPos_4.getY()));
							}
						}
					}
				}
			}

			this.makeLeaves(world, int_1, pos, list_1, bbox, set);
			this.makeTrunk(set, world, pos, int_2, bbox);
			this.makeBranches(set, world, int_1, pos, list_1, bbox);
			return true;
		}
	}

	private int getTreeHeight(Set<BlockPos> set, ModifiableTestableWorld world, BlockPos pos, int int_1, MutableIntBoundingBox bbox) {
		if (!isDirtOrGrass(world, pos.down())) {
			return -1;
		} else {
			int int_2 = this.makeOrCheckBranch(set, world, pos, pos.up(int_1 - 1), false, bbox);
			if (int_2 == -1) {
				return int_1;
			} else {
				return int_2 < 6 ? -1 : int_2;
			}
		}
	}

	static class BranchPosition extends BlockPos {
		private final int endY;

		public BranchPosition(BlockPos pos, int y) {
			super(pos.getX(), pos.getY(), pos.getZ());
			this.endY = y;
		}

		public int getEndY() {
			return this.endY;
		}
	}
}
