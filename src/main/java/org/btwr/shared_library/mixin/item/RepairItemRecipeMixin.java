package org.btwr.shared_library.mixin.item;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import org.btwr.shared_library.api.registry.RepairRecipeBlockedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {

    @Inject(method = "canCombineStacks", at = @At("HEAD"), cancellable = true)
    private static void preventRepair(ItemStack first, ItemStack second, CallbackInfoReturnable<Boolean> cir) {
        if (RepairRecipeBlockedRegistry.isBlocked(first, RepairRecipeBlockedRegistry.BlockType.CRAFTING) || RepairRecipeBlockedRegistry.isBlocked(second, RepairRecipeBlockedRegistry.BlockType.CRAFTING)) {
            cir.setReturnValue(false);
        }
    }
}