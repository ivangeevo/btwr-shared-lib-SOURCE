package org.btwr.shared_library.api.block.interfaces.added;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.btwr.shared_library.api.block.util.Flammability;
import org.btwr.shared_library.api.crafting.FurnaceBurnTime;

public interface BlockAdded {
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

    default void btwr$attachToFacing(World world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

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

    default int btwr$getFurnaceBurnTime(int itemDamage) {
        throw new UnsupportedOperationException();
    }

    default void btwr$setFurnaceBurnTime(int burnTime) {
        throw new UnsupportedOperationException();
    }

    default void btwr$setFurnaceBurnTime(FurnaceBurnTime burnTime) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$doesInfiniteBurnToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$doesExtinguishFireAbove(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default void btwr$onDestroyedByFire(World world, BlockPos pos, int fireAge, boolean forcedFireSpread) {
        throw new UnsupportedOperationException();
    }

    default Block btwr$setFireProperties(int chanceToEncourageFire, int abilityToCatchFire) {
        throw new UnsupportedOperationException();
    }

    default Block btwr$setFireProperties(Flammability flammability) {
        throw new UnsupportedOperationException();
    }

    /**
     * Whether the block itself can be set on fire, rather than a neighboring block being set to a fire block
     */
    default boolean btwr$getCanBeSetOnFireDirectly(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    /**
     *  This a general check that returns whether the item can set this block on fire.
     *  **/
    default boolean btwr$getCanBeSetOnFireDirectlyByItem(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$setOnFireDirectly(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default int btwr$getChanceOfFireSpreadingDirectlyTo(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getCanBlockLightItemOnFire(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getDoesFireDamageToEntities(World world, BlockPos pos, Entity entity) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getDoesFireDamageToEntities(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * Used by Hibachi to determine if it can remove the block above it when lit
     */
    default boolean btwr$getCanBlockBeIncinerated(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    /**
     * Whether a fire block can be directly placed over this one, without first burning or catching fire, as if it were air.
     */
    default boolean btwr$getCanBlockBeReplacedByFire(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$isIncineratedInCrucible() {
        throw new UnsupportedOperationException();
    }

    //------------- Ground cover related functionality ------------//

    default boolean btwr$canGroundCoverRestOnBlock(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default float btwr$groundCoverRestingOnVisualOffset(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$isGroundCover() {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getCanGrassSpreadToBlock(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$spreadGrassToBlock(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getCanGrassGrowUnderBlock(World world, BlockPos pos, boolean grassOnHalfSlab) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getCanMyceliumSpreadToBlock(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$spreadMyceliumToBlock(World world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

    default boolean btwr$getCanBlightSpreadToBlock(World world, BlockPos pos, int blightLevel) {
        throw new UnsupportedOperationException();
    }

    /**
     * Used by blocks like grass and mycellium to determine if they should use a snow side
     * texture.  Note that this refers to the top visible surface, not just the top facing,
     * which means that stuff like half-slabs should only return true if they have ground cover
     * actually on the top surface halfway up the block vertically.
     */
    default boolean btwr$isSnowCoveringTopSurface(WorldAccess world, BlockPos pos) {
        throw new UnsupportedOperationException();
    }

}