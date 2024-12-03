package btwr.btwrsl.lib.mixin;

import btwr.btwrsl.lib.interfaces.added.BlockAdded;
import btwr.btwrsl.lib.block.StackDroppingManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin implements BlockAdded
{

    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"))
    private static void onDropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfo ci)
    {
        StackDroppingManager.getInstance().onDirectionalDropStacks(state, world, pos, blockEntity, entity, tool, ci);
    }

    // inject the directional drop check before the getDroppedStacks(normal drop) method call and cancel if it does so
    @Inject(method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "HEAD"))
    private static void customDirectionalDropStacks(BlockState state, World world, BlockPos pos, BlockEntity blockEntity,
                                                    Entity entity, ItemStack tool, CallbackInfo ci) {
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
}
