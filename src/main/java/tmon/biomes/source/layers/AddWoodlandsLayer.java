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
	public int sample(LayerRandomnessSource random, int center, int var3, int var4, int var5, int var6) {
		if (center == FOREST && var3 == FOREST && var4 == FOREST && var5 == FOREST && var6 == FOREST) {
			return random.nextInt(8) == 0 ? OAK_WOODLANDS : center;
		}
		return center;
	}

}
