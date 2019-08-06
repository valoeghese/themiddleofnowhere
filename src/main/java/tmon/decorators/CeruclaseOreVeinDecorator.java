package tmon.decorators;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;

public class CeruclaseOreVeinDecorator extends Decorator<NopeDecoratorConfig> {
	public CeruclaseOreVeinDecorator(Function<Dynamic<?>, ? extends NopeDecoratorConfig> deserialize) {
		super(deserialize);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld world,
			ChunkGenerator<? extends ChunkGeneratorConfig> generator,
			Random random,
			NopeDecoratorConfig config,
			BlockPos pos) {
		int gridX = pos.getX() / 16 / 8;
		int gridZ = pos.getZ() / 16 / 8;

		Random gridRandom = new Random(((world.getSeed() * 31) + gridX * 31) + gridZ * 31);
		int veinX = (gridX * 8 + 1 + gridRandom.nextInt(7));
		int veinZ = (gridZ * 8 + 1 + gridRandom.nextInt(7));

		int d = Math.abs(pos.getX() / 16 - veinX) + Math.abs(pos.getZ() / 16 + veinZ);

		int count = Math.max(0, (2 - d) * 5);

		if (count == 0) {
			return Stream.empty();
		}
		
		return IntStream.range(0, count).mapToObj(i -> {
			int dx = random.nextInt(16);
			int dy = random.nextInt(72 - 20) + 20;
			int dz = random.nextInt(16);
			return pos.add(dx, dy, dz);
		});
	}

}
