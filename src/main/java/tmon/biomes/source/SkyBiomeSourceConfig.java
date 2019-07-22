package tmon.biomes.source;

import net.minecraft.world.biome.source.BiomeSourceConfig;
import net.minecraft.world.level.LevelProperties;
import tmon.dimension.generator.SkyChunkGeneratorConfig;

public class SkyBiomeSourceConfig implements BiomeSourceConfig {
	private LevelProperties levelProperties;
	private SkyChunkGeneratorConfig generatorSettings;

	public SkyBiomeSourceConfig setLevelProperties(LevelProperties properties) {
		this.levelProperties = properties;
		return this;
	}

	public LevelProperties getLevelProperties() {
		return this.levelProperties;
	}

	public SkyBiomeSourceConfig setGeneratorSettings(SkyChunkGeneratorConfig config) {
		this.generatorSettings = config;
		return this;
	}

	public SkyChunkGeneratorConfig getGeneratorSettings() {
		return this.generatorSettings;
	}
}
