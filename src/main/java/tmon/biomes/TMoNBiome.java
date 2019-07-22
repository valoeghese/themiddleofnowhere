package tmon.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.entity.EntityCategory;
import net.minecraft.world.biome.Biome;

public class TMoNBiome extends Biome {
	protected TMoNBiome(BiomeBuilder settings) {
		super(settings);
		settings.apply.forEach(f -> f.accept(this));
	}

	public static TMoNBiome create(Consumer<BiomeBuilder> f) {
		BiomeBuilder builder = new BiomeBuilder();
		f.accept(builder);
		return new TMoNBiome(builder);
	}

	public static class BiomeBuilder extends Biome.Settings {
		private List<Consumer<? super TMoNBiome>> apply = new ArrayList<>(0);

		public BiomeBuilder apply(Consumer<? super TMoNBiome> f) {
			apply.add(f);
			return this;
		}
	}

	@Override
	public void addSpawn(EntityCategory category, SpawnEntry entry) {
		super.addSpawn(category, entry);
	}	
}
