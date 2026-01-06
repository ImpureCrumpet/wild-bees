package tk.estecka.selfcarehive.mixin;

import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tk.estecka.selfcarehive.SelfCareHive;

/**
 * Prevents bees from taking suffocation damage.
 * This fixes a bug where smaller bees can get stuck and disappear.
 * 
 * Note: Visual scaling is handled by BeeScaleMixin (getScale override).
 */
@Mixin(BeeEntity.class)
public abstract class BeeDimensionsMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void preventSuffocationDamage(net.minecraft.entity.damage.DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        BeeEntity bee = (BeeEntity)(Object)this;
        World world = bee.getWorld();
        if (world != null && !world.isClient()) {
            boolean preventSuffocation = world.getServer().getGameRules().getBoolean(SelfCareHive.PREVENT_SUFFOCATION);
            if (preventSuffocation) {
                // Check if damage source is suffocation (in wall)
                net.minecraft.entity.damage.DamageSource inWallSource = world.getDamageSources().inWall();
                if (damageSource == inWallSource) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}

