package org.btwr.shared_library.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

/**
 * A registry for blocking items from being combined or repaired in vanilla repair mechanics.
 * <p>
 * This includes the crafting grid repair recipe ({@link BlockType#CRAFTING}),
 * grindstone combining ({@link BlockType#GRINDSTONE}), and anvil combining ({@link BlockType#ANVIL}).
 * Mods can register items or item classes to prevent them from being used in any or all of these
 * repair/combine operations.
 * <p>
 * Registration should be done during mod initialization (e.g. {@code onInitialize()}).
 *
 * <p>Example usage:
 * <pre>{@code
 * // Block a specific item from all repair types
 * RepairRecipeBlockedRegistry.registerItem(ModItems.KNITTING, BlockType.ALL);
 *
 * // Block all items of a class from only anvil and grindstone
 * RepairRecipeBlockedRegistry.registerClass(ProgressiveCraftingItem.class, BlockType.ANVIL, BlockType.GRINDSTONE);
 * }</pre>
 */
public class RepairRecipeBlockedRegistry {

    /**
     * Represents the type of repair/combine operation to block.
     */
    public enum BlockType {
        /** The crafting grid item combination recipe. */
        CRAFTING,
        /** The grindstone item combining operation. */
        GRINDSTONE,
        /** The anvil item combining operation. */
        ANVIL;

        /** Convenience constant for blocking all repair types. */
        public static final BlockType[] ALL = values();
    }

    private static final Map<Item, Set<BlockType>> BLOCKED_ITEMS = new HashMap<>();
    private static final Map<Class<? extends Item>, Set<BlockType>> BLOCKED_CLASSES = new HashMap<>();

    /**
     * Registers a specific item instance to be blocked from the given repair/combine operations.
     *
     * @param item  the item to block
     * @param types the {@link BlockType}s to block; use {@link BlockType#ALL} to block all types
     */
    public static void registerItem(Item item, BlockType... types) {
        BLOCKED_ITEMS.computeIfAbsent(item, k -> new HashSet<>()).addAll(Arrays.asList(types));
    }

    /**
     * Registers an item class to be blocked from the given repair/combine operations.
     * Any item that is an instance of the given class will be blocked.
     *
     * @param clazz the item class to block
     * @param types the {@link BlockType}s to block; use {@link BlockType#ALL} to block all types
     */
    public static void registerClass(Class<? extends Item> clazz, BlockType... types) {
        BLOCKED_CLASSES.computeIfAbsent(clazz, k -> new HashSet<>()).addAll(Arrays.asList(types));
    }

    /**
     * Returns whether the given {@link ItemStack} is blocked from the specified repair/combine operation.
     * A stack is considered blocked if its item has been registered via {@link #registerItem(Item, BlockType...)}
     * or is an instance of a class registered via {@link #registerClass(Class, BlockType...)},
     * and the registration includes the given {@link BlockType}.
     *
     * @param stack the stack to check
     * @param type  the {@link BlockType} to check against
     * @return {@code true} if the stack is blocked for the given type, {@code false} otherwise
     */
    public static boolean isBlocked(ItemStack stack, BlockType type) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();

        if (BLOCKED_ITEMS.getOrDefault(item, Set.of()).contains(type)) return true;
        return BLOCKED_CLASSES.entrySet().stream()
                .anyMatch(e -> e.getKey().isInstance(item) && e.getValue().contains(type));
    }
}