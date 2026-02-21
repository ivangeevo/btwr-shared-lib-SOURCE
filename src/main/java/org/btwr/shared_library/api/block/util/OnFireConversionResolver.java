package org.btwr.shared_library.api.block.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Library-wide API for "on fire conversion" logic.
 * <p>
 * Implementations decide what block state (if any) should replace a block
 * after vanilla fire logic has finished at a given position.
 * <p>
 * Typical use cases:
 * <ul>
 *     <li>Turning burnt logs into a smouldering log variant.</li>
 *     <li>Converting blocks into ash, slag, or other remains after burning.</li>
 *     <li>Any other custom transformation that should occur when a block
 *         is consumed by fire.</li>
 * </ul>
 */
public interface OnFireConversionResolver {

    /**
     * Computes the block state that should be placed at {@code pos} after
     * vanilla fire handling, based on the block state that existed there
     * immediately before fire acted.
     * <p>
     * If this method returns {@code null}, the library will leave the
     * vanilla result as-is (typically AIR or FIRE).
     *
     * @param world         the world in which the fire logic is running
     * @param pos           the position being processed
     * @param preBurnState  the block state that was present at {@code pos}
     *                      before fire spread/destruction occurred
     * @return the replacement {@link BlockState} to place at {@code pos},
     *         or {@code null} to perform no custom conversion
     */
    BlockState getSmoulderingState(
            World world,
            BlockPos pos,
            BlockState preBurnState
    );

    /**
     * Simple registry-style access for the active {@link OnFireConversionResolver}.
     * <p>
     * The mixin code calls into this registry to obtain the current resolver
     * implementation. Library code or mods should register a single resolver
     * during initialization.
     */
    final class Registry {
        private static OnFireConversionResolver resolver = null;

        /**
         * Registers the global {@link OnFireConversionResolver} used by the
         * fire mixin.
         * <p>
         * Call this during initialization. If multiple mods need to cooperate,
         * they should compose or wrap their resolvers and register the
         * combined instance here.
         *
         * @param newResolver the resolver to use globally
         */
        public static void register(OnFireConversionResolver newResolver) {
            resolver = newResolver;
        }

        /**
         * Resolves the on-fire conversion for a given position and pre-burn
         * block state using the currently registered resolver.
         *
         * @param world         the world in which the fire logic is running
         * @param pos           the position being processed
         * @param preBurnState  the block state that existed at {@code pos}
         *                      before fire acted
         * @return the replacement {@link BlockState}, or {@code null} if
         *         no resolver is registered or the resolver chose not to
         *         override the vanilla result
         */
        public static BlockState resolve(
                World world,
                BlockPos pos,
                BlockState preBurnState
        ) {
            if (resolver == null) {
                return null;
            }
            return resolver.getSmoulderingState(world, pos, preBurnState);
        }
    }
}