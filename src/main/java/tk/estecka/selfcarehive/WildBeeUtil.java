package tk.estecka.selfcarehive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class WildBeeUtil
{
	/**
	 * Checks if there is a lit campfire within range of the given position.
	 * Delegates to vanilla's smoke detection logic which handles both regular
	 * and soul campfires.
	 */
	static public boolean isSmoked(World world, BlockPos pos) {
		return CampfireBlock.isLitCampfireInRange(world, pos);
	}

	/**
	 * Checks if a block is a bee nest (not a beehive).
	 */
	static public boolean isBeeNest(BlockState state) {
		return state.isOf(Blocks.BEE_NEST);
	}

	/**
	 * Releases all bees from a beehive block entity using angerBees, then modifies their anger duration.
	 * The actual release happens via angerBees, and we intercept the bees in BeehiveEntityMixin.
	 */
	static public void releaseBeesFromNest(BeehiveBlockEntity hive, ServerWorld world, BlockPos pos, java.util.UUID targetUuid, net.minecraft.entity.Entity entity) {
		if (hive == null || world.isClient())
			return;

		// Use vanilla angerBees to release bees - we'll intercept them in BeehiveEntityMixin
		net.minecraft.entity.player.PlayerEntity player = world.getPlayers().stream()
			.filter(p -> p.getUuid().equals(targetUuid))
			.findFirst()
			.orElse(null);
		if (player != null) {
			BlockState state = world.getBlockState(pos);
			hive.angerBees(player, state, BeehiveBlockEntity.BeeState.EMERGENCY);
		}
	}

	/**
	 * Scans for nearby bee nests and releases their bees if escalation is enabled.
	 * Only escalates if smoke is not present.
	 */
	static public void escalateNearbyNests(World world, BlockPos origin, java.util.UUID targetUuid, boolean smokePresent) {
		if (world.isClient() || smokePresent)
			return;

		ServerWorld serverWorld = (ServerWorld)world;
		GameRules rules = serverWorld.getServer().getGameRules();
		int radius = rules.getInt(SelfCareHive.ESCALATION_RADIUS);

		// Scan in radius around origin
		for (int x = -radius; x <= radius; ++x) {
			for (int y = -radius; y <= radius; ++y) {
				for (int z = -radius; z <= radius; ++z) {
					BlockPos checkPos = origin.add(x, y, z);
					
					// Skip origin position
					if (checkPos.equals(origin))
						continue;

					BlockState state = world.getBlockState(checkPos);
					if (isBeeNest(state) && world.getBlockEntity(checkPos) instanceof BeehiveBlockEntity nest) {
						// Find the player entity for escalation
						net.minecraft.entity.Entity playerEntity = serverWorld.getPlayers().stream()
							.filter(p -> p.getUuid().equals(targetUuid))
							.findFirst()
							.orElse(null);
						if (playerEntity != null) {
							releaseBeesFromNest(nest, serverWorld, checkPos, targetUuid, playerEntity);
						}
					}
				}
			}
		}
	}
}

