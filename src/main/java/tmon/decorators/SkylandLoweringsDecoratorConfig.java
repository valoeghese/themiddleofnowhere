package tmon.decorators;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.decorator.DecoratorConfig;

public class SkylandLoweringsDecoratorConfig implements DecoratorConfig {
	public final int count;
	public final float extraChance;
	public final int extraCount;
	public int maxHeight = 50;

	public SkylandLoweringsDecoratorConfig(int count, float extraChance, int extraCount) {
		this.count = count;
		this.extraChance = extraChance;
		this.extraCount = extraCount;
	}

	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(
				ImmutableMap.of(ops.createString("count"), ops.createInt(this.count),
						ops.createString("extra_chance"), ops.createFloat(this.extraChance),
						ops.createString("extra_count"), ops.createInt(this.extraCount))));
	}

	public static SkylandLoweringsDecoratorConfig deserialize(Dynamic<?> dynamic) {
		int count = dynamic.get("count").asInt(0);
		float extraChance = dynamic.get("extra_chance").asFloat(0.0F);
		int extraCount = dynamic.get("extra_count").asInt(0);
		return new SkylandLoweringsDecoratorConfig(count, extraChance, extraCount);
	}
}
