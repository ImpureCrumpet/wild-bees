package tk.estecka.selfcarehive;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;
import net.minecraft.world.rule.GameRuleCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelfCareHive
implements ModInitializer
{
	static public final Logger LOGGER = LoggerFactory.getLogger("selfcare-hive");

	static public final GameRuleCategory CATEGORY = GameRuleCategory.register(
		Identifier.of("selfcarehive", "gamerules")
	);

	static public final GameRule<Boolean> CAN_HEAL = GameRuleBuilder
		.forBoolean(true).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "healing"));
	static public final GameRule<Integer> HEALING_COST = GameRuleBuilder
		.forInteger(1).range(0, Integer.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "healing.cost"));
	static public final GameRule<Double> HEALING_AMOUNT = GameRuleBuilder
		.forDouble(2.0).range(0.0, Double.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "healing.potency"));

	static public final GameRule<Boolean> CAN_BREED = GameRuleBuilder
		.forBoolean(true).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "breeding"));
	static public final GameRule<Integer> BREEDING_COST = GameRuleBuilder
		.forInteger(5).range(0, Integer.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "breeding.cost"));
	static public final GameRule<Integer> TRACKING_DURATION = GameRuleBuilder
		.forInteger(12_000).range(0, Integer.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "tracking.duration"));

	static public final GameRule<Integer> ESCALATION_RADIUS = GameRuleBuilder
		.forInteger(8).range(0, Integer.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "wildbees.escalation_radius"));
	static public final GameRule<Integer> NEST_ANGER_MIN = GameRuleBuilder
		.forInteger(1200).range(0, Integer.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "wildbees.nest_anger_min"));
	static public final GameRule<Integer> NEST_ANGER_MAX = GameRuleBuilder
		.forInteger(2400).range(0, Integer.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "wildbees.nest_anger_max"));
	static public final GameRule<Double> MIN_STING_HEALTH = GameRuleBuilder
		.forDouble(4.0).range(0.0, Double.MAX_VALUE).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "wildbees.min_sting_health"));
	static public final GameRule<Boolean> NON_LETHAL_STINGS = GameRuleBuilder
		.forBoolean(true).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "wildbees.non_lethal_stings"));

	static public final float BEE_SIZE_DEFAULT = 0.65f;
	static public final int BEEHIVE_CAPACITY_DEFAULT = 21;

	static public final GameRule<Double> BEE_SIZE_MODIFIER = GameRuleBuilder
		.forDouble(0.65).range(0.01, 5.0).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "beedimensions.modifier"));
	static public final GameRule<Integer> BEEHIVE_CAPACITY = GameRuleBuilder
		.forInteger(21).range(1, 50).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "beedimensions.hive_capacity"));
	static public final GameRule<Boolean> PREVENT_SUFFOCATION = GameRuleBuilder
		.forBoolean(true).category(CATEGORY)
		.buildAndRegister(Identifier.of("selfcarehive", "beedimensions.prevent_suffocation"));

	@Override
	public void onInitialize() {
		// static init
	}
}
