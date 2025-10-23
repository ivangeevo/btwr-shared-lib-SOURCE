package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.lib.interfaces.added.AnimalEntityAdded;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity implements AnimalEntityAdded {

    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canGrazeOnBlock(BlockPos pos) {
        World world = this.getWorld();
        Block block = world.getBlockState(pos).getBlock();

        if (block != null) {
            return block.canBeGrazedOn(world, pos, (AnimalEntity)(Object)this);
        }

        return false;
    }

    @Override
    public BlockPos getGrazeBlockForPos() {
        BlockPos pos = this.getBlockPos();
        BlockPos targetPos = new BlockPos(
                MathHelper.floor(pos.getX()),
                (int)this.getBoundingBox().minY,
                MathHelper.floor(pos.getZ())
        );

        if (this.canGrazeOnBlock(targetPos)) {
            return targetPos;
        } else {
            //targetPos.y--;
            BlockPos newTargetPos = targetPos.down();

            if (canGrazeOnBlock(newTargetPos) ) {
                return targetPos;
            }
        }

        return null;
    }
}
