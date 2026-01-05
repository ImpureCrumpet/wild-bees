package tk.estecka.selfcarehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import net.minecraft.entity.passive.BeeEntity;

/**
 * Tracks whether a bee originated from a bee nest (vs beehive) for extended anger duration.
 * This is set when bees are released from nests via WildBeeUtil.
 */
@Mixin(BeeEntity.class)
public class BeeEntityMixin
{
	@Unique
	private boolean selfcarehive$fromNest = false;

	@Shadow
	public void setAngerTime(int ticks) {}
	
	@Shadow
	public int getAngerTime() { return 0; }

	/**
	 * Marks this bee as originating from a bee nest.
	 * Called by WildBeeUtil when releasing bees from nests.
	 */
	public void selfcarehive$markFromNest() {
		this.selfcarehive$fromNest = true;
	}

	/**
	 * Checks if this bee originated from a bee nest.
	 */
	public boolean selfcarehive$isFromNest() {
		return this.selfcarehive$fromNest;
	}
}

