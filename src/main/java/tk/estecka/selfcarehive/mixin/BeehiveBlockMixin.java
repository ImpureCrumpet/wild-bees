package tk.estecka.selfcarehive.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.estecka.selfcarehive.WildBeeUtil;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin
{
	@WrapOperation(
		method = "takeHoney(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/entity/BeehiveBlockEntity$BeeState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/entity/BeehiveBlockEntity;angerBees(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BeehiveBlockEntity$BeeState;)V"
		)
	)
	private void onAngerBeesCalled(
		BeehiveBlockEntity hive,
		PlayerEntity player,
		BlockState blockState,
		BeehiveBlockEntity.BeeState beeState,
		Operation<Void> original,
		World world,
		BlockState stateArg,
		BlockPos pos,
		PlayerEntity playerArg,
		BeehiveBlockEntity.BeeState beeStateArg
	) {
		boolean isNest = WildBeeUtil.isBeeNest(blockState);

		original.call(hive, player, blockState, beeState);

		if (isNest && !world.isClient()) {
			boolean smokePresent = WildBeeUtil.isSmoked(world, pos);
			if (!smokePresent && player != null) {
				WildBeeUtil.escalateNearbyNests(world, pos, player.getUuid(), false);
			}
		}
	}
}
