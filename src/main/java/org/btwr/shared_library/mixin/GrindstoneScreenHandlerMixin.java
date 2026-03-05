package org.btwr.shared_library.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.btwr.shared_library.util.RepairRecipeBlockedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin {

    @Inject(method = "combineItems", at = @At("HEAD"), cancellable = true)
    private void preventGrindstoneCombine(ItemStack first, ItemStack second, CallbackInfoReturnable<ItemStack> cir) {
        if (RepairRecipeBlockedRegistry.isBlocked(first, RepairRecipeBlockedRegistry.BlockType.GRINDSTONE) || RepairRecipeBlockedRegistry.isBlocked(second, RepairRecipeBlockedRegistry.BlockType.GRINDSTONE)) {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
}