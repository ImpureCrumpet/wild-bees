package tk.estecka.selfcarehive.mixin;

import net.minecraft.client.render.entity.BeeEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.BeeEntityRenderState;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.estecka.selfcarehive.SelfCareHive;

/**
 * Client-side mixin to adjust bee shadow radius based on hardcoded bee size.
 * Smaller bees should have smaller shadows.
 */
@Mixin(BeeEntityRenderer.class)
public abstract class BeeDimensionsRendererMixin extends EntityRenderer<BeeEntity, BeeEntityRenderState> {
    @Shadow
    protected float shadowRadius;

    protected BeeDimensionsRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
    private void adjustShadowRadius(EntityRendererFactory.Context context, CallbackInfo ci) {
        // Use hardcoded size modifier for client-side consistency
        this.shadowRadius *= SelfCareHive.BEE_SIZE_DEFAULT;
    }
}

