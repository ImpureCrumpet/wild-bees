package tk.estecka.selfcarehive.mixin;

import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import tk.estecka.selfcarehive.SelfCareHive;

@Mixin(LivingEntityRenderer.class)
public abstract class BeeModelScaleMixin {
	@Inject(method = "scale", at = @At("TAIL"))
	private void applyBeeScale(LivingEntityRenderState state, MatrixStack matrices) {
		if ((Object) this instanceof BeeEntityRenderer) {
			float scale = SelfCareHive.BEE_SIZE_DEFAULT;
			matrices.scale(scale, scale, scale);
		}
	}
}
