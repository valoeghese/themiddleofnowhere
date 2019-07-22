package tmon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

@Mixin(DimensionType.class)
public class MixinDimensionType {
	@Inject(at = @At("HEAD"), method = "byRawId", cancellable = true)
	private static void byRawId(int id, CallbackInfoReturnable<DimensionType> info) {
		Registry.DIMENSION.forEach(dimension -> {
			if (dimension.getRawId() == id) {
				info.setReturnValue(dimension);
			}
		});
	}
}
