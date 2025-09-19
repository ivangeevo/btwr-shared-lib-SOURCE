package btwr.btwr_sl.lib.mixin.recipe;

import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import btwr.btwr_sl.lib.recipe.TestShapelessRecipe;
import btwr.btwr_sl.lib.recipe.old.AdditionalDropsShapelessRecipe;
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

    // Method to create the additional drops when taking(crafting) an item
    @Unique
    private void dropAdditionalItemsOnTake(MinecraftServer server, PlayerEntity player) {
        CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();

        Optional<RecipeEntry<CraftingRecipe>> optional = server.getRecipeManager()
                .getFirstMatch(BTWRSLRecipes.TEST_SHAPELESS_RECIPE_TYPE, craftingRecipeInput, player.getWorld());

        if (optional.isEmpty()) return;

        /**
        DefaultedList<ItemStack> drops = optional.get().value().getAdditionalDrops();
        if (drops != null && !drops.isEmpty()) {
            for (ItemStack itemStack : drops) {
                player.dropStack(itemStack.copy());
            }
        }
         **/
    }


}

