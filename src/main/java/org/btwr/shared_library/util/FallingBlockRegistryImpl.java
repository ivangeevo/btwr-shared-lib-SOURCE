package org.btwr.shared_library.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;

public class FallingBlockRegistryImpl {

    private static final FallingBlockRegistryImpl instance = new FallingBlockRegistryImpl();

    public static FallingBlockRegistryImpl getInstance() {
        return instance;
    }

    private FallingBlockRegistryImpl() {}

    public void onOnBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        Block block = world.getBlockState(pos).getBlock();

        if (isCustomFallingBlockInDimension(world, state)) {
            world.scheduleBlockTick(pos, block, this.getFallDelay());
        }
    }

    public void onRandomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isCustomFallingBlockInDimension(world, state)) {
            if (random.nextInt(16) == 0 && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
                ParticleUtil.spawnParticle(world, pos, random, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state));
            }
        }
    }

    public void onScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isCustomFallingBlockInDimension(world, state)) {
            if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) {
                return;
            }

            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
            this.configureFallingBlockEntity(fallingBlockEntity);
        }
    }

    // uncomment the dimension check when we figure out the bug later on that caused a crash earlier when testing this.
    private static boolean isCustomFallingBlockInDimension(World world, BlockState state) {
        return state.isIn(BTWRConventionalTags.Blocks.TURNED_TO_FALLING_BLOCKS) /**&& canFallInCurrentDimension(world, state)**/;
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {}

    public int getFallDelay() {
        return 2;
    }

    /**
     * Determines if the block can fall in the current dimension based on tags.
     *
     * @param world the current world
     * @param state the block state
     * @return true if the block can fall in the current dimension, false otherwise
     */
    public static boolean canFallInCurrentDimension(World world, BlockState state) {
        RegistryEntry<DimensionType> dimensionType = world.getDimensionEntry();
        if (dimensionType.matchesId(DimensionTypes.OVERWORLD_ID)) {
            return state.isIn(BTWRConventionalTags.Blocks.CAN_FALL_IN_OVERWORLD);
        }
        else if (dimensionType.matchesId(DimensionTypes.THE_NETHER_ID)) {
            return state.isIn(BTWRConventionalTags.Blocks.CAN_FALL_IN_THE_NETHER);
        }
        else if (dimensionType.matchesId(DimensionTypes.THE_END_ID)) {
            return state.isIn(BTWRConventionalTags.Blocks.CAN_FALL_IN_THE_END);
        }
        return false;
    }

}