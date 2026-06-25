package tk.estecka.selfcarehive.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tk.estecka.selfcarehive.SelfCareHive;

@Mixin(BeeEntityRenderer.class)
public class BeeDimensionsRendererMixin {
	@ModifyReturnValue(method = "getShadowRadius", at = @At("RETURN"))
	private float selfcarehive$scaleShadowRadius(float original) {
		return original * SelfCareHive.BEE_SIZE_DEFAULT;
	}
}
