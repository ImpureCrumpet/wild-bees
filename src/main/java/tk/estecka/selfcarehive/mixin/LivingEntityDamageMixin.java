package tk.estecka.selfcarehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.world.ServerWorld;
import tk.estecka.selfcarehive.SelfCareHive;

/**
 * Prevents wild bees (from nests) from dying when stinging by clamping their health to minimum threshold.
 * Bees from nests will survive self-inflicted stinging damage but be reduced to 2 health (or configured minimum).
 * This allows wild bees to sting multiple times during the anger period, creating sustained swarm pressure.
 * Protection only applies during the anger period and does not protect against external damage (player attacks, etc.).
 */
@Mixin(LivingEntity.class)
public class LivingEntityDamageMixin
{
	@Inject(
		method = "damage",
		at = @At("RETURN")
	)
	private void clampWildBeeHealthAfterDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity)(Object)this;
		
		// Only apply to bees that originated from nests
		if (!(self instanceof BeeEntity bee))
			return;

		// Only apply if non-lethal stings are enabled
		if (!(self.getWorld() instanceof ServerWorld serverWorld)) {
			return;
		}

		boolean nonLethalEnabled = serverWorld.getServer().getGameRules().getBoolean(SelfCareHive.NON_LETHAL_STINGS);
		if (!nonLethalEnabled)
			return;

		// Check if this bee originated from a nest
		BeeEntityMixin beeMixin = (BeeEntityMixin)(Object)bee;
		if (!beeMixin.selfcarehive$isFromNest())
			return;

		// Only protect during anger period (escalation time)
		if (beeMixin.getAngerTime() <= 0)
			return;

		// Only protect against self-inflicted damage from stinging
		// When a bee stings, it takes damage itself. External damage (player attacks, etc.)
		// will have an attacker that is NOT the bee itself.
		net.minecraft.entity.Entity attacker = source.getAttacker();
		
		// If there's an external attacker (player, mob, etc.), allow normal death
		if (attacker != null && attacker != bee) {
			return;
		}
		
		// If attacker is null or the bee itself, and bee is angry, it's likely stinging damage
		// Protect against this damage

		// Get minimum health threshold
		double minHealth = serverWorld.getServer().getGameRules().get(SelfCareHive.MIN_STING_HEALTH).get();
		float currentHealth = self.getHealth();

		// If bee would have died or is below threshold, set health to minimum
		if (currentHealth <= 0.0f || currentHealth < minHealth) {
			self.setHealth((float)minHealth);
		}
	}
}

