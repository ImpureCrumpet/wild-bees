package tk.estecka.selfcarehive.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tk.estecka.selfcarehive.SelfCareHive;

@Mixin(EntityRenderer.class)
public class BeeDimensionsRendererMixin {
	@ModifyReturnValue(method = "getShadowRadius", at = @At("RETURN"))
	private float selfcarehive$scaleShadowRadius(float original) {
		if ((Object) this instanceof BeeEntityRenderer) {
			return original * SelfCareHive.BEE_SIZE_DEFAULT;
		}
		return original;
	}
}
