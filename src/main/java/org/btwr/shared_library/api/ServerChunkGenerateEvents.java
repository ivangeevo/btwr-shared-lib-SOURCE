package org.btwr.shared_library.api;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

public class ServerChunkGenerateEvents {

    public static void createChunkReplaceEventGlobally(Block original, Block replaced) {
        // World height in 1.21.1: (-64 – 319)
        createChunkReplaceEventWithYRange(original, replaced, -64, 319);
    }

    public static void createChunkReplaceEventWithYRange(Block original, Block replaced, int minY, int maxY) {
        ServerChunkEvents.CHUNK_GENERATE.register((ServerWorld world, WorldChunk chunk) -> {
            BlockPos.Mutable pos = new BlockPos.Mutable();
            for (int y = minY; y <= maxY; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        pos.set(x + chunk.getPos().getStartX(), y, z + chunk.getPos().getStartZ());
                        if (chunk.getBlockState(pos).isOf(original)) {
                            chunk.setBlockState(pos, replaced.getDefaultState(), false);
                        }
                    }
                }
            }
        });
    }
}
