package org.btwr.shared_library.api.world.interfaces.added;

import net.minecraft.util.math.BlockPos;

public interface WorldAdded {

    default boolean btwr$doesBlockHaveSolidTopSurface(BlockPos pos) {
        throw new UnsupportedOperationException();
    }

}
