package btwr.btwr_sl.lib.mixin.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Inject(method = "getRemainingStacks", at = @At("HEAD"), cancellable = true)
    private void beforeGetRemainingStacks(
            RecipeType<?> type,
            RecipeInput input,
            World world,
            CallbackInfoReturnable<DefaultedList<ItemStack>> cir
    ) {
        // Only apply to crafting recipes
        if (type == RecipeType.CRAFTING && input instanceof CraftingRecipeInput craftingInput) {
            world.getRecipeManager()
                    .getFirstMatch(RecipeType.CRAFTING, craftingInput, world)
                    .ifPresent(recipeEntry -> {
                        // get the actual recipe instance
                        var recipe = recipeEntry.value();
                        if (recipe instanceof RepairItemRecipe) {
                            // Skip remainders for repair recipes
                            cir.setReturnValue(DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY));
                        }
                    });
        }
    }
}
