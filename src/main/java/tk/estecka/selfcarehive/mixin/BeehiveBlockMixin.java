package tk.estecka.selfcarehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.estecka.selfcarehive.WildBeeUtil;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin
{
	/**
	 * Intercepts honey harvesting to implement wild bee mechanics for bee nests.
	 * For bee nests, we mark the context and let vanilla release bees, then escalate.
	 */
	@WrapOperation(
		method = "onUseWithItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/entity/BeehiveBlockEntity;angerBees(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/entity/BeehiveBlockEntity$BeeState;Lnet/minecraft/entity/Entity;)V"
		)
	)
	private void onAngerBeesCalled(
		BeehiveBlockEntity hive,
		PlayerEntity player,
		BeehiveBlockEntity.BeeState state,
		net.minecraft.entity.Entity entity,
		Operation<Void> original,
		ItemUsageContext context
	) {
		// Extract needed values from ItemUsageContext
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState blockState = world.getBlockState(pos);
		
		// Mark if this is a nest for later processing
		boolean isNest = WildBeeUtil.isBeeNest(blockState);

		// Call vanilla behavior (bees will be intercepted in BeehiveEntityMixin)
		original.call(hive, player, state, entity);

		// After vanilla releases bees, escalate if this is a nest without smoke
		if (isNest && !world.isClient()) {
			boolean smokePresent = WildBeeUtil.isSmoked(world, pos);
			if (!smokePresent && player != null) {
				WildBeeUtil.escalateNearbyNests(world, pos, player.getUuid(), false);
			}
		}
	}
}

