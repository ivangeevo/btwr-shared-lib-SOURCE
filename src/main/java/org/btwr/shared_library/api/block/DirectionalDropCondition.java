package org.btwr.shared_library.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public record DirectionalDropCondition(Predicate<ItemStack> toolPredicate, Predicate<BlockState> statePredicate) {
    public boolean matches(BlockState state, ItemStack tool) {
        return toolPredicate.test(tool) && statePredicate.test(state);
    }
}
