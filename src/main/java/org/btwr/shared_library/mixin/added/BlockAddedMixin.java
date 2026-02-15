package org.btwr.shared_library.mixin.added;

import org.btwr.shared_library.interfaces.added.BlockAdded;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.btwr.shared_library.util.utils.WorldUtils;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockAddedMixin implements BlockAdded {

    //------------ Hard Point related functionality ----------//


    @Override
    public boolean btwr$hasSmallCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing, boolean ignoreTransparency) {
        return btwr$hasCenterHardPointToFacing(world, pos, facing, ignoreTransparency);
    }

    @Override
    public boolean btwr$hasSmallCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        return btwr$hasCenterHardPointToFacing(world, pos, facing, false);
    }

    @Override
    public boolean btwr$hasCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing, boolean ignoreTransparency) {
        return btwr$hasLargeCenterHardPointToFacing(world, pos, facing, ignoreTransparency);
    }

    @Override
    public boolean btwr$hasCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        return btwr$hasCenterHardPointToFacing(world, pos, facing, false);
    }

    @Override
    public boolean btwr$hasLargeCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing, boolean ignoreTransparency) {
        return world.getBlockState(pos).isFullCube(world, pos);
    }

    @Override
    public boolean btwr$hasLargeCenterHardPointToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        return btwr$hasLargeCenterHardPointToFacing(world, pos, facing, false);
    }

    @Override
    public boolean btwr$isBlockRestingOnThatBelow(WorldAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean btwr$isBlockAttachedToFacing(WorldAccess world, BlockPos pos, Direction facing) {
        return false;
    }

    @Override
    public void btwr$attachToFacing(World world, BlockPos pos, Direction facing) {}

    @Override
    public boolean btwr$hasContactPointToFullFace(WorldAccess world, BlockPos pos, Direction facing) {
        return world.getBlockState(pos).isFullCube(world, pos);
    }

    @Override
    public boolean btwr$hasContactPointToSlabSideFace(WorldAccess world, BlockPos pos, Direction facing, boolean isSlabUpsideDown) {
        return btwr$hasContactPointToFullFace(world, pos, facing);
    }

    @Override
    public boolean btwr$hasContactPointToStairShapedFace(WorldAccess world, BlockPos pos, Direction facing) {
        return btwr$hasContactPointToFullFace(world, pos, facing);
    }

    @Override
    public boolean btwr$hasContactPointToStairNarrowVerticalFace(WorldAccess world, BlockPos pos, Direction facing, int stairFacing) {
        return btwr$hasContactPointToFullFace(world, pos, facing);
    }

    @Override
    public boolean btwr$onMortarApplied(World world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean btwr$hasMortar(WorldAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean btwr$hasNeighborWithMortarInContact(World world, BlockPos pos) {
        for (Direction facing : Direction.values()) {
            if (WorldUtils.hasNeighborWithMortarInFullFaceContactToFacing(world, pos, facing)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean btwr$isStickyToSnow(WorldAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean btwr$hasStickySnowNeighborInContact(World world, BlockPos pos) {
        for (Direction facing : Direction.values()) {
            if (WorldUtils.hasStickySnowNeighborInFullFaceContactToFacing(world, pos, facing)) {
                return true;
            }
        }

        return false;
    }

    //----------- Plant related functionality ----------//

    @Override
    public void btwr$notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {}

    @Override
    public float btwr$getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) { return 1F; }

    @Override
    public boolean btwr$isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {return false;}

    @Override
    public boolean btwr$getIsFertilizedForPlantGrowth(World world, BlockPos pos) {
        return false;
    }

    @Override
    public int btwr$getWeedsGrowthLevel(WorldAccess blockAccess, BlockPos pos)
    {
        return 0;
    }

    @Override
    public void btwr$removeWeeds(World world, BlockPos pos) {}

    //------------- Grazing Functionality -------------//

    @Override
    public boolean btwr$canBeGrazedOn(WorldAccess worldAccess, BlockPos pos, AnimalEntity byAnimal) {
        return false;
    }

    @Override
    public void btwr$onGrazed(World world, BlockPos pos, AnimalEntity animal) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());

        Block blockBelow = world.getBlockState(pos.down()).getBlock();

        if (blockBelow != null) {
            blockBelow.btwr$onVegetationAboveGrazed(world, pos.down(), animal);
        }
    }

    @Override
    public void btwr$onVegetationAboveGrazed(World world, BlockPos pos, AnimalEntity animal) {}

    @Override
    public void btwr$notifyNeighborsBlockDisrupted(World world, BlockPos pos) {
        BlockPos tempPos = new BlockPos(pos);

        for (int facingId = 0; facingId <= 5; facingId++) {
            Direction facing = Direction.byId(facingId);

            tempPos.offset(facing);

            Block tempBlock = world.getBlockState(tempPos).getBlock();

            if (tempBlock != null) {
                tempBlock.btwr$onNeighborDisrupted(world, tempPos, facing.getOpposite());
            }
        }
    }

    @Override
    public void btwr$onNeighborDisrupted(World world, BlockPos pos, Direction facing) {}


}