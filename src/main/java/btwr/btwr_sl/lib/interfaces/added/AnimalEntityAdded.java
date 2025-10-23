package btwr.btwr_sl.lib.interfaces.added;

import net.minecraft.util.math.BlockPos;

public interface AnimalEntityAdded {

    /**
     * Returns null if no valid graze block exists at location
     */
    default BlockPos getGrazeBlockForPos() {
        return null;
    }

    default boolean canGrazeOnBlock(BlockPos pos) {
        return false;
    }
}
