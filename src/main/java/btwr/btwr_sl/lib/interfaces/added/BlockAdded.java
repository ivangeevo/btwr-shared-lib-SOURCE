package btwr.btwr_sl.lib.interfaces.added;

import net.minecraft.block.Block;
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
    default void notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {}

    /**
     * This is used by old style non-daily plant growth
     */
    default float getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) { return 0; }

    default boolean isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) { return false; }

    /**
     * The growth level of weeds growing out of this block.  Range of 0 to 7
     */
    default int getWeedsGrowthLevel(WorldAccess blockAccess, BlockPos pos) { return 0; }

    default void removeWeeds(World world, BlockPos pos) {}

    /**
     * returns true if block is attached to a block in a particular direction.  Example: pumpkins attached to stems
     */
    default boolean isBlockAttachedToFacing(WorldAccess blockAccess, BlockPos pos, Direction direction) {
        return false;
    }

}
