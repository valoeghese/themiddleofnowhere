package tmon.features;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakeFeatureConfig;
import tmon.TMoN;

/** Copy of LakeFeature but replaced blocks to sky counterparts */
public class SkyLakeFeature extends Feature<LakeFeatureConfig> {
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();

	public SkyLakeFeature(Function<Dynamic<?>, ? extends LakeFeatureConfig> deserialize) {
		super(deserialize);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos start, LakeFeatureConfig config) {
		while (start.getY() > 5 && world.isAir(start)) {
			start = start.down();
		}

		if (start.getY() <= 4) {
			return false;
		}

		start = start.down(4);
		ChunkPos chunkPos = new ChunkPos(start);
		if (!world.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_REFERENCES)
				.getStructureReferences(Feature.VILLAGE.getName())
				.isEmpty()) {
			return false;
		}

		boolean[] bitset = new boolean[2048];
		int size = random.nextInt(4) + 4;

		for (int i = 0; i < size; ++i) {
			double xSize = random.nextDouble() * 6.0D + 3.0D;
			double ySize = random.nextDouble() * 4.0D + 2.0D;
			// double ySize = random.nextDouble() * 2.0D + 2.0D;
			double zSize = random.nextDouble() * 6.0D + 3.0D;
			double centerX = random.nextDouble() * (16.0D - xSize - 2.0D) + 1.0D + xSize / 2.0D;
			double centerY = random.nextDouble() * (8.0D - ySize - 4.0D) + 2.0D + ySize / 2.0D;
			double centerZ = random.nextDouble() * (16.0D - zSize - 2.0D) + 1.0D + zSize / 2.0D;

			for (int dx = 1; dx < 15; ++dx) {
				for (int dz = 1; dz < 15; ++dz) {
					for (int dy = 1; dy < 7; ++dy) {
						double xDist = (dx - centerX) / (xSize / 2.0D);
						double yDist = (dy - centerY) / (ySize / 2.0D);
						double zDist = (dz - centerZ) / (zSize / 2.0D);
						double distSq = xDist * xDist + yDist * yDist + zDist * zDist;
						if (distSq < 1.0D) {
							bitset[(dx * 16 + dz) * 8 + dy] = true;
						}
					}
				}
			}
		}

		for (int dx = 0; dx < 16; ++dx) {
			for (int dz = 0; dz < 16; ++dz) {
				for (int dy = 0; dy < 8; ++dy) {
					boolean boolean_2 = !bitset[(dx * 16 + dz) * 8 + dy] && (dx < 15 && bitset[((dx + 1) * 16 + dz) * 8 + dy] || dx > 0 && bitset[((dx - 1) * 16 + dz) * 8 + dy] || dz < 15 && bitset[(dx * 16 + dz + 1) * 8 + dy]
							|| dz > 0 && bitset[(dx * 16 + dz - 1) * 8 + dy] || dy < 7 && bitset[(dx * 16 + dz) * 8 + dy + 1] || dy > 0 && bitset[(dx * 16 + dz) * 8 + dy - 1]);
					if (boolean_2) {
						Material material_1 = world.getBlockState(start.add(dx, dy, dz)).getMaterial();
						if (dy >= 4 && material_1.isLiquid()) {
							return false;
						}

						if (dy < 4 && !material_1.isSolid() && world.getBlockState(start.add(dx, dy, dz)) != config.state) {
							return false;
						}
					}
				}
			}
		}

		for (int i = 0; i < 16; ++i) {
			for (int dz = 0; dz < 16; ++dz) {
				for (int dy = 0; dy < 8; ++dy) {
					if (bitset[(i * 16 + dz) * 8 + dy]) {
						world.setBlockState(start.add(i, dy, dz), dy >= 4 ? CAVE_AIR : config.state, 2);
					}
				}
			}
		}

		for (int dx = 0; dx < 16; ++dx) {
			for (int dz = 0; dz < 16; ++dz) {
				for (int dy = 4; dy < 8; ++dy) {
					if (bitset[(dx * 16 + dz) * 8 + dy]) {
						BlockPos pos = start.add(dx, dy - 1, dz);
						Block block = world.getBlockState(pos).getBlock();
						if ((Block.isNaturalDirt(block) || block == TMoN.SKY_DIRT)
								&& world.getLightLevel(LightType.SKY, start.add(dx, dy, dz)) > 0) {
							world.setBlockState(pos, TMoN.SKY_GRASS.getDefaultState(), 2);
						}
					}
				}
			}
		}

		if (config.state.getMaterial() == Material.LAVA) {
			for (int dx = 0; dx < 16; ++dx) {
				for (int dz = 0; dz < 16; ++dz) {
					for (int dy = 0; dy < 8; ++dy) {
						boolean neighborsLiquid = !bitset[(dx * 16 + dz) * 8 + dy]
								&& (dx < 15 && bitset[((dx + 1) * 16 + dz) * 8 + dy]
										|| dx > 0 && bitset[((dx - 1) * 16 + dz) * 8 + dy]
										|| dz < 15 && bitset[(dx * 16 + dz + 1) * 8 + dy]
										|| dz > 0 && bitset[(dx * 16 + dz - 1) * 8 + dy]
										|| dy < 7 && bitset[(dx * 16 + dz) * 8 + dy + 1]
										|| dy > 0 && bitset[(dx * 16 + dz) * 8 + dy - 1]);
						if (neighborsLiquid && (dy < 4 || random.nextInt(2) != 0)
								&& world.getBlockState(start.add(dx, dy, dz)).getMaterial().isSolid()) {
							world.setBlockState(start.add(dx, dy, dz), TMoN.SKYSTONE.getDefaultState(), 2);
						}
					}
				}
			}
		}

		if (config.state.getMaterial() == Material.WATER) {
			for (int dx = 0; dx < 16; ++dx) {
				for (int dz = 0; dz < 16; ++dz) {
					BlockPos pos = start.add(dx, 4, dz);
					if (world.getBiome(pos).canSetSnow(world, pos, false)) {
						world.setBlockState(pos, Blocks.ICE.getDefaultState(), 2);
					}
				}
			}
		}

		return true;
	}
}
