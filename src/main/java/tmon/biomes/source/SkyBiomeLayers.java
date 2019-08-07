package tmon.biomes.source;

import java.util.function.LongFunction;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.biome.layer.BiomeLayerSampler;
import net.minecraft.world.biome.layer.CachingLayerContext;
import net.minecraft.world.biome.layer.CachingLayerSampler;
import net.minecraft.world.biome.layer.CellScaleLayer;
import net.minecraft.world.biome.layer.LayerFactory;
import net.minecraft.world.biome.layer.LayerSampleContext;
import net.minecraft.world.biome.layer.LayerSampler;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.level.LevelGeneratorType;
import tmon.TMoNInitializer;
import tmon.biomes.source.layers.AddSubbiomesLayer;
import tmon.biomes.source.layers.AssignSkyBiomesLayer;
import tmon.biomes.source.layers.SkyInitLayer;
import tmon.dimension.generator.SkyChunkGeneratorConfig;

public class SkyBiomeLayers {
	public static BiomeLayerSampler[] build(long seed,
			LevelGeneratorType type,
			SkyChunkGeneratorConfig config) {
		ImmutableList<LayerFactory<CachingLayerSampler>> layers = build(type, config, layerSeed -> {
			return new CachingLayerContext(25, seed, layerSeed);
		});
		BiomeLayerSampler noiseLayer = new BiomeLayerSampler(layers.get(0));
		BiomeLayerSampler biomeLayer = new BiomeLayerSampler(layers.get(1));
		BiomeLayerSampler layer3 = new BiomeLayerSampler(layers.get(2));
		return new BiomeLayerSampler[] { noiseLayer, biomeLayer, layer3 };
	}

	public static <T extends LayerSampler, C extends LayerSampleContext<T>> ImmutableList<LayerFactory<T>> build(
			LevelGeneratorType type,
			SkyChunkGeneratorConfig config,
			LongFunction<C> context) {

		LayerFactory<T> biomes;

		biomes = SkyInitLayer.INSTANCE.create(context.apply(0L));

		biomes = ScaleLayer.NORMAL.create(context.apply(10), biomes);
		biomes = ScaleLayer.FUZZY.create(context.apply(11), biomes);

		biomes = AssignSkyBiomesLayer.INSTANCE.create(context.apply(1L), biomes);

		biomes = ScaleLayer.NORMAL.create(context.apply(10), biomes);

		biomes = AddSubbiomesLayer.INSTANCE.create(context.apply(2L), biomes);

		biomes = ScaleLayer.FUZZY.create(context.apply(11), biomes);
		
		int biomeSize = TMoNInitializer.config.biomeSize; // default is 4

		for (int i = 0; i < biomeSize; i++) {
			biomes = ScaleLayer.NORMAL.create(context.apply(20 + i), biomes);
		}

		LayerFactory<T> upscaled = CellScaleLayer.INSTANCE.create(context.apply(10L), biomes);

		return ImmutableList.of(biomes, upscaled, biomes);
	}
}
