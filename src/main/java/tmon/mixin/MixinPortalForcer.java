package tmon.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PortalForcer;
import tmon.TMoN;
import tmon.dimension.TMoNDimensionUtils;

@Mixin(PortalForcer.class)
public class MixinPortalForcer {
	@Shadow
	@Final
	private ServerWorld world;

	@Inject(method = "usePortal", at = @At("HEAD"), cancellable = true)
	public void usePortal(Entity entity, float float_1, CallbackInfoReturnable<Boolean> infoReturnable) {
		// If going to the void world
		if (world.getDimension().getType() == TMoN.SKY_DIMENSION) {
			TMoNDimensionUtils.teleportToSky(entity, (ServerWorld) entity.getEntityWorld(), world);
			infoReturnable.setReturnValue(true);
			infoReturnable.cancel();
		}

		// Coming from the void world
		if (entity.getEntityWorld().getDimension().getType() == TMoN.SKY_DIMENSION) {
			TMoNDimensionUtils.teleportFromSky(entity, (ServerWorld) entity.getEntityWorld(), world);
			infoReturnable.setReturnValue(true);
			infoReturnable.cancel();
		}
	}

}