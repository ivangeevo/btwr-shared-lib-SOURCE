package org.btwr.shared_library.api.loot.condition;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Central registry for directional drop conditions.
 *
 * <p>Directional drops are drops whose resulting {@link ItemStack}s
 * are emitted in the direction the block is mined from, rather than using
 * Minecraft's standard drop logic (which spawns items at or near the block
 * center with randomized motion).</p>
 *
 * <p>A directional drop condition defines a pair of predicates:
 * one for the tool {@link ItemStack} used, and one for the {@link BlockState}
 * being broken. If any registered condition matches, directional drops
 * are considered enabled for that block break interaction.</p>
 *
 * <p>This class acts as a static registry and shared API, allowing multiple
 * mods to contribute conditions that opt specific tools and blocks into
 * directional drop behavior.</p>
 */

public final class DirectionalDropConditions {

    // Private constructor to prevent instantiation
    private DirectionalDropConditions() {}

    private static final List<Condition> CONDITIONS = new ArrayList<>();

    /**
     * Registers a new directional drop condition.
     *
     * @param stackCheck predicate applied to the tool {@link ItemStack}
     * @param stateCheck predicate applied to the {@link BlockState}
     */
    public static void register(Predicate<ItemStack> stackCheck, Predicate<BlockState> stateCheck) {
        CONDITIONS.add(new Condition(stackCheck, stateCheck));
    }

    public static boolean test(BlockState state, ItemStack tool) {
        for (Condition condition : CONDITIONS) {
            if (condition.matches(state, tool)) {
                return true;
            }
        }
        return false;
    }

    private record Condition(Predicate<ItemStack> toolPredicate, Predicate<BlockState> statePredicate) {
        public boolean matches(BlockState state, ItemStack tool) {
            return toolPredicate.test(tool) && statePredicate.test(state);
        }
    }
}
