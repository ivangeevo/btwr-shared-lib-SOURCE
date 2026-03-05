package org.btwr.shared_library.api.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class GroundCoverBlock extends Block {

    public static final float VISUAL_HEIGHT = 0.125F;

    public GroundCoverBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState stateBelow = world.getBlockState(pos.down());
        Block blockBelow = stateBelow.getBlock();

        if (blockBelow != null) {
            return blockBelow.btwr$canGroundCoverRestOnBlock((World)world, pos.down());
        }

        return false;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!canPlaceAt(state, world, pos)) {
            onSelfRemoval(world, pos);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        float visualOffset = 0.0F;

        BlockPos belowPos = pos.down();
        BlockState belowState = world.getBlockState(belowPos);
        Block blockBelow = belowState.getBlock();

        if (!belowState.isAir()) {
            visualOffset = blockBelow.btwr$groundCoverRestingOnVisualOffset(world, belowPos);
        }

        return VoxelShapes.cuboid(
                0.0,
                visualOffset,
                0.0,
                1.0,
                VISUAL_HEIGHT + visualOffset,
                1.0
        );
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean btwr$isGroundCover() {
        return true;
    }

    public static void clearAnyGroundCoverRestingOnBlock(World world, BlockPos pos) {
        BlockState stateAbove = world.getBlockState(pos.up());
        Block blockAbove = stateAbove.getBlock();

        if (!stateAbove.isAir()) {
            if (blockAbove.btwr$isGroundCover()) {
                if (blockAbove instanceof GroundCoverBlock groundCoverBlock) {
                    groundCoverBlock.onSelfRemoval(world, pos.up());
                }
                world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
            }
            else if (blockAbove.btwr$groundCoverRestingOnVisualOffset(world, pos.up()) < -0.99F) {
                BlockState state2Above = world.getBlockState(pos.up(2));
                Block block2Above = stateAbove.getBlock();

                if (!state2Above.isAir() && block2Above.btwr$isGroundCover()) {
                    if (blockAbove instanceof GroundCoverBlock groundCoverBlock) {
                        groundCoverBlock.onSelfRemoval(world, pos.up(2));
                    }
                    world.setBlockState(pos.up(2), Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    public static boolean isGroundCoverRestingOnBlock(World world, BlockPos pos) {
        BlockState stateAbove = world.getBlockState(pos.up());
        Block blockAbove = stateAbove.getBlock();

        if (!stateAbove.isAir()) {
            if (blockAbove.btwr$isGroundCover()) {
                return true;
            }
            else if (blockAbove.btwr$groundCoverRestingOnVisualOffset(world, pos.up()) < -0.99F) {
                BlockState state2Above = world.getBlockState(pos.up(2));
                Block block2Above = stateAbove.getBlock();

                if (!state2Above.isAir() && block2Above.btwr$isGroundCover()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void onSelfRemoval(World world, BlockPos pos) {}

}
