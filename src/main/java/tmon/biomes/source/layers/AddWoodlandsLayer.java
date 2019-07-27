package tmon.biomes.source.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.CrossSamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tmon.TMoN;

public enum AddWoodlandsLayer implements CrossSamplingLayer {
	INSTANCE;

	public static final int FOREST = Registry.BIOME.getRawId(TMoN.SKY_FOREST_BIOME);
	public static final int OAK_WOODLANDS = Registry.BIOME.getRawId(TMoN.SKY_OAK_WOODLANDS_BIOME);

	@Override
	public int sample(LayerRandomnessSource random, int center, int biome1, int biome2, int biome3, int biome4) {
		if (center == FOREST && biome1 == FOREST && biome2 == FOREST && biome3 == FOREST && biome4 == FOREST) {
			return random.nextInt(8) == 0 ? OAK_WOODLANDS : center;
		}
		return center;
	}

}
