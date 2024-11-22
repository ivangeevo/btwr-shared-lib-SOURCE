package btwr.lib.mixin.entity;

import btwr.lib.added.PlayerEntityAdded;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityAdded
{
    private int timesCraftedThisTick = 0;

    @Override
    public int timesCraftedThisTick() {
        return timesCraftedThisTick;
    }

    @Override
    public void setTimesCraftedThisTick(int value) {
        timesCraftedThisTick = value;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectedTick(CallbackInfo ci) {
        setTimesCraftedThisTick(0);
    }
}
