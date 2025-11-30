package org.btwr.shared_library.mixin;

import org.btwr.shared_library.interfaces.added.PlayerEntityAdded;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityAdded {

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectedTick(CallbackInfo ci) {
        btwr$setTimesCraftedThisTick(0);
    }

}