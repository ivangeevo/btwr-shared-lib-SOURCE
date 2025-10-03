package btwr.btwr_sl.lib.util;

// Create a context flag
public class CraftingContext {
    private static final ThreadLocal<Boolean> IN_CRAFTING = ThreadLocal.withInitial(() -> false);

    public static void setCrafting(boolean value) {
        IN_CRAFTING.set(value);
    }

    public static boolean isInCrafting() {
        return IN_CRAFTING.get();
    }
}
