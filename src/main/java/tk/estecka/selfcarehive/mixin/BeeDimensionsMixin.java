package tk.estecka.selfcarehive.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tk.estecka.selfcarehive.SelfCareHive;

@Mixin(BeeEntity.class)
public abstract class BeeDimensionsMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void preventSuffocationDamage(ServerWorld serverWorld, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!serverWorld.getGameRules().getValue(SelfCareHive.PREVENT_SUFFOCATION))
			return;

		if (damageSource == serverWorld.getDamageSources().inWall()) {
			cir.setReturnValue(false);
		}
    }
}
