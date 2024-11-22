package btwr.lib.added;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface ShapelessRecipeAdded
{

    DefaultedList<ItemStack> getAdditionalDrops();

    void setAdditionalDrops(DefaultedList<ItemStack> secondaryOutput);

    /** used primarily to check if it should display a GUI change for additional drops **/
    boolean hasAdditionalDrops();



}
