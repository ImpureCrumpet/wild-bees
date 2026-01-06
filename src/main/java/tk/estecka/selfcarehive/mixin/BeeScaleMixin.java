package tk.estecka.selfcarehive.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tk.estecka.selfcarehive.SelfCareHive;

/**
 * Overrides the scale for bees to make them visually smaller.
 * This affects both server (hitbox) and client (rendering).
 * Uses hardcoded value for client/server consistency.
 */
@Mixin(LivingEntity.class)
public class BeeScaleMixin {
    @Inject(method = "getScale", at = @At("HEAD"), cancellable = true)
    private void modifyBeeScale(CallbackInfoReturnable<Float> cir) {
        if ((LivingEntity)(Object)this instanceof BeeEntity) {
            // Use hardcoded size for client/server consistency
            cir.setReturnValue(SelfCareHive.BEE_SIZE_DEFAULT);
        }
    }
}

