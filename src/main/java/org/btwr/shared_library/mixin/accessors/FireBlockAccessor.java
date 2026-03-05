package org.btwr.shared_library.mixin.accessors;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor for internal FireBlock flammability tables.
 */
@Mixin(FireBlock.class)
public interface FireBlockAccessor {

    /**
     * Access to the "spread chances" table used by vanilla FireBlock.
     */
    @Accessor("spreadChances")
    Object2IntMap<Block> btwr$getSpreadChances();

    /**
     * Access to the "burn chances" table used by vanilla FireBlock.
     * <p>This is the closest analogue to BTW's abilityToCatchFire[].
     */
    @Accessor("burnChances")
    Object2IntMap<Block> btwr$getBurnChances();

}