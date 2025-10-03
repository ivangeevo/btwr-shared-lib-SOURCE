package btwr.btwr_sl.lib.util;

import net.minecraft.item.ItemStack;

public class RecipeRemainderHandler {
    // Thread-local per crafting slot
    public static final ThreadLocal<ItemStack> REMAINDER_STACK = new ThreadLocal<>();

    private RecipeRemainderHandler() {}
}
