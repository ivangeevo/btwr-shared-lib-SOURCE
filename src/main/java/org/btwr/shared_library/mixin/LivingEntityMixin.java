package org.btwr.shared_library.mixin;

import net.minecraft.entity.LivingEntity;
import org.btwr.shared_library.api.event.BTWREvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tick", at = @At("RETURN"))
    private void onEntityTick(CallbackInfo ci) {
        BTWREvents.LIVING_TICK.createInvoker().accept((LivingEntity)(Object)this);
    }

}