package tk.estecka.selfcarehive;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WildBeeUtil
{
	static public boolean isSmoked(World world, BlockPos pos) {
		return CampfireBlock.isLitCampfireInRange(world, pos);
	}

	static public boolean isBeeNest(BlockState state) {
		return state.isOf(Blocks.BEE_NEST);
	}

	static public void releaseBeesFromNest(BeehiveBlockEntity hive, ServerWorld world, BlockPos pos, java.util.UUID targetUuid) {
		if (hive == null || world.isClient())
			return;

		net.minecraft.entity.player.PlayerEntity player = world.getPlayers().stream()
			.filter(p -> p.getUuid().equals(targetUuid))
			.findFirst()
			.orElse(null);
		if (player != null) {
			BlockState state = world.getBlockState(pos);
			hive.angerBees(player, state, BeehiveBlockEntity.BeeState.EMERGENCY);
		}
	}

	static public void escalateNearbyNests(World world, BlockPos origin, java.util.UUID targetUuid, boolean smokePresent) {
		if (world.isClient() || smokePresent)
			return;

		ServerWorld serverWorld = (ServerWorld)world;
		int radius = serverWorld.getGameRules().getValue(SelfCareHive.ESCALATION_RADIUS);

		for (int x = -radius; x <= radius; ++x) {
			for (int y = -radius; y <= radius; ++y) {
				for (int z = -radius; z <= radius; ++z) {
					BlockPos checkPos = origin.add(x, y, z);
					
					if (checkPos.equals(origin))
						continue;

					BlockState state = world.getBlockState(checkPos);
					if (isBeeNest(state) && world.getBlockEntity(checkPos) instanceof BeehiveBlockEntity nest) {
						releaseBeesFromNest(nest, serverWorld, checkPos, targetUuid);
					}
				}
			}
		}
	}
}
