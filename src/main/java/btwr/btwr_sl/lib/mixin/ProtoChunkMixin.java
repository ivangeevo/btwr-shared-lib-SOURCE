package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.lib.util.BlockReplacementRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Modifies world generation by replacing specific blocks based on the BlockReplacementRegistry.
 * Redirects block placement in ProtoChunk to apply replacements dynamically.
 */
@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin {

    @Redirect(method = "setBlockState", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/ChunkSection;setBlockState(IIILnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockState;")
    )
    private BlockState replaceBlockState(ChunkSection chunkSection, int x, int y, int z, BlockState state) {

        Block original = state.getBlock();
        Block replaced = BlockReplacementRegistry.getReplacementFor(original);

        if (original != replaced) {
            state = replaced.getDefaultState();
        }

        return chunkSection.setBlockState(x, y, z, state);
    }
}
