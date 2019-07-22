package tmon.decorators;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

public class SkylandLoweringsDecorator extends Decorator<SkylandLoweringsDecoratorConfig> {
	public SkylandLoweringsDecorator(Function<Dynamic<?>, ? extends SkylandLoweringsDecoratorConfig> deserialize) {
		super(deserialize);
	}

	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, SkylandLoweringsDecoratorConfig config, BlockPos pos) {
		int count = config.count;
		if (random.nextFloat() < config.extraChance) {
			count += config.extraCount;
		}

		return IntStream.range(0, count)
				.mapToObj(i -> {
					for (int j = 0; j < 5; j++) {
						int dx = random.nextInt(16);
						int dz = random.nextInt(16);
						BlockPos top = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos.add(dx, 0, dz));
						if (top.getY() < config.maxHeight) {
							return top;
						}
					}
					return BlockPos.ORIGIN;
				}).filter(p -> p != BlockPos.ORIGIN);
	}
}
