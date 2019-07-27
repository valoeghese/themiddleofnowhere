package tmon.features.tree;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class SkyBushFeature extends SkylandsTreeFeature {
	protected final BlockState leaves;
	protected final BlockState log;

	public SkyBushFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> deserialize, BlockState log, BlockState leaves) {
		super(deserialize, false);
		this.log = log;
		this.leaves = leaves;
	}

	@Override
	public boolean generate(Set<BlockPos> set, ModifiableTestableWorld world, Random random, BlockPos pos, MutableIntBoundingBox bbox) {
		pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos).down();
		if (isNaturalDirtOrGrass(world, pos)) {
			pos = pos.up();
			this.setBlockState(set, world, pos, this.log, bbox);

			for (int y = pos.getY(); y <= pos.getY() + 2; ++y) {
				int dy = y - pos.getY();
				int size = 2 - dy;

				for (int x = pos.getX() - size; x <= pos.getX() + size; ++x) {
					int dx = x - pos.getX();

					for (int z = pos.getZ() - size; z <= pos.getZ() + size; ++z) {
						int dz = z - pos.getZ();

						if (Math.abs(dx) != size || Math.abs(dz) != size || random.nextInt(2) != 0) {
							BlockPos pos2 = new BlockPos(x, y, z);
							if (isAirOrLeaves(world, pos2)) {
								this.setBlockState(set, world, pos2, this.leaves, bbox);
							}
						}
					}
				}
			}
		}

		return true;
	}
}
