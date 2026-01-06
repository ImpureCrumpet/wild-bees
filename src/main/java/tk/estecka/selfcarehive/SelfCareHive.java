package tk.estecka.selfcarehive;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.GameRules.IntRule;
import net.minecraft.world.GameRules.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createIntRule;
import static net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createBooleanRule;
import static net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createDoubleRule;

public class SelfCareHive
implements ModInitializer
{
	static public final Logger LOGGER = LoggerFactory.getLogger("selfcare-hive");

	static public final CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(
		Identifier.of("selfcare-hive", "gamerules"),
		Text.translatable("selfcarehive.gamerules").formatted(Formatting.BOLD, Formatting.YELLOW)
	);
	
	static public final Key<BooleanRule> CAN_HEAL      = GameRuleRegistry.register("selfcarehive.healing",         CATEGORY, createBooleanRule(true));
	static public final Key<IntRule> HEALING_COST      = GameRuleRegistry.register("selfcarehive.healing.cost",    CATEGORY, createIntRule(1, 0));
	static public final Key<DoubleRule> HEALING_AMOUNT = GameRuleRegistry.register("selfcarehive.healing.potency", CATEGORY, createDoubleRule(2.0, 0.0));

	static public final Key<BooleanRule> CAN_BREED     = GameRuleRegistry.register("selfcarehive.breeding",          CATEGORY, createBooleanRule(true));
	static public final Key<IntRule> BREEDING_COST     = GameRuleRegistry.register("selfcarehive.breeding.cost",     CATEGORY, createIntRule(5, 0));
	static public final Key<IntRule> TRACKING_DURATION = GameRuleRegistry.register("selfcarehive.tracking.duration", CATEGORY, createIntRule(12_000, 0));

	// Wild Bee Mechanics
	static public final Key<IntRule> ESCALATION_RADIUS  = GameRuleRegistry.register("selfcarehive.wildbees.escalation_radius", CATEGORY, createIntRule(8, 0));
	static public final Key<IntRule> NEST_ANGER_MIN     = GameRuleRegistry.register("selfcarehive.wildbees.nest_anger_min", CATEGORY, createIntRule(1200, 0));
	static public final Key<IntRule> NEST_ANGER_MAX     = GameRuleRegistry.register("selfcarehive.wildbees.nest_anger_max", CATEGORY, createIntRule(2400, 0));
	static public final Key<DoubleRule> MIN_STING_HEALTH = GameRuleRegistry.register("selfcarehive.wildbees.min_sting_health", CATEGORY, createDoubleRule(4.0, 0.0)); // Minimum health for wild bees after stinging (prevents death)
	static public final Key<BooleanRule> NON_LETHAL_STINGS = GameRuleRegistry.register("selfcarehive.wildbees.non_lethal_stings", CATEGORY, createBooleanRule(true)); // Prevents wild bees from dying when stinging

	// Bee Dimensions Mechanics (hardcoded defaults for client/server consistency)
	static public final float BEE_SIZE_DEFAULT = 0.65f; // Hardcoded for client-side rendering
	static public final int BEEHIVE_CAPACITY_DEFAULT = 21; // Hardcoded for client-side rendering
	
	static public final Key<DoubleRule> BEE_SIZE_MODIFIER = GameRuleRegistry.register("selfcarehive.beedimensions.modifier", CATEGORY, createDoubleRule(0.65, 0.01, 5.0)); // Bee size multiplier (0.65 = 65% of vanilla)
	static public final Key<IntRule> BEEHIVE_CAPACITY = GameRuleRegistry.register("selfcarehive.beedimensions.hive_capacity", CATEGORY, createIntRule(21, 1, 50)); // Maximum bees per hive (vanilla = 3)
	static public final Key<BooleanRule> PREVENT_SUFFOCATION = GameRuleRegistry.register("selfcarehive.beedimensions.prevent_suffocation", CATEGORY, createBooleanRule(true)); // Prevent small bees from suffocation damage (fixes disappearing bug)

	@Override
	public void onInitialize() {
		// static init
	}
}