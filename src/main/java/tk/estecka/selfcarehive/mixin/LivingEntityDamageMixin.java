package tk.estecka.selfcarehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.world.ServerWorld;
import tk.estecka.selfcarehive.IBeeFromNest;
import tk.estecka.selfcarehive.SelfCareHive;

@Mixin(LivingEntity.class)
public class LivingEntityDamageMixin
{
	@Inject(
		method = "damage",
		at = @At("RETURN")
	)
	private void clampWildBeeHealthAfterDamage(ServerWorld serverWorld, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity)(Object)this;
		
		if (!(self instanceof BeeEntity bee))
			return;

		var rules = serverWorld.getGameRules();
		if (!rules.getValue(SelfCareHive.NON_LETHAL_STINGS))
			return;

		if (!((IBeeFromNest) bee).selfcarehive$isFromNest())
			return;

		if (!(bee instanceof Angerable angerable) || !angerable.hasAngerTime())
			return;

		net.minecraft.entity.Entity attacker = source.getAttacker();
		if (attacker != null && attacker != bee)
			return;

		double minHealth = rules.getValue(SelfCareHive.MIN_STING_HEALTH);
		float currentHealth = self.getHealth();

		if (currentHealth <= 0.0f || currentHealth < minHealth) {
			self.setHealth((float)minHealth);
		}
	}
}
