package btwr.btwr_sl.lib.mixin.recipe;

import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import btwr.btwr_sl.lib.recipe.CraftingWithToolShapelessRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin {
    
    @Shadow @Final private RecipeInputInventory input;

    @Shadow @Final private PlayerEntity player;

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    protected void setAdditionalDrops(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        MinecraftServer server = player.getWorld().getServer();
        if (server != null) {
            dropAdditionalItemsOnTake(server, player);
        }
    }

    @Inject(method = "onTakeItem", at = @At("TAIL"))
    protected void setTickCraftLogic(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        player.setTimesCraftedThisTick(player.timesCraftedThisTick() + 1);
    }

    // Add additional logic after the onCrafted() method call and cancel the original logic for RecipeType.CRAFTING
    @Inject(method = "onTakeItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/CraftingResultSlot;onCrafted(Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER), cancellable = true)
    protected void setToolCraftingRemainder(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();
        int i = positioned.left();
        int j = positioned.top();
        DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager().getRemainingStacks(BTWRSLRecipes.TEST_SHAPELESS_RECIPE_TYPE, craftingRecipeInput, player.getWorld());

        for (int k = 0; k < craftingRecipeInput.getHeight(); k++) {
            for (int l = 0; l < craftingRecipeInput.getWidth(); l++) {
                int m = l + i + (k + j) * this.input.getWidth();
                ItemStack itemStack = this.input.getStack(m);
                ItemStack itemStack2 = defaultedList.get(l + k * craftingRecipeInput.getWidth());
                if (!itemStack.isEmpty()) {
                    this.input.removeStack(m, 1);
                    itemStack = this.input.getStack(m);
                }

                if (!itemStack2.isEmpty()) {
                    if (itemStack.isEmpty()) {
                        this.input.setStack(m, itemStack2);
                    } else if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2)) {
                        itemStack2.increment(itemStack.getCount());
                        this.input.setStack(m, itemStack2);
                    } else if (!this.player.getInventory().insertStack(itemStack2)) {
                        this.player.dropItem(itemStack2, false);
                    }
                }
            }
        }
        ci.cancel();
    }

    // Method to create the additional drops when taking(crafting) an item
    @Unique
    private void dropAdditionalItemsOnTake(MinecraftServer server, PlayerEntity player) {
        CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();

        Optional<RecipeEntry<CraftingWithToolShapelessRecipe>> optional = server.getRecipeManager()
                .getFirstMatch(BTWRSLRecipes.CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_TYPE, craftingRecipeInput, player.getWorld());

        if (optional.isEmpty()) return;

        DefaultedList<ItemStack> drops = optional.get().value().getAdditionalDrops();
        if (drops != null && !drops.isEmpty()) {
            for (ItemStack itemStack : drops) {
                player.dropStack(itemStack.copy());
            }
        }

    }


}

