package btwr.btwr_sl.lib.recipe.old;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface AdditionalDropsRecipe {
    DefaultedList<ItemStack> getAdditionalDrops();
}
