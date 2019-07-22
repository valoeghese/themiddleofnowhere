package tmon.dimension.generator;

import java.util.function.Supplier;

import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

public class SkyChunkGeneratorType extends ChunkGeneratorType<SkyChunkGeneratorConfig, SkyChunkGenerator> {
	public SkyChunkGeneratorType(boolean buffetScreen, Supplier<SkyChunkGeneratorConfig> configSupplier) {
		super(null, buffetScreen, configSupplier);
	}

	@Override
	public SkyChunkGenerator create(World world, BiomeSource biomeSource, SkyChunkGeneratorConfig config) {
		return new SkyChunkGenerator(world, biomeSource, config);
	}

}
