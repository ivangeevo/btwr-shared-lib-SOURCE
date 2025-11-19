package org.btwr.shared_library.mixin.block;

import org.btwr.shared_library.interfaces.added.BlockAdded;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static net.minecraft.block.FarmlandBlock.MOISTURE;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin implements BlockAdded {

    @Override
    public boolean btwr$isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {
        return world.getBlockState(pos).get(MOISTURE) == 7;
    }

}
