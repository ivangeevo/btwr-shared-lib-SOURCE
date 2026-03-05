package org.btwr.shared_library.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.btwr.shared_library.util.RepairRecipeBlockedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void preventAnvilCombine(CallbackInfo ci) {
        AnvilScreenHandler self = (AnvilScreenHandler) (Object) this;
        ItemStack first = self.getSlot(0).getStack();
        ItemStack second = self.getSlot(1).getStack();
        if (RepairRecipeBlockedRegistry.isBlocked(first, RepairRecipeBlockedRegistry.BlockType.ANVIL) || RepairRecipeBlockedRegistry.isBlocked(second, RepairRecipeBlockedRegistry.BlockType.ANVIL)) {
            self.getSlot(2).setStack(ItemStack.EMPTY);
            ci.cancel();
        }
    }

}