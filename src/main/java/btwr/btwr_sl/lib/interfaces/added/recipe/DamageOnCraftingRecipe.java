package btwr.btwr_sl.lib.interfaces.added.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

public interface DamageOnCraftingRecipe {
    DefaultedList<Ingredient> getDamagedOnCrafting();
}
