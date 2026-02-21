package org.btwr.shared_library.util.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WorldUtils {

    public static boolean hasNeighborWithMortarInFullFaceContactToFacing(World world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block.btwr$hasMortar(world, pos)) {
            return block.btwr$hasContactPointToFullFace(world, pos, facing.getOpposite());
        }

        return false;
    }

    public static boolean hasStickySnowNeighborInFullFaceContactToFacing(World world, BlockPos pos, Direction facing) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block.btwr$isStickyToSnow(world, pos)) {
            return block.btwr$hasContactPointToFullFace(world, pos, facing.getOpposite());
        }

        return false;
    }

}
