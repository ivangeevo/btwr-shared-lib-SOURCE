package org.btwr.shared_library.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.btwr.shared_library.api.block.util.FireBlockUtils;
import org.btwr.shared_library.api.block.util.OnFireConversionResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {

    @Unique
    private static final ThreadLocal<BlockState> sturdyTrees$PRE_FIRE_STATE = new ThreadLocal<>();

    @Inject(method = "trySpreadingFire", at = @At("HEAD"))
    private void sturdyTrees$capturePreFireState(
            World world,
            BlockPos pos,
            int spreadFactor,
            Random random,
            int currentAge,
            CallbackInfo ci
    ) {
        if (world.isClient) {
            sturdyTrees$PRE_FIRE_STATE.remove();
            return;
        }
        sturdyTrees$PRE_FIRE_STATE.set(world.getBlockState(pos));
    }

    @Inject(method = "trySpreadingFire", at = @At("TAIL"))
    private void sturdyTrees$applySmoulderingIfNeeded(
            World world,
            BlockPos pos,
            int spreadFactor,
            Random random,
            int currentAge,
            CallbackInfo ci
    ) {
        if (world.isClient) {
            sturdyTrees$PRE_FIRE_STATE.remove();
            return;
        }

        BlockState preState = sturdyTrees$PRE_FIRE_STATE.get();
        sturdyTrees$PRE_FIRE_STATE.remove();

        if (preState == null) {
            return;
        }

        BlockState currentState = world.getBlockState(pos);
        Block currentBlock = currentState.getBlock();

        // Only override if vanilla just made this AIR or FIRE.
        if (currentBlock != Blocks.AIR && currentBlock != Blocks.FIRE) {
            return;
        }

        BlockState newState = OnFireConversionResolver.Registry.resolve(world, pos, preState);
        if (newState == null) {
            return; // no registered mapping, leave vanilla result
        }

        world.setBlockState(pos, newState);
    }

}