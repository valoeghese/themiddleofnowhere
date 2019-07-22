package tmon.features.tree;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class SkyTreeFeature extends SkylandsTreeFeature {
	protected final int height;
	private final boolean hasVinesAndCocoa;
	private final BlockState log;
	private final BlockState leaves;

	public SkyTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> deserialize, boolean notify, int height, BlockState log, BlockState leaves, boolean unused) {
		super(deserialize, notify);
		this.height = height;
		this.log = log;
		this.leaves = leaves;
		this.hasVinesAndCocoa = unused;
	}

	@Override
	public boolean generate(Set<BlockPos> set, ModifiableTestableWorld world, Random random, BlockPos pos, MutableIntBoundingBox bbox) {
		int height = this.getTreeHeight(random);
		boolean boolean_1 = true;
		if (pos.getY() >= 1 && pos.getY() + height + 1 <= 256) {
			int int_9;
			int size;
			for (int int_2 = pos.getY(); int_2 <= pos.getY() + 1 + height; ++int_2) {
				int int_3 = 1;
				if (int_2 == pos.getY()) {
					int_3 = 0;
				}

				if (int_2 >= pos.getY() + 1 + height - 2) {
					int_3 = 2;
				}

				BlockPos.Mutable blockPos$Mutable_1 = new BlockPos.Mutable();

				for (int_9 = pos.getX() - int_3; int_9 <= pos.getX() + int_3 && boolean_1; ++int_9) {
					for (size = pos.getZ() - int_3; size <= pos.getZ() + int_3 && boolean_1; ++size) {
						if (int_2 >= 0 && int_2 < 256) {
							if (!canTreeReplace(world, blockPos$Mutable_1.set(int_9, int_2, size))) {
								boolean_1 = false;
							}
						} else {
							boolean_1 = false;
						}
					}
				}
			}

			if (!boolean_1) {
				return false;
			} else if (isDirtOrGrass(world, pos.down()) && pos.getY() < 256 - height - 1) {
				this.setToDirt(world, pos.down());

				int dx;
				int z;
				BlockPos blockPos_4;
				int int_21;
				for (int_21 = pos.getY() - 3 + height; int_21 <= pos.getY() + height; ++int_21) {
					int_9 = int_21 - (pos.getY() + height);
					size = 1 - int_9 / 2;

					for (int x = pos.getX() - size; x <= pos.getX() + size; ++x) {
						dx = x - pos.getX();

						for (z = pos.getZ() - size; z <= pos.getZ() + size; ++z) {
							int dz = z - pos.getZ();
							if (Math.abs(dx) != size || Math.abs(dz) != size || random.nextInt(2) != 0 && int_9 != 0) {
								blockPos_4 = new BlockPos(x, int_21, z);
								if (isAirOrLeaves(world, blockPos_4) || isReplaceablePlant(world, blockPos_4)) {
									this.setBlockState(set, world, blockPos_4, this.leaves, bbox);
								}
							}
						}
					}
				}

				for (int_21 = 0; int_21 < height; ++int_21) {
					if (isAirOrLeaves(world, pos.up(int_21)) || isReplaceablePlant(world, pos.up(int_21))) {
						this.setBlockState(set, world, pos.up(int_21), this.log, bbox);
						if (this.hasVinesAndCocoa && int_21 > 0) {
							if (random.nextInt(3) > 0 && isAir(world, pos.add(-1, int_21, 0))) {
								this.makeVine(world, pos.add(-1, int_21, 0), VineBlock.EAST);
							}

							if (random.nextInt(3) > 0 && isAir(world, pos.add(1, int_21, 0))) {
								this.makeVine(world, pos.add(1, int_21, 0), VineBlock.WEST);
							}

							if (random.nextInt(3) > 0 && isAir(world, pos.add(0, int_21, -1))) {
								this.makeVine(world, pos.add(0, int_21, -1), VineBlock.SOUTH);
							}

							if (random.nextInt(3) > 0 && isAir(world, pos.add(0, int_21, 1))) {
								this.makeVine(world, pos.add(0, int_21, 1), VineBlock.NORTH);
							}
						}
					}
				}

				if (this.hasVinesAndCocoa) {
					for (int_21 = pos.getY() - 3 + height; int_21 <= pos.getY() + height; ++int_21) {
						int_9 = int_21 - (pos.getY() + height);
						size = 2 - int_9 / 2;
						BlockPos.Mutable blockPos$Mutable_2 = new BlockPos.Mutable();

						for (dx = pos.getX() - size; dx <= pos.getX() + size; ++dx) {
							for (z = pos.getZ() - size; z <= pos.getZ() + size; ++z) {
								blockPos$Mutable_2.set(dx, int_21, z);
								if (isLeaves(world, blockPos$Mutable_2)) {
									BlockPos blockPos_3 = blockPos$Mutable_2.west();
									blockPos_4 = blockPos$Mutable_2.east();
									BlockPos blockPos_5 = blockPos$Mutable_2.north();
									BlockPos blockPos_6 = blockPos$Mutable_2.south();
									if (random.nextInt(4) == 0 && isAir(world, blockPos_3)) {
										this.makeVineColumn(world, blockPos_3, VineBlock.EAST);
									}

									if (random.nextInt(4) == 0 && isAir(world, blockPos_4)) {
										this.makeVineColumn(world, blockPos_4, VineBlock.WEST);
									}

									if (random.nextInt(4) == 0 && isAir(world, blockPos_5)) {
										this.makeVineColumn(world, blockPos_5, VineBlock.SOUTH);
									}

									if (random.nextInt(4) == 0 && isAir(world, blockPos_6)) {
										this.makeVineColumn(world, blockPos_6, VineBlock.NORTH);
									}
								}
							}
						}
					}

					if (random.nextInt(5) == 0 && height > 5) {
						for (int_21 = 0; int_21 < 2; ++int_21) {
							Iterator var23 = Direction.Type.HORIZONTAL.iterator();

							while (var23.hasNext()) {
								Direction direction_1 = (Direction) var23.next();
								if (random.nextInt(4 - int_21) == 0) {
									Direction direction_2 = direction_1.getOpposite();
									this.makeCocoa(world, random.nextInt(3), pos.add(direction_2.getOffsetX(), height - 5 + int_21, direction_2.getOffsetZ()), direction_1);
								}
							}
						}
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	protected int getTreeHeight(Random random) {
		return this.height + random.nextInt(3);
	}

	private void makeCocoa(ModifiableWorld world, int age, BlockPos pos, Direction direction) {
		this.setBlockState(world, pos, Blocks.COCOA.getDefaultState()
				.with(CocoaBlock.AGE, age)
				.with(HorizontalFacingBlock.FACING, direction));
	}

	private void makeVine(ModifiableWorld world, BlockPos pos, BooleanProperty property) {
		this.setBlockState(world, pos, Blocks.VINE.getDefaultState().with(property, true));
	}

	private void makeVineColumn(ModifiableTestableWorld world, BlockPos pos, BooleanProperty property) {
		this.makeVine(world, pos, property);
		int count = 4;

		for (pos = pos.down(); isAir(world, pos) && count > 0; --count) {
			this.makeVine(world, pos, property);
			pos = pos.down();
		}
	}
}
