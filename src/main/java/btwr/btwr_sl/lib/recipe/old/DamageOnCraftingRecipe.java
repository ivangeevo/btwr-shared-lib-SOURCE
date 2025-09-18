package btwr.btwr_sl.lib.recipe.old;

import net.minecraft.recipe.Ingredient;

public interface DamageOnCraftingRecipe {
    Ingredient getTool();
    int getToolDamage();
}
