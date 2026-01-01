package org.btwr.shared_library.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class DirectionalDropConditions {

    private static final List<DirectionalDropCondition> CONDITIONS = new ArrayList<>();

    public static void register(DirectionalDropCondition condition) {
        CONDITIONS.add(condition);
    }

    public static boolean test(BlockState state, ItemStack tool) {
        for (DirectionalDropCondition condition : CONDITIONS) {
            // infested blocks can't directional drop
            if (!(state.getBlock() instanceof InfestedBlock) && condition.matches(state, tool)) {
                return true;
            }
        }
        return false;
    }

    private DirectionalDropConditions() {}
}
