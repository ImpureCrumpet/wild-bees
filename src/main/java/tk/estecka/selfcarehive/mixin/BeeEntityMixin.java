package tk.estecka.selfcarehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import net.minecraft.entity.passive.BeeEntity;
import tk.estecka.selfcarehive.IBeeFromNest;

@Mixin(BeeEntity.class)
public class BeeEntityMixin implements IBeeFromNest
{
	@Unique
	private boolean selfcarehive$fromNest = false;

	public void selfcarehive$markFromNest() {
		this.selfcarehive$fromNest = true;
	}

	public boolean selfcarehive$isFromNest() {
		return this.selfcarehive$fromNest;
	}
}
