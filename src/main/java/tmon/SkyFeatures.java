package tmon;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.LakeDecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.BushFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.LakeFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import tmon.biomes.TMoNBiome;
import tmon.decorators.SkylandLoweringsDecoratorConfig;
import tmon.features.SkystoneOreFeatureConfig;

public class SkyFeatures {
	public static void addSkyOres(Biome biome) {
		biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
				Biome.configureFeature(TMoN.SKYSTONE_ORE_FEATURE,
						new SkystoneOreFeatureConfig(TMoN.SKYSTONE_CERUCLASE_ORE.getDefaultState(), 9),
						Decorator.COUNT_RANGE,
						new RangeDecoratorConfig(20, 16, 0, 64)));
	}

	public static void addForestVegetation(Biome biome) {
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.RANDOM_SELECTOR,
						new RandomFeatureConfig(
								new Feature[] { TMoN.SKY_PLUM_FEATURE, TMoN.LARGE_SKYOAK_FEATURE },
								new FeatureConfig[] { FeatureConfig.DEFAULT, FeatureConfig.DEFAULT },
								new float[] { 0.15f, 0.1f },
								TMoN.SKYOAK_FEATURE,
								FeatureConfig.DEFAULT),
						Decorator.COUNT_EXTRA_HEIGHTMAP,
						new CountExtraChanceDecoratorConfig(5, 0.1F, 1)));

		addLoweringsBushes(biome);

		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.BUSH,
						new BushFeatureConfig(TMoN.WHITE_FLOWERS.getDefaultState()),
						Decorator.COUNT_HEIGHTMAP_32,
						new CountDecoratorConfig(7)));
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.BUSH,
						new BushFeatureConfig(TMoN.PURPLE_FLOWER.getDefaultState()),
						Decorator.COUNT_HEIGHTMAP_32,
						new CountDecoratorConfig(5)));
	}
	

	public static void addRockyIslesVegetation(Biome biome) {
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.RANDOM_SELECTOR,
						new RandomFeatureConfig(
								new Feature[] { TMoN.SKYOAK_BUSH_FEATURE, TMoN.SKY_PLUM_FEATURE, TMoN.LARGE_SKYOAK_FEATURE },
								new FeatureConfig[] { FeatureConfig.DEFAULT, FeatureConfig.DEFAULT, FeatureConfig.DEFAULT },
								new float[] { 0.1f, 0.05f, 0.05f },
								TMoN.SKYOAK_FEATURE,
								FeatureConfig.DEFAULT),
						Decorator.COUNT_EXTRA_HEIGHTMAP,
						new CountExtraChanceDecoratorConfig(5, 0.1F, 3)));

		addLoweringsBushes(biome);

		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.BUSH,
						new BushFeatureConfig(TMoN.WHITE_FLOWERS.getDefaultState()),
						Decorator.COUNT_HEIGHTMAP_32,
						new CountDecoratorConfig(15)));
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.BUSH,
						new BushFeatureConfig(TMoN.PURPLE_FLOWER.getDefaultState()),
						Decorator.COUNT_HEIGHTMAP_32,
						new CountDecoratorConfig(5)));
	}

	protected static void addLoweringsBushes(Biome biome) {
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.RANDOM_SELECTOR,
						createBushesConfig(),
						TMoN.SKYLAND_LOWERINGS_DECORATOR,
						new SkylandLoweringsDecoratorConfig(5, 0.1F, 1)));
	}

	public static void addSkyPlumGrooveVegetation(Biome biome) {
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(TMoN.SKY_PLUM_BUSH_FEATURE,
						FeatureConfig.DEFAULT,
						TMoN.SKYLAND_LOWERINGS_DECORATOR,
						new SkylandLoweringsDecoratorConfig(3, 0.1F, 1)));

		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.RANDOM_SELECTOR,
						new RandomFeatureConfig(
								new Feature[] { TMoN.SKY_PLUM_BUSH_FEATURE, TMoN.SKYOAK_FEATURE },
								new FeatureConfig[] { FeatureConfig.DEFAULT, FeatureConfig.DEFAULT },
								new float[] { 0.05f, 0.05f },
								TMoN.SKY_PLUM_FEATURE,
								FeatureConfig.DEFAULT),
						Decorator.COUNT_EXTRA_HEIGHTMAP,
						new CountExtraChanceDecoratorConfig(7, 0.1F, 1)));

		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.BUSH,
						new BushFeatureConfig(TMoN.WHITE_FLOWERS.getDefaultState()),
						Decorator.COUNT_HEIGHTMAP_32,
						new CountDecoratorConfig(15)));
		biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
				Biome.configureFeature(Feature.BUSH,
						new BushFeatureConfig(TMoN.PURPLE_FLOWER.getDefaultState()),
						Decorator.COUNT_HEIGHTMAP_32,
						new CountDecoratorConfig(5)));
	}

	protected static RandomFeatureConfig createBushesConfig() {
		return new RandomFeatureConfig(
				new Feature[] { TMoN.SKY_PLUM_BUSH_FEATURE },
				new FeatureConfig[] { FeatureConfig.DEFAULT },
				new float[] { 0.25f },
				TMoN.SKYOAK_BUSH_FEATURE,
				FeatureConfig.DEFAULT);
	}

	public static void addLakes(TMoNBiome biome) {
		biome.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
				Biome.configureFeature(TMoN.SKY_LAKE_FEATURE,
						new LakeFeatureConfig(Blocks.WATER.getDefaultState()),
						Decorator.WATER_LAKE,
						new LakeDecoratorConfig(4)));
	}
	
	public static void addLakesIslandsLakes(TMoNBiome biome) {
		biome.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS,
				Biome.configureFeature(TMoN.SKY_LAKE_FEATURE,
						new LakeFeatureConfig(Blocks.WATER.getDefaultState()),
						Decorator.WATER_LAKE,
						new LakeDecoratorConfig(1)));
	}

}
