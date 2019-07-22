package tmon.dimension.generator;

import java.util.Random;

import net.minecraft.util.SystemUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class SkyChunkGenerator extends SurfaceChunkGenerator<SkyChunkGeneratorConfig> {
	private static final float[] BIOME_WEIGHT_TABLE = SystemUtil.consume(new float[25], values -> {
		for (int dx = -2; dx <= 2; ++dx) {
			for (int dz = -2; dz <= 2; ++dz) {
				float weight = 10.0F / MathHelper.sqrt(dx * dx + dz * dz + 0.2F);
				values[dx + 2 + (dz + 2) * 5] = weight;
			}
		}
	});

	public SkyChunkGenerator(IWorld world, BiomeSource biomeSource, SkyChunkGeneratorConfig config) {
		super(world, biomeSource, 4, 8, 128, config, false);
	}

	@Override
	protected double[] computeNoiseRange(int x, int z) {
		float scaleTotal = 0.0F;
		float depthTotal = 0.0F;
		float weightTotal = 0.0F;
		float centerDepth = this.biomeSource.getBiomeForNoiseGen(x, z).getDepth();

		for (int dx = -2; dx <= 2; ++dx) {
			for (int dz = -2; dz <= 2; ++dz) {
				Biome biome = this.biomeSource.getBiomeForNoiseGen(x + dx, z + dz);
				float depth = biome.getDepth();
				float scale = biome.getScale();

				float weight = BIOME_WEIGHT_TABLE[dx + 2 + (dz + 2) * 5];
				if (biome.getDepth() > centerDepth) {
					weight /= 2.0F;
				}

				scaleTotal += scale * weight;
				depthTotal += depth * weight;
				weightTotal += weight;
			}
		}

		scaleTotal /= weightTotal;
		depthTotal /= weightTotal;
		return new double[] { depthTotal, scaleTotal };
	}

	@Override
	protected double computeNoiseFalloff(double depth, double scale, int y) {
		// negative depth will make more void, positive will make more landmass
		double value = 12 - 15 * depth;
		
		return value;
	}

	@Override
	protected void sampleNoiseColumn(double[] values, int x, int z) {
		this.sampleNoiseColumn(values, x, z, 684.412D, 684.412D * 4, 684.412D / 80, 684.412D / 160, 3, -10);
	}

	@Override
	public int getSpawnHeight() {
		return 50;
	}

	@Override
	public int getSeaLevel() {
		return 0;
	}

	@Override
	protected void buildBedrock(Chunk chunk, Random random) {

	}

	@Override
	protected double method_16409() {
		return (this.getNoiseSizeY() - 4) / 2;
	}

	@Override
	protected double method_16410() {
		return 8.0D;
	}
}
 