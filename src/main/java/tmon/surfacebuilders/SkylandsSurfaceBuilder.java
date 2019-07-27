package tmon.surfacebuilders;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class SkylandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public SkylandsSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> deserialize) {
		super(deserialize);
	}

	@Override
	public void generate(Random random,
			Chunk chunk, 
			Biome biome, 
			int x,
			int z, 
			int y, 
			double depthNoise,
			BlockState defaultBlock, 
			BlockState defaultFluid,
			int seaLevel, 
			long seed, 
			TernarySurfaceConfig config) {
		this.generate(random, chunk, biome, x, z, y, depthNoise, defaultBlock, defaultFluid, config.getTopMaterial(), config.getUnderMaterial(), config.getUnderwaterMaterial(), seaLevel);
	}

	protected void generate(Random random,
			Chunk chunk,
			Biome biome,
			int x,
			int z,
			int y,
			double depthNoise,
			BlockState defaultBlock,
			BlockState defaultFluid,
			BlockState topMaterial,
			BlockState underMaterial,
			BlockState underwaterMaterial,
			int seaLevel) {
		BlockState top = topMaterial;
		BlockState under = underMaterial;
		BlockPos.Mutable pos = new BlockPos.Mutable();
		int depth = -1;
		int maxDepth = (int) (depthNoise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int chunkX = x & 15;
		int chunkZ = z & 15;

		for (int iy = y; iy >= 0; --iy) {
			pos.set(chunkX, iy, chunkZ);
			BlockState state = chunk.getBlockState(pos);
			if (state.isAir()) {
				depth = -1;
			} else if (state.getBlock() == defaultBlock.getBlock()) {
				if (depth == -1) {
					if (maxDepth <= 0) {
						top = Blocks.AIR.getDefaultState();
						under = defaultBlock;
					} else if (iy >= seaLevel - 4 && iy <= seaLevel + 1) {
						top = topMaterial;
						under = underMaterial;
					}

					if (iy < seaLevel && (top == null || top.isAir())) {
						if (biome.getTemperature(pos.set(x, iy, z)) < 0.15F) {
							top = Blocks.ICE.getDefaultState();
						} else {
							top = defaultFluid;
						}

						pos.set(chunkX, iy, chunkZ);
					}

					depth = maxDepth;
					if (iy >= seaLevel - 1) {
						chunk.setBlockState(pos, top, false);
					} else if (iy < seaLevel - 7 - maxDepth) {
						top = Blocks.AIR.getDefaultState();
						under = defaultBlock;
						chunk.setBlockState(pos, underwaterMaterial, false);
					} else {
						chunk.setBlockState(pos, under, false);
					}
				} else if (depth > 0) {
					--depth;
					chunk.setBlockState(pos, under, false);
					if (depth == 0 && under.getBlock() == Blocks.SAND && maxDepth > 1) {
						depth = random.nextInt(4) + Math.max(0, iy - 63);
						under = under.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
					}
				}
			}
		}

	}
}
