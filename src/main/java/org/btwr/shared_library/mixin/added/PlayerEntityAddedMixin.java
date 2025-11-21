package org.btwr.shared_library.mixin.added;

import org.btwr.shared_library.interfaces.added.PlayerEntityAdded;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityAddedMixin implements PlayerEntityAdded {
    @Unique private int timesCraftedThisTick = 0;

    @Override
    public int btwr$timesCraftedThisTick() {
        return timesCraftedThisTick;
    }

    @Override
    public void btwr$setTimesCraftedThisTick(int value) {
        timesCraftedThisTick = value;
    }
}