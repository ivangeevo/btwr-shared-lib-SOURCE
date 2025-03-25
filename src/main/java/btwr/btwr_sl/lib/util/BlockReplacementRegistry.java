package btwr.btwr_sl.lib.util;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles block replacement mappings for world generation and other modifications.
 * Mods can register block replacements dynamically.
 */
public class BlockReplacementRegistry
{
    private static final Map<Block, Block> REPLACEMENTS = new HashMap<>();

    /**
     * Registers a block replacement pair.
     * @param original The original block to be replaced.
     * @param replacement The block to replace it with.
     */
    public static void registerReplacement(Block original, Block replacement) {
        REPLACEMENTS.put(original, replacement);
    }

    /**
     * Retrieves the replacement block for a given block, or the original if no replacement exists.
     * @param original The original block.
     * @return The replacement block or the original block if no replacement exists.
     */
    public static Block getReplacementFor(Block original) {
        return REPLACEMENTS.getOrDefault(original, original);
    }
}
