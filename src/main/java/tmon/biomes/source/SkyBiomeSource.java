package tmon.biomes.source;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.level.LevelProperties;
import tmon.TMoN;
import tmon.dimension.generator.SkyChunkGeneratorConfig;

public class SkyBiomeSource extends BiomeSource {
	private final BiomeLayerSampler noiseLayer;
	private final BiomeLayerSampler biomeLayer;
	private final Biome[] biomes;

	public SkyBiomeSource(SkyBiomeSourceConfig config) {
		this.biomes = new Biome[] { TMoN.SKY_FOREST_BIOME };
		LevelProperties properties = config.getLevelProperties();
		SkyChunkGeneratorConfig chunkGenConfig = config.getGeneratorSettings();
		BiomeLayerSampler[] layers = SkyBiomeLayers.build(properties.getSeed(), properties.getGeneratorType(), chunkGenConfig);
		this.noiseLayer = layers[0];
		this.biomeLayer = layers[1];
	}

	public Biome getBiome(int x, int z) {
		return this.biomeLayer.sample(x, z);
	}

	public Biome getBiomeForNoiseGen(int x, int z) {
		return this.noiseLayer.sample(x, z);
	}

	public Biome[] sampleBiomes(int x, int z, int xSize, int zSize, boolean unknown) {
		return this.biomeLayer.sample(x, z, xSize, zSize);
	}

	public Set<Biome> getBiomesInArea(int x, int z, int radius) {
		int minX = x - radius >> 2;
		int minZ = z - radius >> 2;
		int maxX = x + radius >> 2;
		int maxZ = z + radius >> 2;
		int xSize = maxX - minX + 1;
		int zSize = maxZ - minZ + 1;
		Set<Biome> set = Sets.newHashSet();
		Collections.addAll(set, this.noiseLayer.sample(minX, minZ, xSize, zSize));
		return set;
	}

	public BlockPos locateBiome(int x, int z, int radius, List<Biome> biomes, Random random) {
		int minX = x - radius >> 2;
		int minZ = z - radius >> 2;
		int maxX = x + radius >> 2;
		int maxZ = z + radius >> 2;
		int xSize = maxX - minX + 1;
		int zSize = maxZ - minZ + 1;
		Biome[] biomesMap = this.noiseLayer.sample(minX, minZ, xSize, zSize);
		BlockPos pos = null;
		int hits = 0;

		for (int i = 0; i < xSize * zSize; ++i) {
			int x2 = minX + i % xSize << 2;
			int z2 = minZ + i / xSize << 2;
			if (biomes.contains(biomesMap[i])) {
				if (pos == null || random.nextInt(hits + 1) == 0) {
					pos = new BlockPos(x2, 0, z2);
				}
				++hits;
			}
		}

		return pos;
	}

	public boolean hasStructureFeature(StructureFeature<?> structure) {
		return this.structureFeatures.computeIfAbsent(structure, s -> {
			Biome[] biomes = this.biomes;
			int length = biomes.length;

			for (int i = 0; i < length; ++i) {
				Biome biome = biomes[i];
				if (biome.hasStructureFeature(s)) {
					return true;
				}
			}

			return false;
		});
	}

	public Set<BlockState> getTopMaterials() {
		if (this.topMaterials.isEmpty()) {
			Biome[] biomes = this.biomes;
			int length = biomes.length;

			for (int i = 0; i < length; ++i) {
				Biome biome = biomes[i];
				this.topMaterials.add(biome.getSurfaceConfig().getTopMaterial());
			}
		}

		return this.topMaterials;
	}
}
