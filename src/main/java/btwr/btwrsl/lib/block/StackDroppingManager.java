package btwr.btwrsl.lib.block;

import btwr.btwrsl.lib.util.utils.ItemUtils;
import btwr.btwrsl.lib.util.utils.VectorUtils;
import btwr.btwrsl.tag.BTWRConventionalTags;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static btwr.btwrsl.tag.BTWRConventionalTags.Blocks.MODDED_CONVERTING_BLOCKS;
import static btwr.btwrsl.tag.BTWRConventionalTags.Blocks.VANILLA_CONVERTING_BLOCKS;

public class StackDroppingManager {
    private static final StackDroppingManager instance = new StackDroppingManager();

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

            if (isDroppingInDirectionBlock(state) && !isFullyBreakingTool(tool)) {
                ItemUtils.ejectStackFromBlockTowardsFacing(world, (PlayerEntity) entity, pos, state, blockEntity, tool, lookDirection.getOpposite());
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

}
