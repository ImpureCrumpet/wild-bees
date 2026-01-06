package tk.estecka.selfcarehive.mixin;

import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.estecka.selfcarehive.SelfCareHive;

/**
 * Client-side mixin to scale the bee model during rendering.
 * This makes bees visually smaller to match their hitbox.
 */
@Mixin(LivingEntityRenderer.class)
public abstract class BeeModelScaleMixin {
    @Inject(method = "scale", at = @At("HEAD"))
    private void applyBeeScale(LivingEntityRenderState state, MatrixStack matrices, CallbackInfo ci) {
        if (((Object)this) instanceof BeeEntityRenderer) {
            float scale = SelfCareHive.BEE_SIZE_DEFAULT;
            matrices.scale(scale, scale, scale);
        }
    }
}

