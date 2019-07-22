package tmon.features;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import tmon.TMoN;

/** Copy of OreFeature but replacing Skystone blocks only. */
public class SkystoneOreFeature extends Feature<SkystoneOreFeatureConfig> {
	public SkystoneOreFeature(Function<Dynamic<?>, ? extends SkystoneOreFeatureConfig> deserialize) {
		super(deserialize);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, SkystoneOreFeatureConfig config) {
		float phase = random.nextFloat() * 3.1415927F;
		float radius = config.size / 8.0F;
		int size2 = MathHelper.ceil((config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
		double x1 = pos.getX() + MathHelper.sin(phase) * radius;
		double x2 = pos.getX() - MathHelper.sin(phase) * radius;
		double z1 = pos.getZ() + MathHelper.cos(phase) * radius;
		double z2 = pos.getZ() - MathHelper.cos(phase) * radius;

		double y1 = pos.getY() + random.nextInt(3) - 2;
		double y2 = pos.getY() + random.nextInt(3) - 2;
		int int_3 = pos.getX() - MathHelper.ceil(radius) - size2;
		int int_4 = pos.getY() - 2 - size2;
		int int_5 = pos.getZ() - MathHelper.ceil(radius) - size2;
		int int_6 = 2 * (MathHelper.ceil(radius) + size2);
		int int_7 = 2 * (2 + size2);

		for (int int_8 = int_3; int_8 <= int_3 + int_6; ++int_8) {
			for (int int_9 = int_5; int_9 <= int_5 + int_6; ++int_9) {
				if (int_4 <= world.getTop(Heightmap.Type.OCEAN_FLOOR_WG, int_8, int_9)) {
					return this.generateVeinPart(world, random, config, x1, x2, z1, z2, y1, y2, int_3, int_4, int_5, int_6, int_7);
				}
			}
		}

		return false;
	}

	protected boolean generateVeinPart(IWorld world, Random random, SkystoneOreFeatureConfig config, double double_1, double double_2, double double_3, double double_4, double double_5, double double_6, int int_1, int int_2, int int_3, int horizontalSize, int verticalSize) {
		int count = 0;
		BitSet bitset = new BitSet(horizontalSize * verticalSize * horizontalSize);
		BlockPos.Mutable pos = new BlockPos.Mutable();
		double[] doubles_1 = new double[config.size * 4];

		int int_8;
		double double_12;
		double double_13;
		double double_14;
		double double_15;
		for (int_8 = 0; int_8 < config.size; ++int_8) {
			float float_1 = (float) int_8 / (float) config.size;
			double_12 = MathHelper.lerp(float_1, double_1, double_2);
			double_13 = MathHelper.lerp(float_1, double_5, double_6);
			double_14 = MathHelper.lerp(float_1, double_3, double_4);
			double_15 = random.nextDouble() * config.size / 16.0D;
			double double_11 = ((MathHelper.sin(3.1415927F * float_1) + 1.0F) * double_15 + 1.0D) / 2.0D;
			doubles_1[int_8 * 4 + 0] = double_12;
			doubles_1[int_8 * 4 + 1] = double_13;
			doubles_1[int_8 * 4 + 2] = double_14;
			doubles_1[int_8 * 4 + 3] = double_11;
		}

		for (int_8 = 0; int_8 < config.size - 1; ++int_8) {
			if (doubles_1[int_8 * 4 + 3] > 0.0D) {
				for (int int_9 = int_8 + 1; int_9 < config.size; ++int_9) {
					if (doubles_1[int_9 * 4 + 3] > 0.0D) {
						double_12 = doubles_1[int_8 * 4 + 0] - doubles_1[int_9 * 4 + 0];
						double_13 = doubles_1[int_8 * 4 + 1] - doubles_1[int_9 * 4 + 1];
						double_14 = doubles_1[int_8 * 4 + 2] - doubles_1[int_9 * 4 + 2];
						double_15 = doubles_1[int_8 * 4 + 3] - doubles_1[int_9 * 4 + 3];
						if (double_15 * double_15 > double_12 * double_12 + double_13 * double_13 + double_14 * double_14) {
							if (double_15 > 0.0D) {
								doubles_1[int_9 * 4 + 3] = -1.0D;
							} else {
								doubles_1[int_8 * 4 + 3] = -1.0D;
							}
						}
					}
				}
			}
		}

		for (int_8 = 0; int_8 < config.size; ++int_8) {
			double double_16 = doubles_1[int_8 * 4 + 3];
			if (double_16 >= 0.0D) {
				double double_17 = doubles_1[int_8 * 4 + 0];
				double double_18 = doubles_1[int_8 * 4 + 1];
				double double_19 = doubles_1[int_8 * 4 + 2];
				int int_11 = Math.max(MathHelper.floor(double_17 - double_16), int_1);
				int int_12 = Math.max(MathHelper.floor(double_18 - double_16), int_2);
				int int_13 = Math.max(MathHelper.floor(double_19 - double_16), int_3);
				int int_14 = Math.max(MathHelper.floor(double_17 + double_16), int_11);
				int int_15 = Math.max(MathHelper.floor(double_18 + double_16), int_12);
				int int_16 = Math.max(MathHelper.floor(double_19 + double_16), int_13);

				for (int int_17 = int_11; int_17 <= int_14; ++int_17) {
					double double_20 = (int_17 + 0.5D - double_17) / double_16;
					if (double_20 * double_20 < 1.0D) {
						for (int int_18 = int_12; int_18 <= int_15; ++int_18) {
							double double_21 = (int_18 + 0.5D - double_18) / double_16;
							if (double_20 * double_20 + double_21 * double_21 < 1.0D) {
								for (int int_19 = int_13; int_19 <= int_16; ++int_19) {
									double double_22 = (int_19 + 0.5D - double_19) / double_16;
									if (double_20 * double_20 + double_21 * double_21 + double_22 * double_22 < 1.0D) {
										int int_20 = int_17 - int_1 + (int_18 - int_2) * horizontalSize + (int_19 - int_3) * horizontalSize * verticalSize;
										if (!bitset.get(int_20)) {
											bitset.set(int_20);
											pos.set(int_17, int_18, int_19);
											if (world.getBlockState(pos).getBlock() == TMoN.SKYSTONE) {
												world.setBlockState(pos, config.state, 2);
												++count;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return count > 0;
	}
}
