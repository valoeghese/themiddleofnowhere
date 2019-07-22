package tmon.biomes.source.layers;

import net.minecraft.world.biome.layer.InitLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;

public enum SkyInitLayer implements InitLayer {
	INSTANCE;

	public int sample(LayerRandomnessSource random, int x, int z) {
		if (x == 0 && z == 0) {
			return 1;
		}
		return random.nextInt(5) == 0 ? 0 : 1;
	}
}
