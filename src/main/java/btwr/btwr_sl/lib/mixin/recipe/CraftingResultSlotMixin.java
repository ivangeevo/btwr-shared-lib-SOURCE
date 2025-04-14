package btwr.btwr_sl.lib.mixin.recipe;

import btwr.btwr_sl.lib.interfaces.added.recipe.ShapelessRecipeAdded;
import btwr.btwr_sl.lib.util.CraftingSoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
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
    protected void setSecondaryDropsAndCraftSound(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        MinecraftServer server = player.getWorld().getServer();
        if (server != null) {
            dropAdditionalItemsOnTake(server, player);
        }

        if (player.getWorld().isClient) {
            //handleSoundOnCraft(stack, player);
            //CraftingSoundManager.playCraftingSound(stack, player);
        }
    }



    @Inject(method = "onTakeItem", at = @At("TAIL"))
    protected void setTickCraftLogic(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        player.setTimesCraftedThisTick(player.timesCraftedThisTick() + 1);
    }

        // ---------- Class specific methods ---------- //

    // Method to create the secondary optional drop.
    @Unique
    private void dropAdditionalItemsOnTake(MinecraftServer server, PlayerEntity player) {
        CraftingRecipeInput.Positioned positioned = this.input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();

        Optional<RecipeEntry<CraftingRecipe>> optional = server.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingRecipeInput, player.getWorld());
        CraftingRecipe craftingRecipe;

        if (optional.isPresent() && (craftingRecipe = optional.get().value()) instanceof ShapelessRecipe) {

            DefaultedList<ItemStack> drops = ((ShapelessRecipeAdded) craftingRecipe).getAdditionalDrops();
            if (drops != null && !drops.isEmpty()) {
                for (ItemStack itemStack : drops) {
                    player.dropStack(itemStack.copy());
                }
            }

        }
    }

}

