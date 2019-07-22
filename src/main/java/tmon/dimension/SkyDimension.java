package tmon.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSourceType;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import tmon.TMoN;
import tmon.biomes.source.SkyBiomeSource;
import tmon.biomes.source.SkyBiomeSourceConfig;
import tmon.dimension.generator.SkyChunkGenerator;
import tmon.dimension.generator.SkyChunkGeneratorConfig;

public class SkyDimension extends Dimension {
	public SkyDimension(World world, DimensionType type) {
		super(world, type);
	}

	@Override
	protected void initializeLightLevelToBrightness() {
		// copied from nether dimension
		for (int level = 0; level <= 15; ++level) {
			float brightness = 1.0F - level / 15.0F;
			this.lightLevelToBrightness[level] = (1.0F - brightness) / (brightness * 3.0F + 1.0F) * 0.9F + 0.1F;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChunkGenerator<?> createChunkGenerator() {
		SkyChunkGeneratorConfig chunkGenConfig = new SkyChunkGeneratorConfig();

		BiomeSourceType<SkyBiomeSourceConfig, SkyBiomeSource> biomeSourceType = (BiomeSourceType<SkyBiomeSourceConfig, SkyBiomeSource>) TMoN.SKY_BIOME_SOURCE_TYPE;

		SkyBiomeSourceConfig biomeSourceConfig = new SkyBiomeSourceConfig()
				.setLevelProperties(world.getLevelProperties())
				.setGeneratorSettings(chunkGenConfig);

		SkyBiomeSource biomeSource = biomeSourceType.applyConfig(biomeSourceConfig);

		ChunkGeneratorType<SkyChunkGeneratorConfig, SkyChunkGenerator> type = (ChunkGeneratorType<SkyChunkGeneratorConfig, SkyChunkGenerator>) TMoN.SKY_CHUNK_GENERATOR;

		return type.create(world, biomeSource, chunkGenConfig);
	}

	@Override
	public BlockPos getSpawningBlockInChunk(ChunkPos var1, boolean var2) {
		return null;
	}

	@Override
	public BlockPos getTopSpawningBlockPosition(int var1, int var2, boolean var3) {
		return null;
	}

	public float getSkyAngle(long long_1, float float_1) {
		double double_1 = MathHelper.fractionalPart((double) long_1 / 24000.0D - 0.25D);
		double double_2 = 0.5D - Math.cos(double_1 * 3.141592653589793D) / 2.0D;
		return (float) (double_1 * 2.0D + double_2) / 3.0F;
	}

	@Override
	public boolean hasVisibleSky() {
		return true;
	}

	@Override
	public DimensionType getType() {
		return TMoN.SKY_DIMENSION;
	}

	@Override
	public BlockPos getForcedSpawnPoint() {
		return new BlockPos(0, 100, 0);
	}

	@Environment(EnvType.CLIENT)
	public Vec3d getFogColor(float x, float y) {
		float float_3 = MathHelper.cos(x * 6.2831855F) * 2.0F + 0.5F;
		float_3 = MathHelper.clamp(float_3, 0.0F, 1.0F);
		float float_4 = 0.7529412F;
		float float_5 = 0.84705883F;
		float float_6 = 1.0F;
		float_4 *= float_3 * 0.94F + 0.06F;
		float_5 *= float_3 * 0.94F + 0.06F;
		float_6 *= float_3 * 0.91F + 0.09F;
		return new Vec3d((double) float_4, (double) float_5, (double) float_6);
	}

	@Override
	public boolean canPlayersSleep() {
		return true;
	}

	@Override
	public boolean shouldRenderFog(int var1, int var2) {
		return false;
	}

	@Override
	public float getCloudHeight() {
		return 8;
	}

	@Override
	public double getHorizonShadingRatio() {
		return 1;
	}

}
