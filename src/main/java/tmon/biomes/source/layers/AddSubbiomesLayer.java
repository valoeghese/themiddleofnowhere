package tmon.biomes.source.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tmon.TMoN;

public enum AddSubbiomesLayer implements IdentitySamplingLayer {
	INSTANCE;

	private static final int FOREST = Registry.BIOME.getRawId(TMoN.SKY_FOREST_BIOME);
	private static final int PLUM_GROOVE = Registry.BIOME.getRawId(TMoN.SKY_PLUM_GROOVE_BIOME);
	private static final int LAKES_ISLANDS = Registry.BIOME.getRawId(TMoN.LAKES_ISLES_BIOME);

	@Override
	public int sample(LayerRandomnessSource random, int value) {
		if (value == FOREST) {
			if (random.nextInt(8) == 0) {
				return PLUM_GROOVE;
			}
			if (random.nextInt(8) == 0) {
				return LAKES_ISLANDS;
			}
			return value;
		}
		return value;
	}
}
