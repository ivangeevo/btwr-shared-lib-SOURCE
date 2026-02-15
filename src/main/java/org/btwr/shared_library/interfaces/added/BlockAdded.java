package org.btwr.shared_library.interfaces.added;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
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
    default int btwr$getWeedsGrowthLevel(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default void btwr$removeWeeds(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    //------------ Hard Point related functionality ----------//
    /**
     * small attachment surfaces, like those required for the bottom of a torch (approx 1/8 block width)
     */
    default boolean btwr$hasSmallCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing, boolean ignoreTransparency)
    {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasSmallCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

    /**
     * medium sized attachment points like the top of fence posts (approx 1/4 block width)
     */
    default boolean btwr$hasCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing, boolean ignoreTransparency)
    {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

    /**
     * large attachment points that can support a full block width
     */
    default boolean btwr$hasLargeCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing, boolean ignoreTransparency)
    {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasLargeCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

    /**
     * returns true if the block is sitting on the one below, like a torch resting on the ground
     */
    default boolean btwr$isBlockRestingOnThatBelow(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * returns true if block is attached to a block in a particular direction.  Example: pumpkins attached to stems
     */
    default boolean btwr$isBlockAttachedToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

    default void btwr$attachToFacing(World world, BlockPos pos, Direction facing) {}

    default boolean btwr$hasContactPointToFullFace(WorldAccess world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasContactPointToSlabSideFace(WorldAccess world, BlockPos pos, Direction facing, boolean isSlabUpsideDown)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * This method refers to the 'L' shaped sides of stair blocks.  Other stair facings will refernce either the full face, stair top,
     * or slab methods, depending on their shape
     */
    default boolean btwr$hasContactPointToStairShapedFace(WorldAccess world, BlockPos pos, Direction facing) {
        return btwr$hasContactPointToFullFace(world, pos, facing);
    }

    /**
     * This method refers to the half-block shaped top or bottom of stair blocks.
     */
    default boolean btwr$hasContactPointToStairNarrowVerticalFace(WorldAccess world, BlockPos pos, Direction facing, int stairFacing)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Should return true if mortar has been successfully applied to block.
     */
    default boolean btwr$onMortarApplied(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasMortar(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasNeighborWithMortarInContact(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$isStickyToSnow(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$hasStickySnowNeighborInContact(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    //------------- Grazing Functionality -------------//


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

    //--------------- Fire related functionality -------------//

    /**
     *  This a general check that returns whether the item can set this block on fire.
     *  **/
    default boolean btwr$getCanBeSetOnFireDirectlyByItem(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getCanBeSetOnFireDirectly(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$setOnFireDirectly(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default int btwr$getChanceOfFireSpreadingDirectlyTo(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default void btwr$checkForFireSpreadFromLocation(World world, BlockPos pos, Random random, int iSourceFireAge) {
        throw new UnsupportedOperationException();
    }
}