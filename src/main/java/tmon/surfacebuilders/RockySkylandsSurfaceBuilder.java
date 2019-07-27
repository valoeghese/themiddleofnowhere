package tmon.surfacebuilders;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import tmon.TMoN;

public class RockySkylandsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
	public RockySkylandsSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> deserialize) {
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
		if (-1 <= depthNoise && depthNoise <= 1) {
			TMoN.SKY_SURFACE_BUILDER.generate(random, chunk, biome, x, z, y,
					depthNoise, defaultBlock, defaultFluid, seaLevel, seed, config);
		}
		TMoN.SKY_SURFACE_BUILDER.generate(random, chunk, biome, x, z, y,
				depthNoise, defaultBlock, defaultFluid, seaLevel, seed, TMoN.SKYSTONE_SURFACE_CONFIG);
	}
}
