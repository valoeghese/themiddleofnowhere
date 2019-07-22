package tmon.biomes.source.layers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.SystemUtil;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.layer.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.LayerRandomnessSource;
import tmon.TMoN;

public enum AssignSkyBiomesLayer implements IdentitySamplingLayer {
	INSTANCE;

	private static final int VOID = Registry.BIOME.getRawId(TMoN.SKY_VOID_BIOME);
	private static final List<Integer> LANDS = SystemUtil.consume(new ArrayList<>(), list -> {
		for (int i = 0; i < 5; i++) {
			list.add(Registry.BIOME.getRawId(TMoN.SKY_FOREST_BIOME));
		}
		list.add(Registry.BIOME.getRawId(TMoN.ROCKY_ISLES_BIOME));
	});

	public int sample(LayerRandomnessSource random, int value) {
		switch (value) {
		case 1:
			return LANDS.get(random.nextInt(LANDS.size()));
		case 0:
		default:
			return VOID;
		}
	}
}
