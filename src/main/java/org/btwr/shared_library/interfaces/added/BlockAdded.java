package org.btwr.shared_library.interfaces.added;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public interface BlockAdded
{

    /**
     * Called when a plant hits a full growth stage, like wheat fully grown,
     * or each full block of Hemp.  Used to clear fertilizer.
     */
    default void btwr$notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {
        throw new UnsupportedOperationException();
    }

    /**
     * This is used by old style non-daily plant growth
     */
    default float btwr$getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getIsFertilizedForPlantGrowth(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * The growth level of weeds growing out of this block.  Range of 0 to 7
     */
    default int btwr$getWeedsGrowthLevel(WorldAccess blockAccess, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default void btwr$removeWeeds(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * returns true if block is attached to a block in a particular direction.  Example: pumpkins attached to stems
     */
    default boolean btwr$isBlockAttachedToFacing(WorldAccess blockAccess, BlockPos pos, Direction direction) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$canBeGrazedOn(WorldAccess worldAccess, BlockPos pos, AnimalEntity byAnimal) {
        throw new UnsupportedOperationException();
    }

    default void btwr$onGrazed(World world, BlockPos pos, AnimalEntity animal) {
        throw new UnsupportedOperationException();
    }

    default void btwr$onVegetationAboveGrazed(World world, BlockPos pos, AnimalEntity animal) {
        throw new UnsupportedOperationException();
    }

    default void btwr$notifyNeighborsBlockDisrupted(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default void btwr$onNeighborDisrupted(World world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

}