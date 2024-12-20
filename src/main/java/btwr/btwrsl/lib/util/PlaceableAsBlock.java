package btwr.btwrsl.lib.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class PlaceableAsBlock {
    private static final PlaceableAsBlock instance = new PlaceableAsBlock();

    // Registry to map item to block to place
    private final Map<Item, Block> itemToPlaceableBlock = new HashMap<>();

    private PlaceableAsBlock() {}

    public static PlaceableAsBlock getInstance() {
        return instance;
    }

    // Register a combination of item and block to place
    public void registerPlaceable(Item item, Block blockToPlace) {
        itemToPlaceableBlock.put(item, blockToPlace);
    }

    /**
    // Method to place the block when the item is used
    public void placeAsBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack heldStack = context.getStack();

        // Check if the item has a registered block to place
        Block blockToPlace = itemToPlaceableBlock.get(heldStack.getItem());

        if (blockToPlace != null) {
            // Ensure the block is placed on the server side
            if (!world.isClient) {
                BlockPos placePos = pos.up(); // Position to place the new block

                // Get the block state of the block you're trying to place the item on
                BlockState blockBelowState = world.getBlockState(pos);

                // Check if the block below can support a block on top of it
                if (!blockBelowState.isSolidBlock(world, pos)) {
                    return;
                }

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(context.getPlayer(), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState blockState = blockToPlace.getPlacementState(placementContext);

                // Check if the target position is air or a replaceable block
                if ((world.isAir(placePos) || world.getBlockState(placePos).canReplace(placementContext)) && blockState != null) {
                    // Replace the block at the target position with the new block
                    world.setBlockState(placePos, blockState);
                    heldStack.decrement(1);

                    // Play the block place sound
                    world.playSound(null, placePos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
    }
     **/

    // Method to place the block when the item is used
    public ActionResult placeAsBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack heldStack = context.getStack();

        // Check if the item has a registered block to place
        Block blockToPlace = itemToPlaceableBlock.get(heldStack.getItem());

        if (blockToPlace != null) {
            // Ensure the block is placed on the server side
            if (!world.isClient) {
                BlockPos placePos = pos.up(); // Position to place the new block

                // Get the block state of the block you're trying to place the item on
                BlockState blockBelowState = world.getBlockState(pos);

                // Check if the block below can support a block on top of it
                if (!blockBelowState.isSolidBlock(world, pos) || isLeavesBlock(blockBelowState)) {
                    return ActionResult.FAIL;
                }

                // Create an ItemPlacementContext for the new block position
                ItemPlacementContext placementContext = new ItemPlacementContext(context.getPlayer(), context.getHand(), heldStack, context.getHitResult());

                // Get the block state using the placement context
                BlockState blockState = blockToPlace.getPlacementState(placementContext);

                // Check if the target position is air or a replaceable block
                if ((world.isAir(placePos) || world.getBlockState(placePos).canReplace(placementContext)) && blockState != null) {
                    // Replace the block at the target position with the new block
                    world.setBlockState(placePos, blockState);
                    heldStack.decrement(1);

                    // Play the block place sound
                    world.playSound(null, placePos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    private boolean isLeavesBlock(BlockState state) {
      return state.isIn(BlockTags.LEAVES) || state.getBlock() instanceof LeavesBlock;
    }

}
