package org.btwr.shared_library.mixin.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.btwr.shared_library.api.block.util.FireBlockUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {

    @Shadow protected abstract int getSpreadChance(BlockState state);

    @Unique
    private static final ThreadLocal<BlockState> sturdyTrees$PRE_FIRE_STATE = new ThreadLocal<>();

    @Inject(method = "trySpreadingFire", at = @At("HEAD"), cancellable = true)
    private void onTrySpreadingFire(
            World world,
            BlockPos pos,
            int spreadFactor,
            Random random,
            int currentAge,
            CallbackInfo ci
    ) {
        Block block = world.getBlockState(pos).getBlock();

        if (block.btwr$hasCustomFireDestructionBehavior()) {
            int i = this.getSpreadChance(world.getBlockState(pos));

            if (random.nextInt(spreadFactor) < i) {
                FireBlockUtils.onBlockDestroyedByFire(world, pos, currentAge, false);
            }
            ci.cancel();
        }
    }

}