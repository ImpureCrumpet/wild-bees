package tk.estecka.selfcarehive;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.rule.GameRules;

public class BeehiveUtil
{
	static public BlockState	SetHoneyLevel(int honey, World world, BlockState hiveState, BlockPos hivePos){
		hiveState = hiveState.with(BeehiveBlock.HONEY_LEVEL, honey);
		world.setBlockState(hivePos, hiveState);
		return hiveState;
	}

	static private GameRules rulesOf(World world) {
		return ((ServerWorld) world).getGameRules();
	}

	static public BlockState TryHeal(BeeEntity bee, World world, BlockState hiveState, BlockPos hivePos){
		GameRules rules = rulesOf(world);
		boolean canHeal = rules.getValue(SelfCareHive.CAN_HEAL);
		int cost = rules.getValue(SelfCareHive.HEALING_COST);
		float potency = rules.getValue(SelfCareHive.HEALING_AMOUNT).floatValue();
		
		int honey = BeehiveBlockEntity.getHoneyLevel(hiveState);
		boolean isHurt = bee.getHealth() < bee.getMaxHealth();
		boolean willOverheal = (bee.getHealth() + potency) >= bee.getMaxHealth();
		boolean willOverflow = bee.hasNectar() && honey >= BeehiveBlock.FULL_HONEY_LEVEL;

		if (canHeal && isHurt && honey>=cost && (willOverflow || !willOverheal)){
			bee.heal(potency);
			return SetHoneyLevel(honey-cost, world, hiveState, hivePos);
		}
		else
			return hiveState;
	}

	static public Pair<@Nullable BeeEntity, BlockState>	TryCreateBaby(BeeEntity parent, IBeeColonyTracker colony, ServerWorld world, BlockState hiveState, BlockPos hivePos){
		GameRules rules = world.getGameRules();
		boolean canBreed = rules.getValue(SelfCareHive.CAN_BREED);
		int cost = rules.getValue(SelfCareHive.BREEDING_COST);

		int honey = BeehiveBlockEntity.getHoneyLevel(hiveState);
		
		if (canBreed
		&&  honey >= cost
		&&  parent.getBreedingAge() == 0
		&&  !colony.selfcarehive$isColonyFull()
		){
			BeeEntity baby = parent.createChild(world, parent);
			baby.setBaby(true);
			baby.setPosition(parent.getEntityPos());
			parent.resetLoveTicks();
			parent.setBreedingAge(6000);
			hiveState = SetHoneyLevel(honey-cost, world, hiveState, hivePos);
			colony.selfcarehive$RememberBee(baby.getUuid());
			return Pair.of(baby, hiveState);
		}
		else
			return Pair.of(null, hiveState);
	}
}
