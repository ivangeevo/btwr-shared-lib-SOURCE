package btwr.btwr_sl.lib.block;

import btwr.btwr_sl.lib.util.utils.ItemUtils;
import btwr.btwr_sl.lib.util.utils.VectorUtils;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static btwr.btwr_sl.tag.BTWRConventionalTags.Blocks.MODDED_CONVERTING_BLOCKS;
import static btwr.btwr_sl.tag.BTWRConventionalTags.Blocks.VANILLA_CONVERTING_BLOCKS;

public class StackDroppingManager {
    private static final StackDroppingManager instance = new StackDroppingManager();

    // Directional dropping mod IDs
    private static final String TOUGH_ENVIRONMENT_ID = "tough_environment";
    private static final String STURDY_TREES_ID = "sturdy_trees";

    // Private constructor to prevent instantiation
    private StackDroppingManager() {}

    public static StackDroppingManager getInstance()
    {
        return instance;
    }

    public void onDropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool)
    {
        if (world instanceof ServerWorld) {
            // the opposite direction
            Direction lookDirection = VectorUtils.getMiningDirection(entity, world, pos);

            if (isDroppingInDirectionBlock(state) && !isFullyBreakingTool(tool) && isModHavingDirectionalDropBlocks()) {
                ItemUtils.ejectStackFromBlockTowardsFacing(world, entity, pos, state, blockEntity, tool, lookDirection.getOpposite());
            } else {
                Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, entity, tool).forEach(stack -> Block.dropStack(world, pos, stack));
                state.onStacksDropped((ServerWorld)world, pos, tool, true);
            }

        }
    }

    private boolean isFullyBreakingTool(ItemStack tool) {
        return tool.isOf(Items.STONE_AXE)
                || tool.isIn(BTWRConventionalTags.Items.MODERN_PICKAXES)
                || tool.isIn(BTWRConventionalTags.Items.MODERN_AXES)

                || tool.isIn(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                || tool.isIn(BTWRConventionalTags.Items.ADVANCED_AXES);
    }

    private boolean isDroppingInDirectionBlock(BlockState state) {
        if (state.getBlock() instanceof InfestedBlock) {
            return false;
        }

        return (state.isIn(VANILLA_CONVERTING_BLOCKS) || state.isIn(MODDED_CONVERTING_BLOCKS));
    }

    private boolean isModHavingDirectionalDropBlocks() {
        return FabricLoader.getInstance().isModLoaded(TOUGH_ENVIRONMENT_ID)
                || FabricLoader.getInstance().isModLoaded(STURDY_TREES_ID)
                ;
    }

}
