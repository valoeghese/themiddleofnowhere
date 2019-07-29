package tmon;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakeFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import tmon.decorators.SkylandLoweringsDecoratorConfig;
import tmon.features.SkystoneOreFeatureConfig;

public class TMoN {
	public static ItemGroup ITEM_GROUP;

	public static ToolMaterial SKY_OAK_TOOL;
	public static ToolMaterial SKYSTONE_TOOL;
	public static ToolMaterial CERUCLASE_TOOL;

	public static Block SKYSTONE;
	public static Block SKY_DIRT;
	public static Block SKY_GRASS;
	public static Block SKYSTONE_CERUCLASE_ORE;

	public static Block CERUCLASE_BLOCK;

	public static Block SKYSTONE_BRICKS;
	public static Block SKYSTONE_BRICK_STAIRS;
	public static Block SKYSTONE_BRICK_SLAB;

	public static Block CHISELED_SKYSTONE;
	
	public static Block SKY_OAK_LOG;
	public static Block SKY_OAK_LEAVES;
	public static Block SKY_OAK_PLANKS;
	public static Block SKY_OAK_STAIRS;
	public static Block SKY_OAK_SLAB;
	public static Block SKY_OAK_SAPLING;
	public static Block SKY_OAK_DOOR;
	public static Block SKY_OAK_TRAPDOOR;
	public static Block SKY_OAK_FENCE;
	public static Block SKY_OAK_FENCE_GATE;
	
	public static Block SKY_PLUM_LOG;
	public static Block SKY_PLUM_LEAVES;
	public static Block SKY_PLUM_PLANKS;
	public static Block SKY_PLUM_STAIRS;
	public static Block SKY_PLUM_SLAB;
	public static Block SKY_PLUM_SAPLING;
	public static Block SKY_PLUM_FENCE;
	public static Block SKY_PLUM_FENCE_GATE;

	public static Block SKY_OAK_CRAFTING_TABLE;
	public static Block ALTAR;

	public static Block WHITE_FLOWERS;
	public static Block PURPLE_FLOWER;
	
	public static BlockEntityType<?> ALTAR_ENTITY;

	public static Item SKY_WOOD_STICK;
	public static Item CERUCLASE;
	public static Item CELESTIAL_PEARL;
	public static Item SKY_PLUM;

	public static Item SKY_OAK_SWORD;
	public static Item SKY_OAK_SHOVEL;
	public static Item SKY_OAK_PICKAXE;

	public static Item SKYSTONE_SWORD;
	public static Item SKYSTONE_SHOVEL;
	public static Item SKYSTONE_PICKAXE;
	public static Item SKYSTONE_AXE;
	public static Item SKYSTONE_HOE;

	public static Item CERUCLASE_SWORD;
	public static Item CERUCLASE_SHOVEL;
	public static Item CERUCLASE_PICKAXE;
	public static Item CERUCLASE_AXE;
	public static Item CERUCLASE_HOE;

	public static Item SKY_OAK_CROOK;
	public static Item CELESTIAL_STAFF;

	public static Item SKY_OAK_BUCKET;
	public static Item SKY_OAK_WATER_BUCKET;
	
	public static DimensionType SKY_DIMENSION;

	public static ChunkGeneratorType<?, ?> SKY_CHUNK_GENERATOR;

	public static SurfaceBuilder<TernarySurfaceConfig> SKY_SURFACE_BUILDER;
	public static SurfaceBuilder<TernarySurfaceConfig> SKYSTONE_ISLES_SURFACE_BUILDER;

	public static TernarySurfaceConfig SKYGRASS_SURFACE_CONFIG;
	public static TernarySurfaceConfig SKYSTONE_SURFACE_CONFIG;

	public static Feature<DefaultFeatureConfig> SKY_OAK_FEATURE;
	public static Feature<DefaultFeatureConfig> SKY_OAK_BUSH_FEATURE;
	public static Feature<DefaultFeatureConfig> LARGE_SKY_OAK_FEATURE;

	public static Feature<DefaultFeatureConfig> SKY_PLUM_FEATURE;
	public static Feature<DefaultFeatureConfig> SKY_PLUM_BUSH_FEATURE;

	public static Feature<SkystoneOreFeatureConfig> SKYSTONE_ORE_FEATURE;
	public static Feature<LakeFeatureConfig> SKY_LAKE_FEATURE;

	public static Decorator<SkylandLoweringsDecoratorConfig> SKYLAND_LOWERINGS_DECORATOR;

	public static Biome SKY_VOID_BIOME;
	public static Biome SKY_FOREST_BIOME;
	public static Biome ROCKY_ISLES_BIOME;
	public static Biome SKY_PLUM_GROOVE_BIOME;
	public static Biome LAKES_ISLES_BIOME;
	
	public static BiomeSourceType<?, ?> SKY_BIOME_SOURCE_TYPE;
}
