package org.btwr.shared_library.registry;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles block replacement mappings for world generation and other modifications.
 * Mods can register block replacements dynamically.
 */
public class BlockReplacementRegistry
{
    private record ReplacementInfo(Block block, boolean copyProperties) {}
    private static final Map<Block, ReplacementInfo> REPLACEMENTS = new HashMap<>();

    /**
     * Registers a block replacement pair
     * <p>The replacement block will have it's default blockstate properties.
     * @param original The original block to be replaced.
     * @param replacement The block to replace it with.
     */
    public static void registerReplacement(Block original, Block replacement) {
        registerReplacement(original, replacement, false);
    }

    /**
     * Registers a block replacement pair.
     * @param original The original block to be replaced.
     * @param shouldCopy Whether blockstate properties of the {@param original} block should be copied to the replacement
     * @param replacement The block to replace it with.
     */
    public static void registerReplacement(Block original, Block replacement, boolean shouldCopy) {
        REPLACEMENTS.put(original, new ReplacementInfo(replacement, shouldCopy));
    }

    private static ReplacementInfo getReplacementInfo(Block original) {
        return REPLACEMENTS.getOrDefault(original, new ReplacementInfo(original, false));
    }

    /**
     * Retrieves the replacement block for a given block, or the original if no replacement exists.
     * @param original The original block.
     * @return The replacement block or the original block if no replacement exists.
     */
    public static Block getReplacementFor(Block original) {
        return getReplacementInfo(original).block();
    }

    public static boolean shouldCopyProperties(Block original) {
        return getReplacementInfo(original).copyProperties();
    }
}