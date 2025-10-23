package btwr.btwr_sl.lib.mixin.block;

import btwr.btwr_sl.lib.interfaces.added.BlockAdded;
import btwr.btwr_sl.lib.block.StackDroppingManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockAdded {

    // modified logic for dropStacks so that it also includes directional dropping conditions
    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"), cancellable = true)
    private static void onDropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci)
    {
        StackDroppingManager.getInstance().onDropStacks(state, world, pos, blockEntity, entity, tool);
        ci.cancel();
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {}

    /**
     * This is used by old style non-daily plant growth
     */
    @Override
    public float getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) { return 1F; }

    @Override
    public boolean isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {return false;}

    @Override
    public int getWeedsGrowthLevel(WorldAccess blockAccess, BlockPos pos)
    {
        return 0;
    }

    @Override
    public void removeWeeds(World world, BlockPos pos) {}

    @Override
    public boolean canBeGrazedOn(WorldAccess worldAccess, BlockPos pos, AnimalEntity byAnimal) {
        return false;
    }

    @Override
    public void onGrazed(World world, BlockPos pos, AnimalEntity animal) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());

        Block blockBelow = world.getBlockState(pos.down()).getBlock();

        if (blockBelow != null) {
            blockBelow.onVegetationAboveGrazed(world, pos.down(), animal);
        }
    }

    @Override
    public void onVegetationAboveGrazed(World world, BlockPos pos, AnimalEntity animal) {}

    @Override
    public void notifyNeighborsBlockDisrupted(World world, BlockPos pos) {
        BlockPos tempPos = new BlockPos(pos);

        for (int facingId = 0; facingId <= 5; facingId++) {
            Direction facing = Direction.byId(facingId);

            tempPos.offset(facing);

            Block tempBlock = world.getBlockState(tempPos).getBlock();

            if (tempBlock != null) {
                tempBlock.onNeighborDisrupted(world, tempPos, facing.getOpposite());
            }
        }
    }

    @Override
    public void onNeighborDisrupted(World world, BlockPos pos, Direction facing) {}

    @Override
    public boolean isBlockAttachedToFacing(WorldAccess blockAccess, BlockPos pos, Direction direction) {
        return false;
    }

    @Override
    public boolean getIsFertilizedForPlantGrowth(World world, BlockPos pos) {
        return false;
    }
}
