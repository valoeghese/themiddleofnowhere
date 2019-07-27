package tmon;

import com.google.common.collect.Sets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.LakeFeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import tmon.biomes.TMoNBiome;
import tmon.biomes.TMoNBiome.BiomeBuilder;
import tmon.biomes.source.SkyBiomeSource;
import tmon.biomes.source.SkyBiomeSourceConfig;
import tmon.blocks.AltarBlock;
import tmon.blocks.ModDoorBlock;
import tmon.blocks.ModStairsBlock;
import tmon.blocks.ModTrapdoorBlock;
import tmon.blocks.SkyGrassBlock;
import tmon.blocks.SkyOakSaplingGenerator;
import tmon.blocks.SkyPlumSaplingGenerator;
import tmon.blocks.SkylandsPlantBlock;
import tmon.blocks.SkylandsSaplingBlock;
import tmon.blocks.WhiteFlowersBlock;
import tmon.blocks.entity.AltarBlockEntity;
import tmon.decorators.SkylandLoweringsDecorator;
import tmon.decorators.SkylandLoweringsDecoratorConfig;
import tmon.dimension.SkyDimension;
import tmon.dimension.generator.SkyChunkGeneratorConfig;
import tmon.dimension.generator.SkyChunkGeneratorType;
import tmon.features.SkyLakeFeature;
import tmon.features.SkystoneOreFeature;
import tmon.features.SkystoneOreFeatureConfig;
import tmon.features.tree.LargeSkyOakFeature;
import tmon.features.tree.SkyBushFeature;
import tmon.features.tree.SkyTreeFeature;
import tmon.items.SkyOakBucketItem;
import tmon.items.StaffItem;
import tmon.items.TMoNAxeItem;
import tmon.items.TMoNPickaxeItem;
import tmon.items.TMoNToolMaterial;
import tmon.items.TempTeleporterItem;
import tmon.surfacebuilders.RockySkylandsSurfaceBuilder;
import tmon.surfacebuilders.SkylandsSurfaceBuilder;

public class TMoNInitializer implements ModInitializer {
	@Override
	public void onInitialize() {
		TMoN.ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier("tmon", "main"), () -> new ItemStack(TMoN.SKY_GRASS));

		TMoN.SKYROOT_TOOL = new TMoNToolMaterial(0, 59, 2.0F, 0, 14, () -> Ingredient.ofItems(TMoN.SKY_OAK_PLANKS));
		TMoN.SKYSTONE_TOOL = new TMoNToolMaterial(1, 131, 4.0F, 1.0F, 8, () -> Ingredient.ofItems(TMoN.SKYSTONE));
		TMoN.CERUCLASE_TOOL = new TMoNToolMaterial(2, 250, 6.0F, 2.0F, 18, () -> Ingredient.ofItems(TMoN.CERUCLASE));

		Block.Settings skystone = FabricBlockSettings.of(Material.STONE).strength(1.5F, 6.0F).breakByTool(FabricToolTags.PICKAXES).build();
		TMoN.SKYSTONE = registerWithItem("skystone", new Block(skystone));
		TMoN.SKY_DIRT = registerWithItem("sky_dirt", new Block(FabricBlockSettings.of(Material.EARTH).strength(0.5F, 0.5F).sounds(BlockSoundGroup.GRAVEL).breakByTool(FabricToolTags.SHOVELS, -1).build()));
		TMoN.SKY_GRASS = registerWithItem("sky_grass", new SkyGrassBlock(FabricBlockSettings.of(Material.ORGANIC).ticksRandomly().strength(0.6F, 0.6F).sounds(BlockSoundGroup.GRASS).breakByTool(FabricToolTags.SHOVELS, -1).build()));
		TMoN.SKYSTONE_CERUCLASE_ORE = registerWithItem("skystone_ceruclase_ore", new Block(FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, 1).build()));
		TMoN.CERUCLASE_BLOCK = registerWithItem("ceruclase_block", new Block(FabricBlockSettings.of(Material.METAL, MaterialColor.DIAMOND).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES, -1).build()));
		TMoN.SKYSTONE_BRICKS = registerWithItem("skystone_bricks", new Block(skystone));
		TMoN.SKYSTONE_BRICK_STAIRS = registerWithItem("skystone_brick_stairs", new ModStairsBlock(TMoN.SKYSTONE_BRICKS.getDefaultState(), skystone));
		TMoN.SKYSTONE_BRICK_SLAB = registerWithItem("skystone_brick_slab", new SlabBlock(skystone));
		TMoN.CHISELED_SKYSTONE = registerWithItem("chiseled_skystone", new Block(skystone));

		Block.Settings skyoak = FabricBlockSettings.of(Material.WOOD, MaterialColor.BROWN).strength(2.0F, 2.0F).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES, -1).build();
		TMoN.SKY_OAK_LOG = registerWithItem("skyoak_log", new LogBlock(MaterialColor.SPRUCE, skyoak));
		TMoN.SKY_OAK_LEAVES = registerWithItem("skyoak_leaves", new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).build()));
		TMoN.SKY_OAK_PLANKS = registerWithItem("skyoak_planks", new Block(skyoak));
		TMoN.SKY_OAK_STAIRS = registerWithItem("skyoak_stairs", new ModStairsBlock(TMoN.SKY_OAK_PLANKS.getDefaultState(), skyoak));
		TMoN.SKY_OAK_SLAB = registerWithItem("skyoak_slab", new SlabBlock(skyoak));
		TMoN.SKY_OAK_SAPLING = registerWithItem("skyoak_sapling", new SkylandsSaplingBlock(new SkyOakSaplingGenerator(), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
		TMoN.SKY_OAK_DOOR = registerWithItem("skyoak_door", new ModDoorBlock(skyoak));
		TMoN.SKY_OAK_TRAPDOOR = registerWithItem("skyoak_trapdoor", new ModTrapdoorBlock(skyoak));
		TMoN.SKY_OAK_FENCE = registerWithItem("skyoak_fence", new FenceBlock(skyoak));
		TMoN.SKY_OAK_FENCE_GATE = registerWithItem("skyoak_fence_gate", new FenceGateBlock(skyoak));

		Block.Settings skyPlum = FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 2.0F).sounds(BlockSoundGroup.WOOD).breakByTool(FabricToolTags.AXES, -1).build();
		TMoN.SKY_PLUM_LOG = registerWithItem("sky_plum_log", new LogBlock(MaterialColor.SPRUCE, skyPlum));
		TMoN.SKY_PLUM_LEAVES = registerWithItem("sky_plum_leaves", new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2F, 0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).build()));
		TMoN.SKY_PLUM_PLANKS = registerWithItem("sky_plum_planks", new Block(skyPlum));
		TMoN.SKY_PLUM_STAIRS = registerWithItem("sky_plum_stairs", new ModStairsBlock(TMoN.SKY_OAK_PLANKS.getDefaultState(), skyPlum));
		TMoN.SKY_PLUM_SLAB = registerWithItem("sky_plum_slab", new SlabBlock(skyPlum));
		TMoN.SKY_PLUM_SAPLING = registerWithItem("sky_plum_sapling", new SkylandsSaplingBlock(new SkyPlumSaplingGenerator(), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));

		TMoN.SKYOAK_CRAFTING_TABLE = registerWithItem("skyoak_crafting_table", new CraftingTableBlock(skyoak) {
		});

		TMoN.ALTAR = registerWithItem("altar", new AltarBlock(skystone));

		TMoN.WHITE_FLOWERS = registerWithItem("white_flowers", new WhiteFlowersBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));
		TMoN.PURPLE_FLOWER = registerWithItem("purple_flower", new SkylandsPlantBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).build()));

		TMoN.SKYROOT_STICK = register("skyoak_stick", new Item(createItemSettings()));
		TMoN.CERUCLASE = register("ceruclase", new Item(createItemSettings()));
		TMoN.CELESTIAL_PEARL = register("celestial_pearl", new TempTeleporterItem(createItemSettings().rarity(Rarity.UNCOMMON).maxCount(16)));
		TMoN.SKY_PLUM = register("sky_plum", new Item(createItemSettings().food(FoodComponents.APPLE)));

		TMoN.SKYOAK_SWORD = register("skyoak_sword", new SwordItem(TMoN.SKYROOT_TOOL, 3, -2.4F, createItemSettings()));
		TMoN.SKYOAK_SHOVEL = register("skyoak_shovel", new ShovelItem(TMoN.SKYROOT_TOOL, 1.5F, -3.0F, createItemSettings()));
		TMoN.SKYOAK_PICKAXE = register("skyoak_pickaxe", new TMoNPickaxeItem(TMoN.SKYROOT_TOOL, 1, -2.8F, createItemSettings()));

		TMoN.SKYSTONE_SWORD = register("skystone_sword", new SwordItem(TMoN.SKYSTONE_TOOL, 3, -2.4F, createItemSettings()));
		TMoN.SKYSTONE_SHOVEL = register("skystone_shovel", new ShovelItem(TMoN.SKYSTONE_TOOL, 1.5F, -3.0F, createItemSettings()));
		TMoN.SKYSTONE_PICKAXE = register("skystone_pickaxe", new TMoNPickaxeItem(TMoN.SKYSTONE_TOOL, 1, -2.8F, createItemSettings()));
		TMoN.SKYSTONE_AXE = register("skystone_axe", new TMoNAxeItem(TMoN.SKYSTONE_TOOL, 7, -3.2F, createItemSettings()));
		TMoN.SKYSTONE_HOE = register("skystone_hoe", new HoeItem(TMoN.SKYSTONE_TOOL, -2.0F, createItemSettings()));

		TMoN.CERUCLASE_SWORD = register("ceruclase_sword", new SwordItem(TMoN.CERUCLASE_TOOL, 3, -2.4F, createItemSettings()));
		TMoN.CERUCLASE_SHOVEL = register("ceruclase_shovel", new SwordItem(TMoN.CERUCLASE_TOOL, 3, -2.4F, createItemSettings()));
		TMoN.CERUCLASE_PICKAXE = register("ceruclase_pickaxe", new TMoNPickaxeItem(TMoN.CERUCLASE_TOOL, 1, -2.8F, createItemSettings()));
		TMoN.CERUCLASE_AXE = register("ceruclase_axe", new TMoNAxeItem(TMoN.CERUCLASE_TOOL, 6, -3.1F, createItemSettings()));
		TMoN.CERUCLASE_HOE = register("ceruclase_hoe", new HoeItem(TMoN.CERUCLASE_TOOL, -1.0F, createItemSettings()));

		TMoN.SKYOAK_CROOK = register("skyoak_crook", new StaffItem(TMoN.SKYROOT_TOOL, createItemSettings()));
		TMoN.CELESTIAL_STAFF = register("celestial_staff", new StaffItem(TMoN.SKYSTONE_TOOL, createItemSettings()));

		TMoN.SKY_OAK_BUCKET = register("skyoak_bucket", new SkyOakBucketItem(Fluids.EMPTY, createItemSettings()));
		TMoN.SKY_OAK_WATER_BUCKET = register("skyoak_water_bucket", new SkyOakBucketItem(Fluids.WATER, createItemSettings().recipeRemainder(TMoN.SKY_OAK_BUCKET)));

		TMoN.ALTAR_ENTITY = register("altar", new BlockEntityType<>(AltarBlockEntity::new, Sets.newHashSet(TMoN.ALTAR), null));

		TMoN.SKY_DIMENSION = register("sky", new DimensionType(13, "_sky", "DIM_SKY",
				SkyDimension::new, true) {
		});

		TMoN.SKY_CHUNK_GENERATOR = register("sky", new SkyChunkGeneratorType(false, SkyChunkGeneratorConfig::new));

		TMoN.SKY_SURFACE_BUILDER = register("skylands", new SkylandsSurfaceBuilder(TernarySurfaceConfig::deserialize));
		TMoN.SKYSTONE_ISLES_SURFACE_BUILDER = register("rocky_skylands", new RockySkylandsSurfaceBuilder(TernarySurfaceConfig::deserialize));

		TMoN.SKYGRASS_SURFACE_CONFIG = new TernarySurfaceConfig(
				TMoN.SKY_GRASS.getDefaultState(),
				TMoN.SKY_DIRT.getDefaultState(),
				TMoN.SKYSTONE.getDefaultState());
		TMoN.SKYSTONE_SURFACE_CONFIG = new TernarySurfaceConfig(
				TMoN.SKYSTONE.getDefaultState(),
				TMoN.SKYSTONE.getDefaultState(),
				TMoN.SKYSTONE.getDefaultState());

		TMoN.SKYOAK_FEATURE = register("skyoak", new SkyTreeFeature(DefaultFeatureConfig::deserialize,
				false,
				4,
				TMoN.SKY_OAK_LOG.getDefaultState(),
				TMoN.SKY_OAK_LEAVES.getDefaultState(),
				false));
		TMoN.SKYOAK_BUSH_FEATURE = register("skyoak_bush", new SkyBushFeature(DefaultFeatureConfig::deserialize,
				TMoN.SKY_OAK_LOG.getDefaultState(),
				TMoN.SKY_OAK_LEAVES.getDefaultState()));
		TMoN.LARGE_SKYOAK_FEATURE = register("large_skyoak", new LargeSkyOakFeature(DefaultFeatureConfig::deserialize, false));

		TMoN.SKY_PLUM_FEATURE = register("sky_plum", new SkyTreeFeature(DefaultFeatureConfig::deserialize,
				false,
				3,
				TMoN.SKY_PLUM_LOG.getDefaultState(),
				TMoN.SKY_PLUM_LEAVES.getDefaultState(),
				false));
		TMoN.SKY_PLUM_BUSH_FEATURE = register("sky_plum_bush", new SkyBushFeature(DefaultFeatureConfig::deserialize,
				TMoN.SKY_PLUM_LOG.getDefaultState(),
				TMoN.SKY_PLUM_LEAVES.getDefaultState()));

		TMoN.SKYSTONE_ORE_FEATURE = register("sky_ore", new SkystoneOreFeature(SkystoneOreFeatureConfig::deserialize));
		TMoN.SKY_LAKE_FEATURE = register("sky_lake", new SkyLakeFeature(LakeFeatureConfig::deserialize));

		TMoN.SKYLAND_LOWERINGS_DECORATOR = register("count_skylands_lowerings", new SkylandLoweringsDecorator(SkylandLoweringsDecoratorConfig::deserialize));

		TMoN.SKY_VOID_BIOME = register("sky_void", TMoNBiome.create(builder -> {
			builder.configureSurfaceBuilder(TMoN.SKY_SURFACE_BUILDER, TMoN.SKYGRASS_SURFACE_CONFIG);
			builder.depth(-3F);
			setDefaultSettings(builder);
			builder.parent((String) null);

			builder.apply(biome -> {
				SkyFeatures.addSkyOres(biome);
			});
		}));
		TMoN.SKY_FOREST_BIOME = register("sky_forest", TMoNBiome.create(builder -> {
			builder.configureSurfaceBuilder(TMoN.SKY_SURFACE_BUILDER, TMoN.SKYGRASS_SURFACE_CONFIG);
			builder.depth(0F);
			setDefaultSettings(builder);
			builder.parent((String) null);

			builder.apply(biome -> {
				SkyFeatures.addSkyOres(biome);

				SkyFeatures.addForestVegetation(biome);
				SkyFeatures.addLakes(biome);
			});
		}));
		TMoN.ROCKY_ISLES_BIOME = register("rocky_isles", TMoNBiome.create(builder -> {
			builder.configureSurfaceBuilder(TMoN.SKYSTONE_ISLES_SURFACE_BUILDER, TMoN.SKYGRASS_SURFACE_CONFIG);
			builder.depth(-0.5F);
			setDefaultSettings(builder);
			builder.parent((String) null);

			builder.apply(biome -> {
				SkyFeatures.addSkyOres(biome);

				SkyFeatures.addRockyIslesVegetation(biome);
				SkyFeatures.addLakes(biome);
			});
		}));
		TMoN.SKY_PLUM_GROOVE_BIOME = register("sky_plum_groove", TMoNBiome.create(builder -> {
			builder.configureSurfaceBuilder(TMoN.SKY_SURFACE_BUILDER, TMoN.SKYGRASS_SURFACE_CONFIG);
			builder.depth(0F);
			setDefaultSettings(builder);
			builder.parent("sky_forest");

			builder.apply(biome -> {
				SkyFeatures.addSkyOres(biome);

				SkyFeatures.addSkyPlumGrooveVegetation(biome);
				SkyFeatures.addLakes(biome);
			});
		}));
		TMoN.SKY_OAK_WOODLANDS_BIOME = register("sky_oak_woodlands", TMoNBiome.create(builder -> {
			builder.configureSurfaceBuilder(TMoN.SKY_SURFACE_BUILDER, TMoN.SKYGRASS_SURFACE_CONFIG);
			builder.depth(0F);
			setDefaultSettings(builder);
			builder.parent("sky_forest");

			builder.apply(biome -> {
				SkyFeatures.addSkyOres(biome);

				SkyFeatures.addWoodlandsVegetation(biome);
				SkyFeatures.addLakes(biome);
			});
		}));
		TMoN.LAKES_ISLES_BIOME = register("lakes_islands", TMoNBiome.create(builder -> {
			builder.configureSurfaceBuilder(TMoN.SKY_SURFACE_BUILDER, TMoN.SKYGRASS_SURFACE_CONFIG);
			builder.depth(0F);
			setDefaultSettings(builder);
			builder.parent("sky_forest");

			builder.apply(biome -> {
				SkyFeatures.addSkyOres(biome);

				SkyFeatures.addRockyIslesVegetation(biome);
				SkyFeatures.addLakesIslandsLakes(biome);
			});
		}));

		TMoN.SKY_BIOME_SOURCE_TYPE = register("sky", new BiomeSourceType<>(SkyBiomeSource::new, SkyBiomeSourceConfig::new));
	}

	protected void setDefaultSettings(BiomeBuilder builder) {
		builder.precipitation(Biome.Precipitation.RAIN);
		builder.category(Biome.Category.FOREST);
		builder.scale(0F);
		builder.temperature(0.7F);
		builder.downfall(0.8F);
		builder.waterColor(0x3D57D6);
		builder.waterFogColor(0x050533);
	}

	protected static Settings createItemSettings() {
		return new Item.Settings().group(TMoN.ITEM_GROUP);
	}

	private static final Block registerWithItem(String name, Block block) {
		register(name, new BlockItem(block, createItemSettings()));
		return register(name, block);
	}

	private static final Block register(String name, Block block) {
		return Registry.register(Registry.BLOCK, new Identifier("tmon", name), block);
	}

	private static final Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier("tmon", name), item);
	}

	private static final <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
		return Registry.register(Registry.BLOCK_ENTITY, new Identifier("tmon", name), type);
	}

	private static final DimensionType register(String name, DimensionType type) {
		return Registry.register(Registry.DIMENSION, new Identifier("tmon", name), type);
	}

	private static final ChunkGeneratorType<?, ?> register(String name, ChunkGeneratorType<?, ?> type) {
		return Registry.register(Registry.CHUNK_GENERATOR_TYPE, new Identifier("tmon", name), type);
	}

	private static final <T extends FeatureConfig> Feature<T> register(String name, Feature<T> feature) {
		return Registry.register(Registry.FEATURE, new Identifier("tmon", name), feature);
	}

	private static final <T extends DecoratorConfig> Decorator<T> register(String name, Decorator<T> feature) {
		return Registry.register(Registry.DECORATOR, new Identifier("tmon", name), feature);
	}

	private static final Biome register(String name, Biome biome) {
		return Registry.register(Registry.BIOME, new Identifier("tmon", name), biome);
	}

	private static final BiomeSourceType<?, ?> register(String name, BiomeSourceType<?, ?> type) {
		return Registry.register(Registry.BIOME_SOURCE_TYPE, new Identifier("tmon", name), type);
	}

	private static final <T extends SurfaceConfig> SurfaceBuilder<T> register(String name, SurfaceBuilder<T> builder) {
		return Registry.register(Registry.SURFACE_BUILDER, new Identifier("tmon", name), builder);
	}
}
