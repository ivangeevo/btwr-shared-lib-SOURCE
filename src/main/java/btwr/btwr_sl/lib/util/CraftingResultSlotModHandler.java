package btwr.btwr_sl.lib.util;

import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import btwr.btwr_sl.lib.recipe.ExtendedShapelessRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

public class CraftingResultSlotModHandler {

    private static CraftingResultSlotModHandler INSTANCE = new CraftingResultSlotModHandler();

    private CraftingResultSlotModHandler() {}

    public static CraftingResultSlotModHandler getInstance() {
        return INSTANCE;
    }

    // Method to create the additional drops when taking(crafting) an item
    public void dropAdditionalItemsOnTake(MinecraftServer server, PlayerEntity player, RecipeInputInventory input) {
        CraftingRecipeInput.Positioned positioned = input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();

        Optional<RecipeEntry<ExtendedShapelessRecipe>> optional = server.getRecipeManager()
                .getFirstMatch(BTWRSLRecipes.EXTENDED_SHAPELESS_RECIPE_TYPE, craftingRecipeInput, player.getWorld());

        if (optional.isEmpty()) return;

        DefaultedList<ItemStack> drops = optional.get().value().getAdditionalDrops();
        if (drops != null && !drops.isEmpty()) {
            for (ItemStack itemStack : drops) {
                player.dropStack(itemStack.copy());
            }
        }

    }

    public void setRemainderForTools(PlayerEntity player, RecipeInputInventory input) {
        CraftingRecipeInput.Positioned positioned = input.createPositionedRecipeInput();
        CraftingRecipeInput craftingRecipeInput = positioned.input();
        int i = positioned.left();
        int j = positioned.top();
        DefaultedList<ItemStack> defaultedList = player.getWorld().getRecipeManager()
                .getRemainingStacks(BTWRSLRecipes.EXTENDED_SHAPELESS_RECIPE_TYPE, craftingRecipeInput, player.getWorld());

        for (int k = 0; k < craftingRecipeInput.getHeight(); k++) {
            for (int l = 0; l < craftingRecipeInput.getWidth(); l++) {
                int m = l + i + (k + j) * input.getWidth();
                ItemStack itemStack = input.getStack(m);
                ItemStack itemStack2 = defaultedList.get(l + k * craftingRecipeInput.getWidth());
                if (!itemStack.isEmpty()) {
                    input.removeStack(m, 1);
                    itemStack = input.getStack(m);
                }

                if (!itemStack2.isEmpty()) {
                    if (itemStack.isEmpty()) {
                        input.setStack(m, itemStack2);
                    } else if (ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2)) {
                        itemStack2.increment(itemStack.getCount());
                        input.setStack(m, itemStack2);
                    } else if (!player.getInventory().insertStack(itemStack2)) {
                        player.dropItem(itemStack2, false);
                    }
                }
            }
        }
    }
}
