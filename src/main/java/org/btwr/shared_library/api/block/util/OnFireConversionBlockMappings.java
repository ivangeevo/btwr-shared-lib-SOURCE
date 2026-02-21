package org.btwr.shared_library.api.block.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Single global {@code Block -> Block} mapping for simple on-fire conversions.
 * <p>
 * This implementation covers the common case where, when a block is consumed
 * by fire, you just want it to turn into another block (while preserving
 * compatible block state properties where possible).
 * <p>
 * Examples:
 * <ul>
 *     <li>All vanilla logs -> a single "smouldering log" block.</li>
 *     <li>Planks -> charred planks.</li>
 *     <li>Custom blocks -> ash or slag variants.</li>
 * </ul>
 *
 * <p>
 * Usage:
 * <ul>
 *     <li>The library calls {@link #register()} during initialization to hook
 *         this mapping into {@link OnFireConversionResolver.Registry}.</li>
 *     <li>Mods call {@link #addMapping(Block, Block)} to contribute their
 *         own conversion pairs.</li>
 * </ul>
 */
public final class OnFireConversionBlockMappings implements OnFireConversionResolver {

    private static final OnFireConversionBlockMappings INSTANCE = new OnFireConversionBlockMappings();

    private final Map<Block, Block> mappings = new IdentityHashMap<>();

    private OnFireConversionBlockMappings() {
        // no-op
    }

    /**
     * Registers this mapping implementation as the global
     * {@link OnFireConversionResolver}.
     * <p>
     * Should be called once by the shared library during initialization.
     */
    public static void register() {
        OnFireConversionResolver.Registry.register(INSTANCE);
    }

    /**
     * Public API for mods: add a simple {@code Block -> Block} conversion.
     * <p>
     * When {@code original} is consumed by fire, it will be replaced with
     * {@code converted}, with compatible block state properties copied from
     * the original state where possible.
     *
     * @param original  the block that is being destroyed by fire
     * @param converted the block that should replace it after burning
     */
    public static void addMapping(Block original, Block converted) {
        INSTANCE.mappings.put(original, converted);
    }

    @Override
    public BlockState getSmoulderingState(
            World world,
            BlockPos pos,
            BlockState preBurnState
    ) {
        Block converted = mappings.get(preBurnState.getBlock());
        if (converted == null) {
            return null;
        }
        // Copy over any compatible properties (e.g. axis, waterlogged) from the original.
        return converted.getStateWithProperties(preBurnState);
    }
}