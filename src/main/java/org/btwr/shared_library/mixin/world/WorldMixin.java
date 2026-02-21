package org.btwr.shared_library.mixin.world;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.btwr.shared_library.api.world.interfaces.added.WorldAdded;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAdded {


    @Override // client only override
    public boolean btwr$doesBlockHaveSolidTopSurface(BlockPos pos) {
        World self = (World)(Object)this;

        Block block = self.getBlockState(pos).getBlock();

        return block != null && block.btwr$hasLargeCenterHardPointToFacing(self, pos, Direction.UP);
    }

}